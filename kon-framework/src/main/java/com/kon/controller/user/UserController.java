package com.kon.controller.user;


import com.kon.annotation.MySystemLog;
import com.kon.domain.entity.User;
import com.kon.result.ResponseResult;
import com.kon.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 平泽唯
 * @since 2025-09-24
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Tag(name = "用户相关接口")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/userInfo")
    @Operation(summary = "查询个人信息")
    @MySystemLog(businessName = "查询个人信息")//自定义日志注解
    public ResponseResult UserInfo() {
        log.info("查询个人信息");
        return userService.userInfo();
    }

    @PutMapping("/userInfo")
    @Operation(summary = "更新个人信息")
    @MySystemLog(businessName = "更新个人信息")//自定义日志注解
    public ResponseResult  updateUserInfo(@RequestBody User user){
        //更新个人信息
        return userService.updateUserInfo(user);
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册")
    @MySystemLog(businessName = "用户注册")//自定义日志注解
    public ResponseResult register(@RequestBody User user){
        log.info("用户注册:{}",user);
        return userService.register(user);
    }
}
