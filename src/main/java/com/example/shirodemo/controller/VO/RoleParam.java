package com.example.shirodemo.controller.VO;

import lombok.Data;
import org.apache.ibatis.annotations.Param;
@Data
public class RoleParam {
    private String role;
    private String description;
}
