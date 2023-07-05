package com.example.shirodemo.modules.sys.controller.VO;

import com.example.shirodemo.modules.sys.dataobject.StudentCourseDateDO;
import lombok.Data;

@Data
public class CourseVO {
    private String name;
    private String time;
    private String year;
    private String month;
    private String day;
    private String student;
    private String teacher;
    private String place;
    private String content;

    public CourseVO(){

    }
    public CourseVO(StudentCourseDateDO studentCourseDateDO,String year, String month, String day,String time){
        this.teacher = studentCourseDateDO.getTeacher();
        this.name = studentCourseDateDO.getCourse();
        this.student = studentCourseDateDO.getStudent();
        this.place = studentCourseDateDO.getPlace();
        this.content = "Python基础语法教学";
        this.year = year;
        this.month = month;
        this.day = day;
        this.time = time;
    }
}
