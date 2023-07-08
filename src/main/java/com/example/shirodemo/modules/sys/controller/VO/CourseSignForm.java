package com.example.shirodemo.modules.sys.controller.VO;

import lombok.Data;

import java.util.Date;

@Data
public class CourseSignForm {
    private Integer courseDateId;
    private Integer studentId;
    private String courseName;
    private Date start;
    private Date end;
    private Integer teacherId;
    private Integer status;
    private Integer classNum;
    private String mark;
    private String imgUrl;
    private String place;
}
