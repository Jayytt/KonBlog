package com.kon;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
@MapperScan("com.kon.mapper")
public class KonBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(KonBlogApplication.class, args);
        log.info("server start");
    }
}
