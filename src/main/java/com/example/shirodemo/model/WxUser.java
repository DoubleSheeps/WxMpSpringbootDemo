package com.example.shirodemo.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.util.StringUtils;

import java.util.Date;

@Data
public class WxUser {
    private String openid;

    private String phone;

    private String nickname;

    private Byte sex;

    private String city;

    private String province;

    private String headimgurl;

    private Date subscribeTime;

    private Boolean subscribe;

    private String unionid;

    private String remark;

    private String subscribeScene;

    private String qrSceneStr;

    private String tagidList;

    public WxUser(){
    }

    public WxUser(WxMpUser wxMpUser) {
        this.openid = wxMpUser.getOpenId();
        this.subscribe=wxMpUser.getSubscribe();
        if(wxMpUser.getSubscribe()){
            this.nickname = wxMpUser.getNickname();
            this.headimgurl = wxMpUser.getHeadImgUrl();
            this.subscribeTime = new Date(wxMpUser.getSubscribeTime()*1000);
            this.unionid=wxMpUser.getUnionId();
            this.remark=wxMpUser.getRemark();
            this.tagidList= JSONObject.toJSONString(wxMpUser.getTagIds());
            this.subscribeScene=wxMpUser.getSubscribeScene();
            String qrScene =  wxMpUser.getQrScene();
            this.qrSceneStr= !StringUtils.hasText(qrScene) ? wxMpUser.getQrSceneStr() : qrScene;
        }
    }

}
