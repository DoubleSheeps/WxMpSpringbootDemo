package com.example.shirodemo.modules.wx.controller;

import com.example.shirodemo.modules.sys.service.StudentService;
import com.example.shirodemo.modules.wx.config.WxMpProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;

/**
 * @Author: YoungSheep
 * @Date: 2023-06-15
 * @Description:
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/wx/my")
public class WxMyController {
    private final WxMpService wxService;

    private final WxMpProperties properties;

    private final StudentService studentService;


    /**
     * 请求微信服务器认证授权
     * 用户在页面填写需要绑定的信息（手机号）然后调用此接口，会构造一个请求微信服务器认证授权并携带需要绑定信息的超链接，进行重定向
     * @param phone 绑定的手机号
     * @return 重定向网址
     */
    @GetMapping("/authorize")
    public String authorize(@RequestParam("phone") String phone) {
        // 用户授权完成后的重定向链接，441aca54.ngrok.io为使用ngrok代理后的公网域名，该域名需要替换为在微信公众号后台设置的域名，否则请求微信服务器不成功，一般都是采用将本地服务代理映射到一个可以访问的公网进行开发调试
        String url = properties.getTestUrl()+"/wx/my/userInfo";
        String redirectURL = wxService.getOAuth2Service().buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(phone));
        log.info("【微信网页授权】获取code,redirectURL={}", redirectURL);
        return "redirect:" + redirectURL;
    }

    /**
     * 认证授权成功后返回微信用户信息
     *
     * @param code      微信授权成功后重定向地址后的code参数
     * @return
     * @throws Exception
     */
    /**
     * 构造请求微信服务器认证授权超链接时需要微信服务器重定向的接口，微信服务器认证授权成功后会自动调用此接口并携带用户wxOAuth2AccessToken信息和之前传入的绑定信息
     * @param code 构造用户wxOAuth2AccessToken信息的授权码
     * @param phone 用户传入的绑定信息
     * @return 重定向绑定之后的链接
     */
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String phone) throws Exception {
        log.info("【微信网页授权】code={}", code);
        log.info("【微信网页授权】state={}", phone);

        WxOAuth2AccessToken wxOAuth2AccessToken;
        try {
            // 获取accessToken
            wxOAuth2AccessToken = wxService.getOAuth2Service().getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权】{}", e);
            throw new Exception(e.getError().getErrorMsg());
        }
        // 根据accessToken获取openId
        String openId = wxOAuth2AccessToken.getOpenId();
        log.info("【微信网页授权】openId={}", openId);
        Integer num = studentService.bindStudent(phone,openId);
        if(num > 0){
            String remark = studentService.getRemarkByPhone(phone);
            wxService.getUserService().userUpdateRemark(openId, remark);
            String lang = "zh_CN"; //语言
            WxMpUser user = wxService.getUserService().userInfo(openId,lang);
            log.info("【微信网页授权】wxMpUser={}", user);
            // 获取用户信息
            WxOAuth2UserInfo wxUser = wxService.getOAuth2Service().getUserInfo(wxOAuth2AccessToken, null);
            log.info("【微信网页授权】wxMpUser={}", wxUser);
            // 刷新accessToken
            wxService.getOAuth2Service().refreshAccessToken(wxOAuth2AccessToken.getRefreshToken());
            // 验证accessToken是否有效
            wxService.getOAuth2Service().validateAccessToken(wxOAuth2AccessToken);
            return "redirect:"+properties.getWebUrl()+"/#/bindSuccess?names="+URLEncoder.encode(remark);
        }else {
            String msg = "没有找到孩子的信息，请确认填写的手机号是购买课程时留下的手机号";
            return "redirect:"+properties.getWebUrl()+"/#/bindFail?msg="+URLEncoder.encode(msg);
        }
    }
}
