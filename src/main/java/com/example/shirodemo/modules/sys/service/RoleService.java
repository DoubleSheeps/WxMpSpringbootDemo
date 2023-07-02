package com.example.shirodemo.modules.sys.service;

import com.example.shirodemo.Utils.TreeUtils;
import com.example.shirodemo.Utils.UserUtil;
import com.example.shirodemo.config.shiro.UserRealm;
import com.example.shirodemo.modules.sys.controller.VO.Permission;
import com.example.shirodemo.modules.sys.dao.PermissionDOMapper;
import com.example.shirodemo.modules.sys.dao.RoleDOMapper;
import com.example.shirodemo.modules.sys.dao.RolePermissionDOMapper;
import com.example.shirodemo.modules.sys.dao.UserRoleDOMapper;
import com.example.shirodemo.modules.sys.dataobject.PermissionDO;
import com.example.shirodemo.modules.sys.dataobject.RoleDO;
import com.example.shirodemo.error.BusinessException;
import com.example.shirodemo.error.EmBusinessError;
import com.example.shirodemo.model.RoleModel;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private final RoleDOMapper roleDOMapper;
    private final PermissionDOMapper permissionDOMapper;
    private final RolePermissionDOMapper rolePermissionDOMapper;
    private final UserRoleDOMapper userRoleDOMapper;
    private final UserRealm userRealm;
    private final UserUtil userUtil;

    @Autowired
    public RoleService(RoleDOMapper roleDOMapper, PermissionDOMapper permissionDOMapper, RolePermissionDOMapper rolePermissionDOMapper, UserRoleDOMapper userRoleDOMapper, UserRealm userRealm,UserUtil userUtil){
        this.roleDOMapper = roleDOMapper;
        this.permissionDOMapper = permissionDOMapper;
        this.rolePermissionDOMapper = rolePermissionDOMapper;
        this.userRoleDOMapper = userRoleDOMapper;
        this.userRealm = userRealm;
        this.userUtil = userUtil;
    }

    public List<RoleModel> roleList(){
        List<RoleDO> roleDOList = roleDOMapper.selectALL();
        return roleDOList.stream().map(this::copy).collect(Collectors.toList());
    }

    public List<Permission> permissionList(Integer roleId){
        List<Permission> permissionList = new ArrayList<>();
        Permission permissionRoot = new Permission();
        permissionRoot.setId(0);
        permissionRoot.setPermission("root");
        permissionRoot.setLabel("权限");
        permissionList.add(permissionRoot);
        Set<Permission> permissionSet = new TreeSet<>((o1, o2) -> o1.getId().compareTo(o2.getId()));
        // 根据角色查询权限
        List<PermissionDO> permissionDOList = permissionDOMapper.selectByRoleId(roleId);
        for (PermissionDO permissionDO : permissionDOList) {
            if (permissionDO != null) {
                // 添加权限
                Permission permission = new Permission();
                permission.setId(permissionDO.getId());
                permission.setParentId(permissionDO.getParentId().intValue());
                permission.setPermission(permissionDO.getPermission());
                permission.setLabel(permissionDO.getName());
                if(permissionSet.add(permission)){
                    permissionList.add(permission);
                }
            }
        }
        return TreeUtils.generateTrees(permissionList);
    }

    public List<Integer> permissionIdList(Integer roleId){
        List<Integer> permissionIdList = new ArrayList<>();
        permissionIdList.add(0);
        // 根据角色查询权限
        List<PermissionDO> permissionDOList = permissionDOMapper.selectByRoleId(roleId);
        for (PermissionDO permissionDO : permissionDOList) {
            if (permissionDO != null) {
                permissionIdList.add(permissionDO.getId());
            }
        }
        return permissionIdList;
    }

    public List<Permission> permissionList2(Integer roleId){
        Subject subject = SecurityUtils.getSubject();
        String account = userUtil.getAccount();
        List<Permission> permissionList = new ArrayList<>();
        Permission permissionRoot = new Permission();
        permissionRoot.setId(0);
        permissionRoot.setPermission("root");
        permissionRoot.setLabel("权限");
        permissionRoot.setDisabled(true);
        permissionList.add(permissionRoot);
        Set<Permission> permissionSet = new TreeSet<>((o1, o2) -> o1.getId().compareTo(o2.getId()));
        // 根据角色查询权限
        List<PermissionDO> permissionDOList = permissionDOMapper.selectAll();
        List<Integer> permIds = permissionIdList(roleId);
        for (PermissionDO permissionDO : permissionDOList) {
            if (permissionDO != null) {
                // 添加权限
                Permission permission = new Permission();
                permission.setId(permissionDO.getId());
                permission.setParentId(permissionDO.getParentId().intValue());
                permission.setPermission(permissionDO.getPermission());
                permission.setLabel(permissionDO.getName());
                if(!subject.isPermitted(permission.getPermission())){
                    permission.setDisabled(true);
                }
                if((!account.equals("admin"))&&(permission.getPermission().equals("role:view")||permission.getPermission().equals("role:add")||permission.getPermission().equals("role:perm"))){
                    permission.setDisabled(true);
                }
                if(permissionSet.add(permission)){
                    for(Integer id:permIds){
                        if(permission.getId() == id){
                            permission.setDisabled(true);
                            break;
                        }
                    }
                    permissionList.add(permission);
                }
            }
        }
        return TreeUtils.generateTrees(permissionList);
    }

    public void add(String role, String description){
        RoleDO roleDO1 = roleDOMapper.selectByRole(role);
        if(roleDO1!=null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"角色已存在");
        }
        RoleDO roleDO = new RoleDO();
        roleDO.setRole(role);
        roleDO.setAvailable(true);
        roleDO.setDescription(description);
        if(roleDOMapper.insertSelective(roleDO)!=1){
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,"插入数据库出错");
        }
    }

    @Transactional
    public void permGrant(Integer roleId,Integer[] permIds){
        Subject subject = SecurityUtils.getSubject();
        RoleDO roleDO = roleDOMapper.selectByPrimaryKey(roleId);
        if(roleDO==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"角色id有误");
        }
        for(Integer permId:permIds){
            PermissionDO permissionDO = permissionDOMapper.selectByPrimaryKey(permId);
            if(permissionDO==null){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"权限id有误");
            }
            if(!subject.isPermitted(permissionDO.getPermission())){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"没有"+permissionDO.getPermission()+"权限，无法授权给其他用户");
            }
        }
        rolePermissionDOMapper.insertRolePerms(roleId,permIds);
        clearRoleAuthCache(roleId);
    }

    private void clearRoleAuthCache(Integer roleId) {
        // 获取该角色下的所有用户.
        List<Integer> userIds = userRoleDOMapper.selectUserIdByRoleId(roleId);
        if(userIds!=null&&userIds.size()>0){
            // 将该角色下所有用户的认证信息缓存清空, 以到达刷新认证信息的目的.
            for (Integer userId : userIds) {
                userRealm.clearAuthCacheByUserId(userId);
            }
        }
    }

    private RoleModel copy(RoleDO roleDO){
        if(roleDO==null){
            return null;
        }else {
            RoleModel roleModel = new RoleModel();
            BeanUtils.copyProperties(roleDO, roleModel);
            return roleModel;
        }
    }
}
