package com.example.shirodemo.controller.VO;

import lombok.Data;

@Data
public class UserRoleParam {
    private Integer userId;
    private Integer[] roleIds;
}
