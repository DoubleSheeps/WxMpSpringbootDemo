package com.example.shirodemo.controller;

import com.example.shirodemo.controller.VO.PermParam;
import com.example.shirodemo.controller.VO.RoleParam;
import com.example.shirodemo.model.RoleModel;
import com.example.shirodemo.model.common.CommonReturnType;
import com.example.shirodemo.service.RoleService;
import com.example.shirodemo.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/role")
@RestController
@CrossOrigin(
        origins = {"*"},
        maxAge = 3600
)
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }

    @GetMapping
    @RequiresPermissions(logical = Logical.AND, value = {"role:view"})
    public CommonReturnType roleList(){
        List<RoleModel> roleModelList = roleService.roleList();
        return CommonReturnType.create(roleModelList);
    }

    @PostMapping("/add")
    @RequiresPermissions(logical = Logical.AND,value = {"role:add"})
    public CommonReturnType add(@RequestBody RoleParam roleParam){
        roleService.add(roleParam.getRole(),roleParam.getDescription());
        return CommonReturnType.create("添加角色成功");
    }

    @GetMapping("/perm/{roleId}")
    @RequiresPermissions(logical = Logical.AND,value = {"role:view"})
    public CommonReturnType perm(@PathVariable(value = "roleId")Integer roleId){
        return CommonReturnType.create(roleService.permissionList(roleId));
    }

    @GetMapping("/perm/ids/{roleId}")
    @RequiresPermissions(logical = Logical.AND,value = {"role:view"})
    public CommonReturnType permKeys(@PathVariable(value = "roleId")Integer roleId){
        return CommonReturnType.create(roleService.permissionIdList(roleId));
    }

    @GetMapping("/perm/all/{roleId}")
    @RequiresPermissions(logical = Logical.AND,value = {"role:perm"})
    public CommonReturnType permList(@PathVariable(value = "roleId")Integer roleId){
        return CommonReturnType.create(roleService.permissionList2(roleId));
    }


    @PostMapping("/perm/add")
    @RequiresPermissions(logical = Logical.AND, value = {"role:perm"})
    public CommonReturnType grantPerm(@RequestBody PermParam permParam){
        System.out.println("roleId:"+permParam.getRoleId()+",permIds:"+permParam.getPermIds().toString());
        roleService.permGrant(permParam.getRoleId(), permParam.getPermIds());
        return CommonReturnType.create("授权成功");
    }
}
