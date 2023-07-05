package com.example.shirodemo.modules.sys.dataobject;

import lombok.Data;

import java.util.Date;
@Data
public class StudentCourseDateDO {

    private String course;

    private Date startTime;

    private String teacher;

    private String place;

    private Date endTime;

    private String student;
}
