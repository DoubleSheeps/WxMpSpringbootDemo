package com.example.shirodemo.config.swagger;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import lombok.AllArgsConstructor;
import springfox.documentation.service.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@EnableSwagger2WebMvc
@Configuration
@AllArgsConstructor
public class Swagger2Config {

    /*引入Knife4j提供的扩展类*/
    private final OpenApiExtensionResolver openApiExtensionResolver;

    @Bean
    public Docket createRestApi() {
        String groupName = "web模块";
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(groupName)
                .apiInfo(apiInfo())
                .select()
                //为当前包路径,控制器类包
                .apis(RequestHandlerSelectors.basePackage("com.example.shirodemo.modules.sys.controller"))
                .paths(PathSelectors.any())
                .build()
                //赋予插件体系
                .extensions(openApiExtensionResolver.buildExtensions(groupName));
    }

    @Bean
    public Docket createRestApi2() {
        String groupName = "wx模块";
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(groupName)
                .apiInfo(apiInfo())
                .select()
                //为当前包路径,控制器类包
                .apis(RequestHandlerSelectors.basePackage("com.example.shirodemo.modules.wx.controller"))
                .paths(PathSelectors.any())
                .build()
                //赋予插件体系
                .extensions(openApiExtensionResolver.buildExtensions(groupName));
    }

    //构建 api文档的详细信息函数
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("API接口文档")
                //创建人
                .contact(new Contact("Young","",
                        "568462483@qq.com"))
                //版本号
                .version("1.0")
                //描述
                .description("天天教育平台API接口文档，分为web端接口文档和微信页面端接口文档，左上角可切换分组查看")
                .build();
    }
}
