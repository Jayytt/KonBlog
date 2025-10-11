package com.kon.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig {

    /**
     * 全局API信息配置
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("轻音博客接口文档")
                        .description("轻音博客项目 REST API 文档")
                        .version("v1.2.1")
                        .contact(new Contact()
                                .name("Yui")
                                .email("1838089345@qq.com")
                                .url("https://www.bilibili.com/bangumi/play/ss1172?spm_id_from=333.337.0.0"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.bilibili.com/bangumi/play/ss1172?spm_id_from=333.337.0.0")));
    }

    /**
     * 后台管理端接口分组
     */
    @Bean
    public GroupedOpenApi adminApi() {
        // 指定后台接口的包路径
        return GroupedOpenApi.builder()
                .group("管理端接口")
                .packagesToScan("com.kon.controller.admin")  // 扫描后台控制器包
                .build();
    }

    /**
     * 前台用户端接口分组
     */
    @Bean
    public GroupedOpenApi userApi() {
        // 指定前台接口的包路径
        return GroupedOpenApi.builder()
                .group("用户端接口")
                .packagesToScan("com.kon.controller.user")  // 扫描前台控制器包
                .build();
    }
}
