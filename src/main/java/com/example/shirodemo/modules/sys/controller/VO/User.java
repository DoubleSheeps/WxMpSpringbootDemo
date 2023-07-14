package com.example.shirodemo.modules.sys.controller.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@ApiModel("用户")
@AllArgsConstructor
public class User {
    @ApiModelProperty(value = "用户名",example = "admin")
    private String username;
    @ApiModelProperty(value = "密码",example = "admin")
    private String password;
}
