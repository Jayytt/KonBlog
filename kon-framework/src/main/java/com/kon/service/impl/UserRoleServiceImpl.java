package com.kon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kon.domain.entity.UserRole;
import com.kon.mapper.UserRoleMapper;
import com.kon.service.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}
