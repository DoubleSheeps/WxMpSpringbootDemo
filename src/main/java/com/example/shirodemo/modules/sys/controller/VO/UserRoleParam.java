package com.example.shirodemo.modules.sys.controller.VO;

import lombok.Data;

@Data
public class UserRoleParam {
    private Integer userId;
    private Integer[] roleIds;
}
