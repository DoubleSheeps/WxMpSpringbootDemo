package com.example.shirodemo.modules.wx.service;

import com.example.shirodemo.config.TaskExcutor;
import com.example.shirodemo.model.WxUser;
import com.example.shirodemo.modules.sys.dao.MemberDOMapper;
import com.example.shirodemo.modules.sys.dataobject.MemberDO;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import me.chanjar.weixin.mp.bean.tag.WxUserTag;
import me.chanjar.weixin.mp.util.WxMpConfigStorageHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final WxMpService wxMpService;
    private final MemberDOMapper memberDOMapper;

    private volatile static  boolean syncWxUserTaskRunning=false;

    public List<MemberDO> getAll(){
        return memberDOMapper.selectAll();
    }

    public void syncWxUsers(){
        //同步较慢，防止个多线程重复执行同步任务
        Assert.isTrue(!syncWxUserTaskRunning,"后台有同步任务正在进行中，请稍后重试");
        syncWxUserTaskRunning=true;
        logger.info("同步公众号粉丝列表：任务开始");
        boolean hasMore=true;
        String nextOpenid=null;
        WxMpUserService wxMpUserService = wxMpService.getUserService();
        try {
            int page=1;
            while (hasMore){
                WxMpUserList wxMpUserList = wxMpUserService.userList(nextOpenid);//拉取openid列表，每次最多1万个
                logger.info("拉取openid列表：第{}页，数量：{}",page++,wxMpUserList.getCount());
                List<String> openids = wxMpUserList.getOpenids();
                this.syncWxUsers(openids);
                nextOpenid=wxMpUserList.getNextOpenid();
                hasMore= StringUtils.hasText(nextOpenid) && wxMpUserList.getCount()>=10000;
            }
        } catch (WxErrorException e) {
            logger.error("同步公众号粉丝出错:",e);
        }finally {
            syncWxUserTaskRunning=false;
        }
        logger.info("同步公众号粉丝列表：完成");
    }

    public void syncWxUsers(List<String> openids) {
        if(openids.size()<1) {
            return;
        }
        final String batch=openids.get(0).substring(20);//截取首个openid的一部分做批次号（打印日志时使用，无实际意义）
        WxMpUserService wxMpUserService = wxMpService.getUserService();
        int start=0,batchSize=openids.size(),end=Math.min(100,batchSize);
        logger.info("开始处理批次：{}，批次数量：{}",batch,batchSize);
        while (start<end && end<=batchSize){//分批处理,每次最多拉取100个用户信息
            final int finalStart = start,finalEnd = end;
            final List<String> subOpenids=openids.subList(finalStart,finalEnd);
            TaskExcutor.submit(()->{//使用线程池同步数据，否则大量粉丝数据需同步时会很慢
                logger.info("同步批次:【{}--{}-{}】，数量：{}",batch, finalStart, finalEnd,subOpenids.size());
                List<WxMpUser> wxMpUsers = null;//批量获取用户信息，每次最多100个
                try {
                    wxMpUsers = wxMpUserService.userInfoList(subOpenids);
                } catch (WxErrorException e) {
                    logger.error("同步出错，批次：【{}--{}-{}】，错误信息：{}",batch, finalStart, finalEnd,e.getError().getErrorMsg());
                }
                if(wxMpUsers!=null && !wxMpUsers.isEmpty()){
                    List<MemberDO> wxUsers=wxMpUsers.parallelStream().map(item->this.copy(new WxUser(item))).collect(Collectors.toList());
                    this.saveOrUpdateBatch(wxUsers);
                }
            });
            start=end;
            end=Math.min(end+100,openids.size());
        }
        logger.info("批次：{}处理完成",batch);
    }

    public void saveOrUpdateBatch(List<MemberDO> wxUsers){
        for (MemberDO memberDO:wxUsers){
            MemberDO memberDO1 = memberDOMapper.selectByPrimaryKey(memberDO.getOpenid());
            if(memberDO1==null){
                memberDOMapper.insertSelective(memberDO);
            }else {
                memberDOMapper.updateByPrimaryKeySelective(memberDO);
            }
        }
    }

    public void saveOrUpdate(MemberDO memberDO){
        MemberDO memberDO1 = memberDOMapper.selectByPrimaryKey(memberDO.getOpenid());
        if(memberDO1==null){
            memberDOMapper.insertSelective(memberDO);
        }else {
            memberDOMapper.updateByPrimaryKeySelective(memberDO);
        }
    }

    public WxUser getById(String openid){
        MemberDO memberDO = memberDOMapper.selectByPrimaryKey(openid);
        return copy(memberDO);
    }

    public WxUser refreshUserInfo(String openid){
        try {
            // 获取微信用户基本信息
            logger.info("更新用户信息，openid={}",openid);
            WxMpUser userWxInfo = wxMpService.getUserService().userInfo(openid, null);
            if (userWxInfo == null) {
                logger.error("获取不到用户信息，无法更新,openid:{}",openid);
                return null;
            }
            WxUser user = new WxUser(userWxInfo);
            this.saveOrUpdate(copy(user));
            return user;
        } catch (Exception e) {
            logger.error("更新用户信息失败,openid:{}",openid);
        }
        return null;
    }

    public List<WxUserTag> getWxTags() throws WxErrorException {
        logger.info("拉取公众号用户标签");
        return wxMpService.getUserTagService().tagGet();
    }

    public void creatTag(String name) throws WxErrorException {
        wxMpService.getUserTagService().tagCreate(name);
    }

    public void updateTag(Long tagid, String name) throws WxErrorException {
        wxMpService.getUserTagService().tagUpdate(tagid,name);
    }

    public void tagging(Long tagid, String openid) throws WxErrorException {
        wxMpService.getUserTagService().batchTagging(tagid,new String[]{openid});
        refreshUserInfo(openid);
    }

    public void untagging(Long tagid, String openid) throws WxErrorException {
        wxMpService.getUserTagService().batchUntagging(tagid,new String[]{openid});
        String appid = WxMpConfigStorageHolder.get();
        refreshUserInfo(openid);
    }

    private MemberDO copy(WxUser wxUser){
        if(wxUser==null){
            return null;
        }
        MemberDO memberDO = new MemberDO();
        BeanUtils.copyProperties(wxUser,memberDO);
        return memberDO;
    }
    private WxUser copy(MemberDO memberDO){
        if(memberDO==null){
            return null;
        }
        WxUser wxUser = new WxUser();
        BeanUtils.copyProperties(memberDO,wxUser);
        return wxUser;
    }
}
