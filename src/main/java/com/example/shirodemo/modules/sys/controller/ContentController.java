package com.example.shirodemo.modules.sys.controller;

import com.example.shirodemo.model.common.CommonReturnType;
import com.example.shirodemo.modules.sys.controller.VO.ResourceVO;
import com.example.shirodemo.modules.sys.dataobject.CourseContent;
import com.example.shirodemo.modules.sys.dataobject.CourseType;
import com.example.shirodemo.modules.sys.service.ContentService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "课程资源模块")
@RestController
@Slf4j
@RequestMapping("/api/content")
@AllArgsConstructor
public class ContentController {
    private final ContentService contentService;

    @ApiOperation("获取课程类型")
    @GetMapping("/type")
    public CommonReturnType<List<CourseType>> getType(){
        return CommonReturnType.create(contentService.getType());
    }

    @ApiOperation("获取课程目录")
    @GetMapping("/list/{id}")
    public CommonReturnType<List<CourseContent>> getContent(@ApiParam(value = "类别id", required = true)@PathVariable Integer id){
        return CommonReturnType.create(contentService.getContent(id));
    }
    @ApiOperation("获取课程资源")
//    @ApiResponse(code = 200, message = "处理成功",response = CommonReturnType.class)
    @GetMapping("/resource/{id}")
    public CommonReturnType<ResourceVO> getResource(@ApiParam(value = "课程id", required = true)@PathVariable Integer id){
        return CommonReturnType.create(contentService.getResource(id));
    }
}
