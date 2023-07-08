package com.example.shirodemo.modules.wx.service.impl;

import com.example.shirodemo.Utils.DateStringUtil;
import com.example.shirodemo.config.TaskExcutor;
import com.example.shirodemo.error.BusinessException;
import com.example.shirodemo.error.EmBusinessError;
import com.example.shirodemo.model.MsgTemplate;
import com.example.shirodemo.modules.sys.controller.VO.ActivityForm;
import com.example.shirodemo.modules.sys.controller.VO.TemplateMsgBatchForm;
import com.example.shirodemo.modules.sys.dao.TemplateDOMapper;
import com.example.shirodemo.modules.sys.dao.TemplateInfoDOMapper;
import com.example.shirodemo.modules.sys.dataobject.MemberDO;
import com.example.shirodemo.modules.sys.dataobject.StudentDO;
import com.example.shirodemo.modules.sys.dataobject.TemplateDOWithBLOBs;
import com.example.shirodemo.modules.sys.dataobject.TemplateInfoDO;
import com.example.shirodemo.modules.wx.config.WxMpProperties;
import com.example.shirodemo.modules.wx.service.TemplateMessageService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.template.WxMpTemplate;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: YoungSheep
 * @Date: 2023-06-17
 * @Description:
 */
@Service
public class TemplateMessageServiceImpl implements TemplateMessageService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private WxMpProperties properties;
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private TemplateDOMapper templateDOMapper;
    @Autowired
    private TemplateInfoDOMapper templateInfoDOMapper;
    @Override
    public void sendBindMessage( WxMpUser userWxInfo) throws WxErrorException {
        if (userWxInfo != null) {
            //绑定模板
            WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(userWxInfo.getOpenId())
                .templateId("VBtVEzA4kC8PU7cu8q21YkC3gYt-KzCVAM3sveaVobw")
                .url(properties.getWebUrl()+"/bindUserInfo?url="+properties.getTestUrl())
                .build();
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        }
    }

    @Override
    public void sendClassTipMessage(WxMpUser userWxInfo) throws WxErrorException {
        logger.debug(userWxInfo.toString());
        if (userWxInfo != null) {
            WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(userWxInfo.getOpenId())
                .templateId("iuyjmbFX8YBgZc8bas-9yjZohj5QyH9XsrqYCwAXyb4")
                .url(properties.getWebUrl() + "/timetable?openid="+userWxInfo.getOpenId())
                .build();
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        }
    }

    public void sendClassTipMessage(List<StudentDO> studentDOList, String course, String time, String place,String templateId) {
        TaskExcutor.submit(()->{//使用线程池发送模板，否则大量课前提醒需要发送时会很慢
            logger.info("正在推送课程 {} 的提醒，学生列表共{}个，具体为：{}",course,studentDOList.toString(),studentDOList.size());
            studentDOList.forEach(studentDO -> {
                if(studentDO.getSub()!=null&&studentDO.getSub().intValue()==1){
                    WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                            .toUser(studentDO.getOpenid())
                            .templateId(templateId)
                            .url(properties.getWebUrl() + "/timetable?openid="+studentDO.getOpenid())
                            .build();
                    templateMessage.addData(new WxMpTemplateData("name", studentDO.getName()));
                    templateMessage.addData(new WxMpTemplateData("course", course));
                    templateMessage.addData(new WxMpTemplateData("time", time));
                    templateMessage.addData(new WxMpTemplateData("place", place));
                    logger.info("发送课前提醒：{}",templateMessage.toJson());
                    try {
                        wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
                    } catch (WxErrorException e) {
                        logger.error("发送课前提醒失败，原因：{}",e.getError().getErrorMsg());
                    }
                }else {
                    logger.info("学生{}没有绑定家长微信，课程提醒发送失败",studentDO.getName());
                }
            });
        });
    }

    public void sendClassStatusMessage(String student,String teacher, String course, String time, String place,String status,String openId,Integer id) {
        TaskExcutor.submit(()->{//使用线程池发送模板，否则大量课前提醒需要发送时会很慢
            WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                    .toUser(openId)
                    .templateId("-CHM7syVYgtZ6munpaCo4C_Sh8aOHkNI4cn9rrwdd6M")
                    .url(properties.getWebUrl() + "/classFeed?openid="+openId+"&id="+id.toString())
                    .build();
            templateMessage.addData(new WxMpTemplateData("student", student));
            templateMessage.addData(new WxMpTemplateData("course", course));
            templateMessage.addData(new WxMpTemplateData("teacher", teacher));
            templateMessage.addData(new WxMpTemplateData("time", time));
            templateMessage.addData(new WxMpTemplateData("place", place));
            templateMessage.addData(new WxMpTemplateData("status", status));
            logger.info("正在推送学员 {}  {}的{} 课程的状态信息模板：{}",student,time,course,templateMessage.toJson());
            try {
                wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
            } catch (WxErrorException e) {
                logger.error("发送课前提醒失败，原因：{}",e.getError().getErrorMsg());
            }
        });
    }

    @Async
    public void sendMsgBatch(TemplateMsgBatchForm form) {
        logger.info("批量发送模板消息任务开始,参数：{}",form.toString());
        WxMpTemplateMessage.WxMpTemplateMessageBuilder builder = WxMpTemplateMessage.builder()
                .templateId(form.getTemplateId())
                .url(form.getUrl())
                .data(form.getData());
        List<String> openids = form.getOpenids();
        if(openids==null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户openid数组有误！");
        }
        openids.forEach(openid->{
            WxMpTemplateMessage msg = builder.toUser(openid).build();
            this.sendTemplateMsg(msg);
        });
        logger.info("批量发送模板消息任务结束");
    }
    @Async
    public void sendTemplateMsg(WxMpTemplateMessage msg) {
        String result;
        try {
            result = wxMpService.getTemplateMsgService().sendTemplateMsg(msg);
        } catch (WxErrorException e) {
            result = e.getMessage();
        }
        //保存发送日志
//        TemplateMsgLog log = new TemplateMsgLog(msg,appid, result);
//        templateMsgLogService.addLog(log);
    }

    public List<MsgTemplate> getWxTemplateList(){
        List<TemplateInfoDO> templateInfoDOList = templateInfoDOMapper.getAll();
        return templateInfoDOList.stream().map(this::copy).collect(Collectors.toList());
    }

    @Override
    public void syncWxTemplate() throws WxErrorException {
        List<WxMpTemplate> wxMpTemplateList= wxMpService.getTemplateMsgService().getAllPrivateTemplate();
        for(WxMpTemplate wxMpTemplate:wxMpTemplateList){
            saveOrUpdate(wxMpTemplate);
        }
    }
    @Override
    public List<TemplateDOWithBLOBs> list(){
        return templateDOMapper.selectAll();
    }

    @Override
    public void addActivity(ActivityForm form) {
        TemplateDOWithBLOBs templateDOWithBLOBs = new TemplateDOWithBLOBs();
        templateDOWithBLOBs.setTemplateId(form.getTemplateId());
        templateDOWithBLOBs.setName(form.getName());
        templateDOWithBLOBs.setContent(form.getContent());
        templateDOWithBLOBs.setData(form.getData());
        templateDOWithBLOBs.setUrl(form.getUrl());
        templateDOWithBLOBs.setTitle(form.getTitle());
        templateDOWithBLOBs.setUpdateTime(new Date());
        templateDOWithBLOBs.setStatus((byte)1);
        templateDOWithBLOBs.setAppid(properties.getAppId());
        templateDOMapper.insertSelective(templateDOWithBLOBs);
    }

    public void saveOrUpdate(WxMpTemplate wxMpTemplate){
        TemplateInfoDO templateInfoDO1 = templateInfoDOMapper.selectByPrimaryKey(wxMpTemplate.getTemplateId());
        if(templateInfoDO1==null){
            TemplateInfoDO templateInfoDO = new TemplateInfoDO();
            templateInfoDO.setTemplateId(wxMpTemplate.getTemplateId());
            templateInfoDO.setTitle(wxMpTemplate.getTitle());
            templateInfoDO.setPrimaryIndustry(wxMpTemplate.getPrimaryIndustry());
            templateInfoDO.setDeputyIndustry(wxMpTemplate.getDeputyIndustry());
            templateInfoDO.setContent(wxMpTemplate.getContent());
            templateInfoDOMapper.insertSelective(templateInfoDO);
        }else {
            templateInfoDO1.setTemplateId(wxMpTemplate.getTemplateId());
            templateInfoDO1.setTitle(wxMpTemplate.getTitle());
            templateInfoDO1.setPrimaryIndustry(wxMpTemplate.getPrimaryIndustry());
            templateInfoDO1.setDeputyIndustry(wxMpTemplate.getDeputyIndustry());
            templateInfoDO1.setContent(wxMpTemplate.getContent());
            templateInfoDOMapper.updateByPrimaryKeySelective(templateInfoDO1);
        }
    }

    MsgTemplate copy(TemplateInfoDO templateInfoDO){
        if(templateInfoDO==null){
            return null;
        }
        MsgTemplate msgTemplate = new MsgTemplate();
        BeanUtils.copyProperties(templateInfoDO,msgTemplate);
        return msgTemplate;
    }

}
