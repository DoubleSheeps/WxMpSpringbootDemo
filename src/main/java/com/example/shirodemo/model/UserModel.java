package com.example.shirodemo.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserModel implements Serializable {
    private Integer uid;
    private String account;//帐号
    private String name;//名称（昵称或者真实姓名，不同系统不同定义）
    private String password; //密码;
    private byte state;//用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定.

}