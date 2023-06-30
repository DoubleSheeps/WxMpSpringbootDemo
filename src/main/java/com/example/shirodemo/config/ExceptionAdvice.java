package com.example.shirodemo.config;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.shirodemo.error.BusinessException;
import com.example.shirodemo.error.EmBusinessError;
import com.example.shirodemo.model.common.CommonReturnType;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 异常控制处理器
 * @author dolyw.com
 * @date 2018/8/30 14:02
 */
@RestControllerAdvice
public class ExceptionAdvice {
    Map<String, Object> responseData = new HashMap<>();
    /**
     * 捕捉所有Shiro异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ShiroException.class)
    public CommonReturnType handle401(ShiroException e) {
        responseData.put("errCode", EmBusinessError.USER_UNAUTHORIZED.getErrCode());
        responseData.put("errMsg", "无权访问(Unauthorized):" + e.getMessage());
        return CommonReturnType.create(CommonReturnType.STATUS_FAIL, responseData);
    }

    /**
     * 单独捕捉Shiro(UnauthorizedException)异常
     * 该异常为访问有权限管控的请求而该用户没有所需权限所抛出的异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(UnauthorizedException.class)
    public CommonReturnType handle401(UnauthorizedException e) {
        responseData.put("errCode", EmBusinessError.USER_UNAUTHORIZED.getErrCode());
        responseData.put("errMsg", "无权访问(Unauthorized):当前Subject没有此请求所需权限(" + e.getMessage() + ")");
        return CommonReturnType.create(CommonReturnType.STATUS_FAIL, responseData);
    }

    /**
     * 单独捕捉Shiro(UnauthenticatedException)异常
     * 该异常为以游客身份访问有权限管控的请求无法对匿名主体进行授权，而授权失败所抛出的异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(UnauthenticatedException.class)
    public CommonReturnType handle401(UnauthenticatedException e) {
        responseData.put("errCode", EmBusinessError.USER_NOT_LOGIN.getErrCode());
        responseData.put("errMsg", "无权访问(Unauthorized):当前Subject是匿名Subject，请先登录(This subject is anonymous.)");
        return CommonReturnType.create(CommonReturnType.STATUS_FAIL, responseData);
    }

    /**
     * 单独捕捉Token过期(TokenExpiredException)异常
     * 该异常为以游客身份访问有权限管控的请求无法对匿名主体进行授权，而授权失败所抛出的异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(TokenExpiredException.class)
    public CommonReturnType handle401(TokenExpiredException e) {
        responseData.put("errCode", EmBusinessError.USER_NOT_LOGIN.getErrCode());
        responseData.put("errMsg", "Token已过期，请重新登录("+e.getMessage()+")");
        return CommonReturnType.create(CommonReturnType.STATUS_FAIL, responseData);
    }

    /**
     * 捕捉其他所有自定义异常
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BusinessException.class)
    public CommonReturnType handle401(BusinessException e) {
        responseData.put("errCode",e.getErrCode());
        responseData.put("errMsg", e.getMessage());
        return CommonReturnType.create(CommonReturnType.STATUS_FAIL, responseData);
    }

    /**
     * 捕捉校验异常(BindException)
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BindException.class)
    public CommonReturnType validException(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        Map<String, Object> result = this.getValidError(fieldErrors);
        responseData.put("errCode", EmBusinessError.PARAMETER_VALIDATION_ERROR.getErrCode());
        responseData.put("errMsg", result);
        return CommonReturnType.create(CommonReturnType.STATUS_FAIL, responseData);
    }

    /**
     * 捕捉校验异常(MethodArgumentNotValidException)
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonReturnType validException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        Map<String, Object> result = this.getValidError(fieldErrors);
        responseData.put("errCode", EmBusinessError.USER_UNAUTHORIZED.getErrCode());
        responseData.put("errMsg", result);
        return CommonReturnType.create(CommonReturnType.STATUS_FAIL, responseData);
    }



    /**
     * 捕捉404异常
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NoHandlerFoundException.class)
    public CommonReturnType handle(NoHandlerFoundException e) {
        responseData.put("errCode", EmBusinessError.UNKNOWN_ERROR.getErrCode());
        responseData.put("errMsg", "404:" + e.getMessage());
        return CommonReturnType.create(CommonReturnType.STATUS_FAIL, responseData);
    }

    /**
     * 捕捉其他所有异常
     * @param request
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public CommonReturnType globalException(HttpServletRequest request, Throwable ex) {
        responseData.put("errCode", EmBusinessError.UNKNOWN_ERROR.getErrCode());
        responseData.put("errMsg",  ex.getMessage());
        return CommonReturnType.create(CommonReturnType.STATUS_FAIL, responseData);
    }

    /**
     * 获取状态码
     * @param request
     * @return
     */
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

    /**
     * 获取校验错误信息
     * @param fieldErrors
     * @return
     */
    private Map<String, Object> getValidError(List<FieldError> fieldErrors) {
        Map<String, Object> result = new HashMap<String, Object>(16);
        List<String> errorList = new ArrayList<String>();
        StringBuffer errorMsg = new StringBuffer("校验异常(ValidException):");
        for (FieldError error : fieldErrors) {
            errorList.add(error.getField() + "-" + error.getDefaultMessage());
            errorMsg.append(error.getField()).append("-").append(error.getDefaultMessage()).append(".");
        }
        result.put("errorList", errorList);
        result.put("errorMsg", errorMsg);
        return result;
    }
}
