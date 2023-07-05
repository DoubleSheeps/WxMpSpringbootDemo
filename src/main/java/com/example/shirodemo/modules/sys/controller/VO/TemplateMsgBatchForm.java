package com.example.shirodemo.modules.sys.controller.VO;

import lombok.Data;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;

import java.util.List;

@Data
public class TemplateMsgBatchForm {
    private String templateId;
    private String url;
    private List<WxMpTemplateData> data;
    private List<String> openids;
}
