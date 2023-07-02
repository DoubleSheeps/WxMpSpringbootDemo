package com.example.shirodemo.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.shirodemo.Utils.Json;
import lombok.Data;
import me.chanjar.weixin.mp.bean.template.WxMpTemplate;

import java.io.Serializable;
import java.util.Date;
@Data
public class MsgTemplate implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String appid;
    private String templateId;

    private String name;
    private String title;
    private String content;
    private JSONArray data;
    private String url;
    private JSONObject miniprogram;

    private boolean status;
    private Date updateTime;
    public MsgTemplate() {

    }
    public MsgTemplate(WxMpTemplate mpTemplate, String appid) {
        this.appid = appid;
        this.templateId=mpTemplate.getTemplateId();
        this.title=mpTemplate.getTitle();
        this.name=mpTemplate.getTemplateId();
        this.content = mpTemplate.getContent();
        this.status=true;
    }

//    @Override
//    public String toString() {
//        return Json.toJsonString(this);
//    }

}
