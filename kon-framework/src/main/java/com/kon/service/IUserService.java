package com.kon.service;

import com.kon.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kon.result.ResponseResult;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 平泽唯
 * @since 2025-09-24
 */
public interface IUserService extends IService<User> {
    /*用户信息查询*/
    ResponseResult userInfo();

    /*更新用户信息*/
    ResponseResult updateUserInfo(User user);

    /*用户注册*/
    ResponseResult register(User user);

    /*分页查询用户列表*/
    ResponseResult selectUserPage(User user, Integer pageNum, Integer pageSize);

    //增加用户-②新增用户
    boolean checkUserNameUnique(String userName);
    boolean checkPhoneUnique(User user);
    boolean checkEmailUnique(User user);
    ResponseResult addUser(User user);

    //修改用户-②更新用户信息
    void updateUser(User user);
}
