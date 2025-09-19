package com.kon.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类：WebMvc + Knife4j（OpenAPI 3）接口文档
 */
@Configuration
@Slf4j
public class WebMvcConfiguration implements WebMvcConfigurer {

    /**
     * Knife4j（OpenAPI 3）文档配置
     * 替代原有的 Springfox Docket 配置
     */
    @Bean
    public OpenAPI customOpenAPI() {
        // 文档基本信息（标题、版本、描述、联系人）
        Info info = new Info()
                .title("轻音博客项目接口文档") // 文档标题
                .version("2.0") // 接口版本
                .description("轻音博客项目的后端API文档，包含用户、文章、评论等核心接口") // 文档描述
                // 可选：添加联系人信息
                .contact(new Contact()
                        .name("开发者名称")
                        .email("开发者邮箱@xxx.com")
                        .url("项目地址（如GitHub）"));

        // 返回 OpenAPI 配置（Knife4j 会自动识别该 Bean）
        return new OpenAPI().info(info);
    }

    /**
     * 静态资源映射：配置 Knife4j 文档页面和 WebJars 资源
     * 替代原有的 addResourceHandlers 方法（适配 Spring Boot 3.x）
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 1. Knife4j 文档页面（doc.html）
        registry.addResourceHandler("/doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        // 2. Knife4j 依赖的 WebJars 资源（如样式、脚本）
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        // （可选）如果项目有其他静态资源（如前端页面、图片），可在此添加映射
        // 例如：registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    /**
     * （可选）如果原配置有拦截器，保留此方法（无拦截器可删除）
     * 注意：需排除 Knife4j 文档路径，避免拦截器阻断文档访问
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 示例：如果有自定义拦截器，需排除 /doc.html 和 /webjars/**
        // registry.addInterceptor(yourInterceptor)
        //         .addPathPatterns("/**") // 拦截所有请求
        //         .excludePathPatterns("/doc.html", "/webjars/**"); // 排除文档路径
    }
}