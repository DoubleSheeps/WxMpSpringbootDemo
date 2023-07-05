package com.example.shirodemo.modules.sys.controller.VO;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CourseDateForm {
    private Integer courseId;
    private Integer teacherId;
    private Integer studentId;
    private String place;
    private List<CourseTime> dates;

    @Data
    public class CourseTime {
        private Date start;
        private Date end;
    }
}
