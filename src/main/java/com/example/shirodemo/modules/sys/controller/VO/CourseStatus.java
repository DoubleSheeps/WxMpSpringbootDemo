package com.example.shirodemo.modules.sys.controller.VO;

import lombok.Data;

@Data
public class CourseStatus {
    private Integer courseDateId;
    private String student;
    private String course;
    private String time;
    private String teacher;
    private Integer status;
    private Integer classNum;
    private String mark;
    private String imgUrl;
    private String place;
}
