package com.kon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kon.domain.entity.Role;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
    List<String> selectRoleKeyByOtherUserId(Long userId);
}
