package com.example.shirodemo.error;

public enum EmBusinessError implements CommonError {
    UNKNOWN_ERROR(100000,"未知错误"),
    PARAMETER_VALIDATION_ERROR(100002,"参数有误"),
    USER_NOT_LOGIN(100003,"用户未登录"),
    USER_UNAUTHORIZED(100004,"权限不足"),
    USER_NOT_EXIST(100005,"用户不存在")

    ;

    private EmBusinessError(int errCode,String errMsg){
        this.errCode=errCode;
        this.errMsg=errMsg;
    }

    private int errCode;
    private String errMsg;

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
