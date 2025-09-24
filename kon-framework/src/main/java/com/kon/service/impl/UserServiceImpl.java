package com.kon.service.impl;

import com.kon.domain.entity.User;
import com.kon.mapper.UserMapper;
import com.kon.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 平泽唯
 * @since 2025-09-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
