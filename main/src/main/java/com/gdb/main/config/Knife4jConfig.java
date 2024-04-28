package com.gdb.main.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mr.Gui
 */
@Slf4j
@Configuration
public class Knife4jConfig {
    @Bean
    public OpenAPI myOpenAPI() {
        // todo 为什么这里会执行两次呢
        log.info("接口文档生成了");
        return new OpenAPI()
                // 接口文档标题
                .info(new Info().title("一览无余 API")
                        // 接口文档简介
                        .description("一览无余")
                        .termsOfService("https://blog.csdn.net/weixin_65032328?type=blog")
                        // 接口文档版本
                        .version("v1.0.0")
                        // 接口文档协议
                        .license(new License()
                                .name("许可协议")
                                .url("https://blog.csdn.net/weixin_65032328?type=blog"))
                        // 开发者联系方式
                        .contact(new Contact()
                                .name("迷人的小宝")
                                .email("dingbaogui8@gmail.com")
                                .url("https://blog.csdn.net/weixin_65032328?type=blog")))
                .externalDocs(new ExternalDocumentation()
                        .description("小宝945博客")
                        .url("https://blog.csdn.net/weixin_65032328?type=blog"));
    }
}
