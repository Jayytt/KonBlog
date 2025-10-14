package com.kon.controller.admin;

import com.kon.domain.entity.LoginUser;
import com.kon.domain.entity.Menu;
import com.kon.domain.entity.User;
import com.kon.domain.vo.AdminUserInfoVo;
import com.kon.domain.vo.RoutersVo;
import com.kon.domain.vo.UserInfoVo;
import com.kon.enums.AppHttpCodeEnum;
import com.kon.exception.SystemException;
import com.kon.result.ResponseResult;
import com.kon.service.MenuService;
import com.kon.service.RoleService;
import com.kon.service.SystemLoginService;
import com.kon.utils.BeanCopyUtils;
import com.kon.utils.RedisCache;
import com.kon.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController

@Tag(name = "后台用户登录相关接口")
public class LoginController {

    @Autowired
    private SystemLoginService systemLoginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/user/login")
    @Operation(summary = "用户登录")
    public ResponseResult login(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())) {
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return systemLoginService.login(user);
    }

    //查询(超级管理员|非超级管理员)的权限和角色信息

    @GetMapping("/getInfo")
    @Operation(summary = "查询超级管理员的权限和角色信息")
    public ResponseResult<AdminUserInfoVo> getInfo() {
        //获取当前登录的用户。SecurityUtils
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户id查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());

        //获取用户信息
        User user = loginUser.getUser();
        //BeanCopyUtils
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        //封装响应返回
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, roleKeyList, userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    //查询路由信息
    @GetMapping("/getRouters")
    @Operation(summary = "查询路由信息")
    public ResponseResult<RoutersVo> getRouters() {
        //获取用户id
        Long userId = SecurityUtils.getUserId();
        //根据id查询menu(权限菜单)。要求查询结果是tree的形式，也就是子父菜单树
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        return ResponseResult.okResult(new RoutersVo(menus));
    }

    //-----------------------------退出登录的接口(我们写在service比较好---------------------------------

    @Autowired
    private RedisCache redisCache;

    @PostMapping("/user/logout")
    public ResponseResult logout(){
        //退出登录
        return systemLoginService.logout();
    }
}