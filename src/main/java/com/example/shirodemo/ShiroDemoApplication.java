package com.example.shirodemo;

import com.example.shirodemo.Utils.JedisUtil;
import com.example.shirodemo.Utils.JwtUtil;
import com.example.shirodemo.Utils.StringUtil;
import com.example.shirodemo.error.BusinessException;
import com.example.shirodemo.error.EmBusinessError;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication(scanBasePackages = {"com.example.shirodemo"})
@RestController
@MapperScan("com.example.shirodemo.modules.sys.dao")
public class ShiroDemoApplication {

    private static final Logger logger = LoggerFactory.getLogger(ShiroDemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ShiroDemoApplication.class, args);
        String key = String.valueOf(System.currentTimeMillis());
        JedisUtil.setJson(key,"true");
        if(StringUtil.isBlank(JedisUtil.getJson(key))){
            logger.error("Redis连接失败！");
        }else {
            logger.info("Redis连接成功！");
        }
    }

}
