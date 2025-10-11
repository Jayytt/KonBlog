package com.kon.controller.admin;

import com.kon.domain.entity.User;
import com.kon.enums.AppHttpCodeEnum;
import com.kon.exception.SystemException;
import com.kon.result.ResponseResult;
import com.kon.service.SystemLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
@Tag(name = "用户登录相关接口")
public class LoginController {

    @Autowired
    private SystemLoginService systemLoginService;

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public ResponseResult login(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())) {
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return systemLoginService.login(user);
    }
}