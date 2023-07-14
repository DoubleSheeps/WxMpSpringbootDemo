package com.example.shirodemo.model.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *接口统一返回类型
 */
@Data
public class CommonReturnType<T> {
    /**
     * 表明对应请求的处理结果是"success"或者"fail"
     * 若status=success,则返回前端需要的json数据
     * 若status=fail,则data内使用通用的错误码格式
     */
    @ApiModelProperty(value = "返回处理结果",example = "success/fail")
    private String status;

    public static final String STATUS_SUCCESS = "success";
    public static final String STATUS_FAIL = "fail";

    @ApiModelProperty(value = "返回处理结果数据")
    private T data;

    public static <T> CommonReturnType<T> create(String status,T result){
        CommonReturnType<T> type = new CommonReturnType<>();
        type.setStatus(status);
        type.setData(result);
        return type;
    }

    public static <T> CommonReturnType<T> create(T result){
        return CommonReturnType.create(CommonReturnType.STATUS_SUCCESS,result);
    }
}
