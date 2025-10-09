package com.kon;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Slf4j
@MapperScan("com.kon.mapper")
@EnableScheduling //开启定时任务
public class KonBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(KonBlogApplication.class, args);
        log.info("server start");
    }
}
