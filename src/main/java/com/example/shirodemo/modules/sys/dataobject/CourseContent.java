package com.example.shirodemo.modules.sys.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("course_content")
public class CourseContent {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer ind;
    private Integer typeId;
}
