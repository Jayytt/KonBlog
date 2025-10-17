package com.kon.controller.admin;

import com.huanf.vo.MenuVo;
import com.kon.domain.entity.Menu;
import com.kon.domain.vo.MenuTreeVo;
import com.kon.domain.vo.RoleMenuTreeSelectVo;
import com.kon.result.ResponseResult;
import com.kon.service.MenuService;
import com.kon.utils.BeanCopyUtils;
import com.kon.utils.SystemConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 35238
 * @date 2023/8/10 0010 10:54
 */
@RestController
@RequestMapping("/system/menu")
@Tag(name = "后台菜单管理相关接口")
public class MenuController {

    @Autowired
    private MenuService menuService;

    //---------------------------------查询菜单列表--------------------------------------

    @GetMapping("/list")
    @Operation(summary = "查询菜单列表")
    public ResponseResult list(Menu menu) {
        List<Menu> menus = menuService.selectMenuList(menu);
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menus, MenuVo.class);
        return ResponseResult.okResult(menuVos);
    }
    //-----------------------------------新增菜单---------------------------------------


    @PostMapping
    @Operation(summary = "新增菜单")
    public ResponseResult add(@RequestBody Menu menu) {
        menuService.save(menu);
        return ResponseResult.okResult();
    }

    //-----------------------------------修改菜单---------------------------------------

    @GetMapping(value = "/{menuId}")
    @Operation(summary = "根据菜单id查询菜单信息")
    //①先查询根据菜单id查询对应的权限菜单
    public ResponseResult getInfo(@PathVariable Long menuId) {
        return ResponseResult.okResult(menuService.getById(menuId));
    }

    @PutMapping
    @Operation(summary = "修改菜单")
    //②然后才是更新菜单
    public ResponseResult edit(@RequestBody Menu menu) {
        if (menu.getId().equals(menu.getParentId())) {
            return ResponseResult.errorResult(500, "修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        menuService.updateById(menu);
        return ResponseResult.okResult();
    }

    //-----------------------------------删除菜单---------------------------------------

    @DeleteMapping("/{menuId}")
    @Operation(summary = "删除菜单")
    public ResponseResult remove(@PathVariable("menuId") Long menuId) {
        if (menuService.hasChild(menuId)) {
            return ResponseResult.errorResult(500,"存在子菜单不允许删除");
        }
        menuService.removeById(menuId);
        return ResponseResult.okResult();
    }
    //----------------------------新增角色-获取菜单下拉树列表-------------------------------


    @GetMapping("/treeselect")
    @Operation(summary = "获取菜单下拉树列表")
    public ResponseResult treeselect() {
        //复用之前的selectMenuList方法。方法需要参数，参数可以用来进行条件查询，而这个方法不需要条件，所以直接new Menu()传入
        List<Menu> menus = menuService.selectMenuList(new Menu());
        List<MenuTreeVo> options =  SystemConverter.buildMenuSelectTree(menus);
        return ResponseResult.okResult(options);
    }

    //---------------------修改角色-根据角色id查询对应角色菜单列表树--------------------------

    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    @Operation(summary = "根据角色id查询对应角色菜单列表树")
    public ResponseResult roleMenuTreeSelect(@PathVariable("roleId") Long roleId) {
        List<Menu> menus = menuService.selectMenuList(new Menu());
        List<Long> checkedKeys = menuService.selectMenuListByRoleId(roleId);
        List<MenuTreeVo> menuTreeVos = SystemConverter.buildMenuSelectTree(menus);
        RoleMenuTreeSelectVo vo = new RoleMenuTreeSelectVo(checkedKeys,menuTreeVos);
        return ResponseResult.okResult(vo);
    }
}