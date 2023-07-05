package com.example.shirodemo.modules.wx.service;

import com.example.shirodemo.model.MsgTemplate;
import com.example.shirodemo.modules.sys.controller.VO.ActivityForm;
import com.example.shirodemo.modules.sys.controller.VO.TemplateMsgBatchForm;
import com.example.shirodemo.modules.sys.dataobject.TemplateDOWithBLOBs;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

import java.util.List;

/**
 * @Author: YoungSheep
 * @Date: 2023-06-17
 * @Description:
 */

public interface TemplateMessageService {
    //发送绑定模板
    void sendBindMessage(WxMpUser userWxInfo) throws WxErrorException;
    //发送课前提醒模板
    void sendClassTipMessage(WxMpUser userWxInfo) throws WxErrorException;

    void sendTemplateMsg(WxMpTemplateMessage msg);

    void sendMsgBatch(TemplateMsgBatchForm form);
    void sendClassTipMessage(String openid,String name,String course,String time,String place);

    //获取模板
    List<MsgTemplate> getWxTemplateList();

    void syncWxTemplate() throws WxErrorException;

    List<TemplateDOWithBLOBs> list();

    void addActivity(ActivityForm form);
}
