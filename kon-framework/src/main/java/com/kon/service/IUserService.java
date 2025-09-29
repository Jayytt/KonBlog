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
}
