package com.example.shirodemo.modules.sys.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@TableName("resource_info")
@ApiModel("资源信息表")
public class ResourceInfo {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer type;
    private String url;
    private Integer ind;
    private Integer contentId;
    private String name;
}
