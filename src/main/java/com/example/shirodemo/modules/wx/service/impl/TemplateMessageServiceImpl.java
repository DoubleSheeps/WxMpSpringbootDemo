package com.example.shirodemo.modules.wx.service.impl;

import com.example.shirodemo.model.MsgTemplate;
import com.example.shirodemo.modules.sys.controller.VO.ActivityForm;
import com.example.shirodemo.modules.sys.dao.TemplateDOMapper;
import com.example.shirodemo.modules.sys.dao.TemplateInfoDOMapper;
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
import org.springframework.beans.BeanUtils;
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
    @Autowired
    private TemplateInfoDOMapper templateInfoDOMapper;
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
