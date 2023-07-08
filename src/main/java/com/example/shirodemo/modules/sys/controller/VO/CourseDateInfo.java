package com.example.shirodemo.modules.sys.controller.VO;

import com.example.shirodemo.model.UserModel;
import com.example.shirodemo.modules.sys.dataobject.StudentDO;
import com.example.shirodemo.modules.sys.dataobject.UserDO;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class CourseDateInfo {
    private Integer id;
    private Date start;
    private Date end;
    private String course;
    private String place;
    private UserDO teacher;
    private List<StudentDO> students;
}
