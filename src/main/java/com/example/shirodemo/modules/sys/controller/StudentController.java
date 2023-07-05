package com.example.shirodemo.modules.sys.controller;

import com.example.shirodemo.Utils.AesCipherUtil;
import com.example.shirodemo.Utils.StringUtil;
import com.example.shirodemo.error.BusinessException;
import com.example.shirodemo.error.EmBusinessError;
import com.example.shirodemo.model.StudentModel;
import com.example.shirodemo.model.UserModel;
import com.example.shirodemo.model.common.CommonReturnType;
import com.example.shirodemo.model.common.Constant;
import com.example.shirodemo.modules.sys.service.StudentService;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/student")
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/list")
    @RequiresPermissions(logical = Logical.OR ,value = {"exp:user","class:add"})
    public CommonReturnType list(){
        return CommonReturnType.create(studentService.list());
    }

    @PostMapping("/add")
    @RequiresPermissions(logical = Logical.AND, value = {"exp:student"})
    public CommonReturnType add(@RequestBody StudentModel studentModel) {
        if(studentService.add(studentModel)!=1){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"新增失败，参数有误！");
        }
        return CommonReturnType.create("新增成功(Insert Success)");
    }

}
