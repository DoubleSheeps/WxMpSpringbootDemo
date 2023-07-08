package com.example.shirodemo.modules.sys.controller.VO;

import lombok.Data;

import java.util.List;
@Data
public class CourseDateList {
    private Integer index;
    private List<CourseDateInfo> courses;
}
