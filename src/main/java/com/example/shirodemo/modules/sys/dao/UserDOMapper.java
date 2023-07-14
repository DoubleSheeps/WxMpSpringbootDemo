package com.example.shirodemo.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shirodemo.model.UserModel;
import com.example.shirodemo.modules.sys.dataobject.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
public interface UserDOMapper extends BaseMapper<UserDO> {


    List<UserDO> selectALL();

    List<UserModel> selectTeacher();

}