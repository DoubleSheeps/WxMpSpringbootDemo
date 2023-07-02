package com.example.shirodemo.modules.wx.service.impl;

import com.example.shirodemo.model.MsgTemplate;
import com.example.shirodemo.modules.sys.controller.VO.ActivityForm;
import com.example.shirodemo.modules.sys.dao.TemplateDOMapper;
import com.example.shirodemo.modules.sys.dataobject.TemplateDOWithBLOBs;
import com.example.shirodemo.modules.wx.config.WxMpProperties;
import com.example.shirodemo.modules.wx.service.TemplateMessageService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.template.WxMpTemplate;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: YoungSheep
 * @Date: 2023-06-17
 * @Description:
 */
@Service
public class TemplateMessageServiceImpl implements TemplateMessageService {
    @Value("${wx.mp.webUrl}")
    private String URL;
    @Autowired
    private WxMpProperties properties;
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private TemplateDOMapper templateDOMapper;
    @Override
    public void sendBindMessage( WxMpUser userWxInfo) throws WxErrorException {
        if (userWxInfo != null) {
            //绑定模板
            WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(userWxInfo.getOpenId())
                .templateId("VBtVEzA4kC8PU7cu8q21YkC3gYt-KzCVAM3sveaVobw")
                .url(URL+"/#/bindUserInfo")
                .build();
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        }
    }

    @Override
    public void sendClassTipMessage(WxMpUser userWxInfo) throws WxErrorException {
        if (userWxInfo != null) {
            WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(userWxInfo.getOpenId())
                .templateId("BlpfUyYyGgW2dSkvCcU-WwvnW8SYzoLysXxRFL0PUCw")
                .url(URL + "/#/timetable")
                .build();
            templateMessage.addData(new WxMpTemplateData("name", userWxInfo.getRemark()));
            templateMessage.addData(new WxMpTemplateData("course", "少儿编程"));
            templateMessage.addData(new WxMpTemplateData("time", "2023-06-18 10:00~12:00"));
            templateMessage.addData(new WxMpTemplateData("place", "中科大先研院A404"));

            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        }
    }

    @Override
    public List<MsgTemplate> syncWxTemplate() throws WxErrorException {
        List<WxMpTemplate> wxMpTemplateList= wxMpService.getTemplateMsgService().getAllPrivateTemplate();
        return wxMpTemplateList.stream().map(item->new MsgTemplate(item,properties.getConfigs().get(0).getAppId())).collect(Collectors.toList());
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
        templateDOMapper.insertSelective(templateDOWithBLOBs);
    }

}
