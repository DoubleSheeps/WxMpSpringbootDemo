package com.example.shirodemo.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shirodemo.modules.sys.dataobject.CourseType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TypeTreeDOMapper extends BaseMapper<CourseType> {

}
