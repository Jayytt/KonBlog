package com.kon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kon.domain.entity.Menu;

import java.util.List;

/**
 * @author 35238
 * @date 2023/8/4 0004 13:24
 */
public interface MenuService extends IService<Menu> {
    //查询用户的权限信息
    List<String> selectPermsByUserId(Long id);

    //查询用户路由信息，也就是查询权限菜单
    List<Menu> selectRouterMenuTreeByUserId(Long userId);
}