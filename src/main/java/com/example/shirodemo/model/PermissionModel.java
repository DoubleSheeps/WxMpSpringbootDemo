package com.example.shirodemo.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class PermissionModel implements Serializable {

    private Integer id;//主键.
    private String name;//名称.
    private String resourceType;//资源类型，[menu|button]
    private String url;//资源路径.
    private String permission; //权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
    private Long parentId; //父编号
    private String parentIds; //父编号列表
    private Boolean available = Boolean.FALSE;

    // 省略 get set 方法
}
