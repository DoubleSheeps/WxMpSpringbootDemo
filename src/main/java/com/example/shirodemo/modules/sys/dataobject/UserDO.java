package com.example.shirodemo.modules.sys.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("user_info")
public class UserDO {
    @TableId(type = IdType.AUTO)
    private Integer uid;

    private String name;

    private String password;

    private Byte state;

    @TableField(insertStrategy = FieldStrategy.IGNORED)//account重复则不插入
    private String account;

}