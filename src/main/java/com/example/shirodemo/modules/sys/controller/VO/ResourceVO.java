package com.example.shirodemo.modules.sys.controller.VO;

import com.example.shirodemo.modules.sys.dataobject.ResourceInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
@ApiModel("ResourceVO")
public class ResourceVO {
    @ApiModelProperty("ppt资源")
    private ResourceInfo ppt;
    @ApiModelProperty("pdf资源")
    private ResourceInfo pdf;
    @ApiModelProperty("mp4资源")
    private List<ResourceInfo> video;
}
