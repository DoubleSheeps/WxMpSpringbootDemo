package com.example.shirodemo.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shirodemo.modules.sys.dataobject.CourseContent;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContentMapper extends BaseMapper<CourseContent> {
}
