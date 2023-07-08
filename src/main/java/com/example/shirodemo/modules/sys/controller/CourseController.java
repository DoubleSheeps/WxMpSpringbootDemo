package com.example.shirodemo.modules.sys.controller;

import com.example.shirodemo.model.common.CommonReturnType;
import com.example.shirodemo.modules.sys.controller.VO.*;
import com.example.shirodemo.modules.sys.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;


@RestController
@Slf4j
@RequestMapping("/api/class")
public class CourseController {

    private final CourseService courseService;
    @Autowired
    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @PostMapping("/course")
    public CommonReturnType getCourse(@RequestBody CourseListForm form) throws ParseException {
        return CommonReturnType.create(courseService.getAllCourse(form.getStart(),form.getNum()));
    }


    @GetMapping("/course/list")
    public CommonReturnType listCourse(){
        return CommonReturnType.create(courseService.listCourse());
    }

    @PostMapping("/course/add")
    public CommonReturnType addCourse(@RequestBody CourseForm form){
        courseService.addCourse(form);
        return CommonReturnType.create("添加成功");
    }

    @PostMapping("/course/sign")
    public CommonReturnType courseSign(@RequestBody CourseSignForm form){
        courseService.courseSign(form);
        return CommonReturnType.create("推送上课状态成功");
    }

    @PostMapping("/course/date")
    public CommonReturnType addCourseDate(@RequestBody CourseDateForm form){
        courseService.addCourseDate(form);
        return CommonReturnType.create("添加成功");
    }

    @PostMapping("/course/wx/{openid}")
    public CommonReturnType listCourse(@PathVariable(value = "openid") String openid ,@RequestBody CourseTimeForm form){
        return CommonReturnType.create(courseService.getCourse(form.getStart(),form.getEnd(), openid));
    }

    @PostMapping("/course/wx/status")
    public CommonReturnType courseStatus(@RequestBody CourseStatusForm form){
        return CommonReturnType.create(courseService.getStatus(form));
    }

}
