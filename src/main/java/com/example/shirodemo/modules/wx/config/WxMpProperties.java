package com.example.shirodemo.modules.wx.config;

import com.example.shirodemo.Utils.JsonUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * wechat mp properties
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Data
@ConfigurationProperties(prefix = "wx.mp")
public class WxMpProperties {
    /**
     * 测试网址
     */
    private String testUrl;
    /**
     * 前端跳转网址
     */
    private String webUrl;

    /**
     * 设置微信公众号的appid
     */
    private String appId;

    /**
     * 设置微信公众号的app secret
     */
    private String secret;

    /**
     * 设置微信公众号的token
     */
    private String token;

    /**
     * 设置微信公众号的EncodingAESKey
     */
    private String aesKey;

    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }
}
