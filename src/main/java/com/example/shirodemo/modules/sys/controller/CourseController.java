package com.example.shirodemo.modules.sys.controller;

import com.example.shirodemo.model.common.CommonReturnType;
import com.example.shirodemo.modules.sys.controller.VO.ActivityForm;
import com.example.shirodemo.modules.sys.controller.VO.CourseDateForm;
import com.example.shirodemo.modules.sys.controller.VO.CourseForm;
import com.example.shirodemo.modules.sys.dataobject.StudentCourseDateDO;
import com.example.shirodemo.modules.sys.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/class")
public class CourseController {
    @Autowired
    private CourseService courseService;


    @GetMapping("/course/list")
    public CommonReturnType listCourse(){
        return CommonReturnType.create(courseService.listCourse());
    }

    @PostMapping("/course/add")
    public CommonReturnType addCourse(@RequestBody String name){
        courseService.addCourse(name);
        return CommonReturnType.create("添加成功");
    }

    @PostMapping("/course/date")
    public CommonReturnType addCourseDate(@RequestBody CourseDateForm form){
        courseService.addCourseDate(form);
        return CommonReturnType.create("添加成功");
    }

    @PostMapping("/course/wx/{openid}")
    public CommonReturnType listCourse(@PathVariable(value = "openid") String openid ,@RequestBody CourseForm form){
        return CommonReturnType.create(courseService.getCourse(form.getStart(),form.getEnd(), openid));
    }

}
