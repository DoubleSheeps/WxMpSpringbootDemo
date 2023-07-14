package com.example.shirodemo.modules.sys.controller;

import com.example.shirodemo.model.common.CommonReturnType;
import com.example.shirodemo.modules.sys.controller.VO.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Hello控制类")
@ApiIgnore
@RestController
public class HelloController {
    @ApiOperation("获取用户信息")
    @GetMapping(value = "/user")
    public CommonReturnType<User> getUser(){
        return CommonReturnType.create(new User("admin","123456"));
    }
    @ApiOperation("传入用户名")
    @PostMapping("/param")
    public CommonReturnType<String> hello2(@ApiParam("用户名") String username){
        return CommonReturnType.create("hello" + username);
    }
}
