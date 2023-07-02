package com.example.shirodemo.modules.sys.controller;

import com.example.shirodemo.Utils.TreeUtils;
import com.example.shirodemo.modules.sys.controller.VO.PermParam;
import com.example.shirodemo.modules.sys.controller.VO.Permission;
import com.example.shirodemo.modules.sys.controller.VO.RoleParam;
import com.example.shirodemo.model.RoleModel;
import com.example.shirodemo.model.common.CommonReturnType;
import com.example.shirodemo.modules.sys.service.RoleService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/role")
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
        List<Permission> permissionList = roleService.permissionList(roleId);
        System.out.println();
        System.out.println(permissionList.get(0).toString());
        System.out.println();
        List<Permission> leaf = TreeUtils.getLeaves(permissionList.get(0));
        System.out.println();
        System.out.println(leaf.toString());
        System.out.println();
        List<Integer> permissionIdList = new ArrayList<>();
        for (Permission permission:leaf){
            permissionIdList.add(permission.getId());
        }
        return CommonReturnType.create(permissionIdList);
    }

    @GetMapping("/perm/ids/all/{roleId}")
    @RequiresPermissions(logical = Logical.AND,value = {"role:view"})
    public CommonReturnType permAllKeys(@PathVariable(value = "roleId")Integer roleId){
        List<Integer> permissionIdList = roleService.permissionIdList(roleId);
        return CommonReturnType.create(permissionIdList);
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
