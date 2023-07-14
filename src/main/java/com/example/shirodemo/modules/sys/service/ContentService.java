package com.example.shirodemo.modules.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.shirodemo.Utils.TreeUtils;
import com.example.shirodemo.modules.sys.controller.VO.ResourceVO;
import com.example.shirodemo.modules.sys.dao.ContentMapper;
import com.example.shirodemo.modules.sys.dao.ResourceMapper;
import com.example.shirodemo.modules.sys.dao.TypeTreeDOMapper;
import com.example.shirodemo.modules.sys.dataobject.CourseContent;
import com.example.shirodemo.modules.sys.dataobject.CourseType;
import com.example.shirodemo.modules.sys.dataobject.ResourceInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ContentService {
    private final ContentMapper contentMapper;
    private final ResourceMapper resourceMapper;
    private final TypeTreeDOMapper typeTreeDOMapper;

    public List<CourseType> getType(){
        List<CourseType> courseTypeList = new ArrayList<>();
        CourseType courseType = new CourseType();
        courseType.setId(0);
        courseTypeList.add(courseType);
        courseTypeList.addAll(typeTreeDOMapper.selectList(null));
        return TreeUtils.generateTrees(courseTypeList);
    }

    public List<CourseContent> getContent(Integer id){
        LambdaQueryWrapper<CourseContent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CourseContent::getTypeId,id);
        return contentMapper.selectList(queryWrapper);
    }

    public ResourceVO getResource(Integer id){
        LambdaQueryWrapper<ResourceInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ResourceInfo::getContentId,id);
        List<ResourceInfo> resourceInfos = resourceMapper.selectList(queryWrapper);
        ResourceVO resourceVO = new ResourceVO();
        List<ResourceInfo> mp4List = new ArrayList<>();
        resourceInfos.forEach(resourceInfo -> {
            if(resourceInfo.getType()==1){
                resourceVO.setPpt(resourceInfo);
            }else if(resourceInfo.getType()==2){
                resourceVO.setPdf(resourceInfo);
            }else if(resourceInfo.getType()==3){
                mp4List.add(resourceInfo);
            }
        });
        resourceVO.setVideo(mp4List);
        return resourceVO;
    }
}
