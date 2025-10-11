package com.kon.service;

import com.kon.domain.entity.User;
import com.kon.result.ResponseResult;

/**
 * @author 35238
 * @date 2023/7/22 0022 21:38
 */
public interface SystemLoginService {

    //登录
    ResponseResult login(User user);

}