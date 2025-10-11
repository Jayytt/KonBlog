package com.kon.controller.user;

import com.kon.annotation.MySystemLog;
import com.kon.domain.entity.User;
import com.kon.enums.AppHttpCodeEnum;
import com.kon.exception.SystemException;
import com.kon.result.ResponseResult;
import com.kon.service.BlogLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 35238
 * @date 2023/7/22 0022 21:31
 */
@RestController
@Slf4j
@Tag(name = "客户端登录相关接口")
public class BlogLoginController {

    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    @MySystemLog(businessName = "用户登录")//自定义日志注解
    public ResponseResult login(@RequestBody User user) {
        //如果登录时没有传入用户名
        if(!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        log.info("登录成功：用户,{}", user);
        return blogLoginService.login(user);
    }

    @PostMapping("/logout")
    @Operation(summary = "退出登录")
    public  ResponseResult logout(){
        return blogLoginService.logout();
    }

}