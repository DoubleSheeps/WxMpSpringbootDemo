package com.example.shirodemo.modules.sys.controller.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ActivityForm {
    @ApiModelProperty(value = "模板id",example = "sasdawdsadwasddadwdsad")
    private String templateId;
    @ApiModelProperty(value = "模板名称", example = "一级模板")
    private String name;
    @ApiModelProperty(value = "跳转地址",example = "/home")
    private String url;
    @ApiModelProperty(value = "数据Json", example = "xxx")
    private String data;
    @ApiModelProperty(value = "标题",example = "测试")
    private String title;
    @ApiModelProperty(value = "模板内容", example = "xxx")
    private String content;
}
