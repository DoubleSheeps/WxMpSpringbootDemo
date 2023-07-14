package com.example.shirodemo.modules.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shirodemo.Utils.TreeUtils;
import com.example.shirodemo.modules.sys.controller.VO.Permission;
import com.example.shirodemo.modules.sys.dao.PermissionDOMapper;
import com.example.shirodemo.modules.sys.dao.RoleDOMapper;
import com.example.shirodemo.modules.sys.dao.UserDOMapper;
import com.example.shirodemo.modules.sys.dao.UserRoleDOMapper;
import com.example.shirodemo.modules.sys.dataobject.PermissionDO;
import com.example.shirodemo.modules.sys.dataobject.RoleDO;
import com.example.shirodemo.modules.sys.dataobject.UserDO;
import com.example.shirodemo.error.BusinessException;
import com.example.shirodemo.error.EmBusinessError;
import com.example.shirodemo.model.RoleModel;
import com.example.shirodemo.model.UserModel;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

public interface  UserService extends IService<UserDO> {

    UserModel findByAccount(String account);

    UserModel findByUID(Integer uid);

    int add(UserModel userModel);

    int update(UserModel userModel);

    int delete(Integer uid);

    List<UserModel> userList();
    List<UserModel> teacherList();


    List<RoleModel> roleListByUserId(Integer id);

    List<RoleModel> roleListByUserId2(Integer id);


    void roleAdd(Integer userId,Integer[] roleIds);



    List<Permission> permissionListByAccount(String account);

//    public List<String> selectRoleNameByUserName(String userName){
//        return userDOMapper.selectRoleNameByUserName(userName);
//    }
//
//    public List<String> selectPermsByUserName(String userName){
//        return userDOMapper.selectPermsByUserName(userName);
//    }

}
