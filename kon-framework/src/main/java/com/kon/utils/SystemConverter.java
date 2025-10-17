package com.kon.utils;

import com.kon.domain.entity.Menu;
import com.kon.domain.vo.MenuTreeVo;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 35238
 * @date 2023/8/10 0010 15:17
 */
//新增角色-获取菜单下拉树列表
    /*将数据库中查询到的 “扁平结构” 菜单数据（每个菜单只记录自身 ID 和父 ID），转换为 “层级结构” 的 MenuTreeVo 列表，
    方便前端以树形下拉框的形式展示菜单（例如角色新增时，选择该角色可访问的菜单权限）*/
public class SystemConverter {

    private SystemConverter() {}

    // 构建菜单下拉树结构
    public static List<MenuTreeVo> buildMenuSelectTree(List<Menu> menus) {
        List<MenuTreeVo> MenuTreeVos = menus.stream()
                .map(m -> new MenuTreeVo(m.getId(), m.getMenuName(), m.getParentId(), null))
                .collect(Collectors.toList());
        List<MenuTreeVo> options = MenuTreeVos.stream()
                .filter(o -> o.getParentId().equals(0L))
                .map(o -> o.setChildren(getChildList(MenuTreeVos, o)))
                .collect(Collectors.toList());


        return options;
    }


    /**
     * 得到子节点列表
     */
    private static List<MenuTreeVo> getChildList(List<MenuTreeVo> list, MenuTreeVo option) {
        List<MenuTreeVo> options = list.stream()
                .filter(o -> Objects.equals(o.getParentId(), option.getId()))
                .map(o -> o.setChildren(getChildList(list, o)))
                .collect(Collectors.toList());
        return options;

    }
}