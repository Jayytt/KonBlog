package com.kon.controller.admin;

import com.kon.domain.dto.ChangeRoleStatusDTO;
import com.kon.domain.entity.Link;
import com.kon.domain.entity.Role;
import com.kon.result.ResponseResult;
import com.kon.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    //------------------------------查询角色列表---------------------------------------
    @GetMapping("/list")
    @Operation(summary = "查询角色列表")
    public ResponseResult list(Role role, Integer pageNum, Integer pageSize) {
        return roleService.selectRolePage(role,pageNum,pageSize);
    }

    //-----------------------------修改角色的状态--------------------------------------

    @PutMapping("/changeStatus")
    @Operation(summary = "修改角色状态")
    public ResponseResult changeStatus(@RequestBody ChangeRoleStatusDTO roleStatusDTO){
        Role role = new Role();
        role.setId(roleStatusDTO.getRoleId());
        role.setStatus(roleStatusDTO.getStatus());
        return ResponseResult.okResult(roleService.updateById(role));
    }

    //-------------------------------新增角色-----------------------------------------

    @PostMapping
    @Operation(summary = "新增角色")
    public ResponseResult add( @RequestBody Role role) {
        roleService.insertRole(role);
        return ResponseResult.okResult();

    }

//----------------------修改角色-根据角色id查询对应的角色-----------------------------

    @GetMapping(value = "/{roleId}")
    @Operation(summary = "根据角色id查询角色信息")
    public ResponseResult getInfo(@PathVariable Long roleId) {
        Role role = roleService.getById(roleId);
        return ResponseResult.okResult(role);
    }

    //-------------------------修改角色-保存修改好的角色信息------------------------------

    @PutMapping
    @Operation(summary = "修改角色")
    public ResponseResult edit(@RequestBody Role role) {
        roleService.updateRole(role);
        return ResponseResult.okResult();
    }

    //--------------------------------删除角色---------------------------------------

    @DeleteMapping("/{id}")
    @Operation(summary = "删除角色")
    public ResponseResult remove(@PathVariable("id") List<Long> id) {
        roleService.removeByIds(id);
        return ResponseResult.okResult();
    }
}