package com.example.shirodemo.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shirodemo.Utils.TreeUtils;
import com.example.shirodemo.error.BusinessException;
import com.example.shirodemo.error.EmBusinessError;
import com.example.shirodemo.model.RoleModel;
import com.example.shirodemo.model.UserModel;
import com.example.shirodemo.modules.sys.controller.VO.Permission;
import com.example.shirodemo.modules.sys.dao.PermissionDOMapper;
import com.example.shirodemo.modules.sys.dao.RoleDOMapper;
import com.example.shirodemo.modules.sys.dao.UserDOMapper;
import com.example.shirodemo.modules.sys.dao.UserRoleDOMapper;
import com.example.shirodemo.modules.sys.dataobject.PermissionDO;
import com.example.shirodemo.modules.sys.dataobject.RoleDO;
import com.example.shirodemo.modules.sys.dataobject.UserDO;
import com.example.shirodemo.modules.sys.service.UserService;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserDOMapper, UserDO> implements UserService {
    private final UserDOMapper userDOMapper;
    private final RoleDOMapper roleDOMapper;
    private final PermissionDOMapper permissionDOMapper;
    private final UserRoleDOMapper userRoleDOMapper;

    public UserModel findByAccount(String account){
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getAccount,account);
        UserDO userDO = userDOMapper.selectOne(queryWrapper);
        if(userDO!=null){
            return copy(userDO);
        }
        return null;
    }


    public UserModel findByUID(Integer uid){
//        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(UserDO::getUid,uid);
        UserDO userDO = userDOMapper.selectById(uid);
        if(userDO!=null){
            return copy(userDO);
        }
        return null;
    }
    @Transactional
    public int add(UserModel userModel){
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel,userDO);
        userDO.setState((byte)1);
        return  userDOMapper.insert(userDO);
    }
    @Transactional
    public int update(UserModel userModel){
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel,userDO);
//        LambdaUpdateWrapper<UserDO> updateWrapper = new LambdaUpdateWrapper<>();
//        updateWrapper.eq(UserDO::getUid,userModel.getUid());
        return  userDOMapper.updateById(userDO);
    }
    @Transactional
    public int delete(Integer uid){
        LambdaUpdateWrapper<UserDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserDO::getUid,uid);
        updateWrapper.set(UserDO::getState,0);
        return userDOMapper.update(null,updateWrapper);
    }

    public List<UserModel> userList(){
        List<UserDO> userDOList = userDOMapper.selectList(null);
        return userDOList.stream().map(this::copy).collect(Collectors.toList());
    }
    public List<UserModel> teacherList(){
        return userDOMapper.selectTeacher();
    }


    public List<RoleModel> roleListByUserId(Integer id){
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getUid,id);
        UserDO userDO = userDOMapper.selectOne(queryWrapper);
        if(userDO==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户不存在");
        }
        List<RoleDO> roleDOList = roleDOMapper.selectByUserAccount(userDO.getAccount());
        return roleDOList.stream().map(this::copy).collect(Collectors.toList());
    }

    public List<RoleModel> roleListByUserId2(Integer id){
        List<RoleModel> roleModelList = new ArrayList<>();
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getUid,id);
        UserDO userDO = userDOMapper.selectOne(queryWrapper);
        if(userDO==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户不存在");
        }
        List<RoleDO> roleDOList = roleDOMapper.selectALL();
        List<RoleDO> userRoleDOList = roleDOMapper.selectByUserAccount(userDO.getAccount());
        for (RoleDO roleDO:roleDOList){
            RoleModel roleModel = copy(roleDO);
            for(RoleDO roleDO1:userRoleDOList){
                if(roleDO1.getId()==roleDO.getId()){
                    roleModel.setDisabled(true);
                }
            }
            roleModelList.add(roleModel);
        }
        return roleModelList;
    }

    @Transactional
    public void roleAdd(Integer userId,Integer[] roleIds){
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getUid,userId);
        UserDO userDO = userDOMapper.selectOne(queryWrapper);
        if(userDO==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户不存在");
        }
        for (Integer roleId:roleIds){
            RoleDO roleDO = roleDOMapper.selectByPrimaryKey(roleId);
            if(roleDO==null){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"角色id有误");
            }
        }
        userRoleDOMapper.insertUserRole(userId,roleIds);
    }



    public List<Permission> permissionListByAccount(String account){
        // 查询用户角色
        List<RoleDO> roleDOList = roleDOMapper.selectByUserAccount(account);
        if(roleDOList==null||roleDOList.size()<=0){
            return null;
        }
        List<Permission> permissionList = new ArrayList<>();
        Permission permissionRoot = new Permission();
        permissionRoot.setId(0);
        permissionRoot.setPermission("root");
        permissionList.add(permissionRoot);
        Set<Permission> permissionSet = new TreeSet<>((o1, o2) -> o1.getId().compareTo(o2.getId()));
        for (RoleDO roleDO : roleDOList) {
            if (roleDO != null) {
                // 根据用户角色查询权限
                List<PermissionDO> permissionDOList = permissionDOMapper.selectByRoleId(roleDO.getId());
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
            }
        }
        //System.out.println(permissionList.toString());
        return TreeUtils.generateTrees(permissionList);
    }

//    public List<String> selectRoleNameByUserName(String userName){
//        return userDOMapper.selectRoleNameByUserName(userName);
//    }
//
//    public List<String> selectPermsByUserName(String userName){
//        return userDOMapper.selectPermsByUserName(userName);
//    }

    private UserModel copy(UserDO userDO){
        if(userDO==null){
            return null;
        }else {
            UserModel userModel = new UserModel();
            BeanUtils.copyProperties(userDO, userModel);
            return userModel;
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
