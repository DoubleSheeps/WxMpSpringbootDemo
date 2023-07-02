package com.example.shirodemo.modules.sys.service;

import com.example.shirodemo.model.StudentModel;
import com.example.shirodemo.modules.sys.dao.StudentDOMapper;
import com.example.shirodemo.modules.sys.dataobject.StudentDO;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class StudentService {
    private final StudentDOMapper studentDOMapper;

    public List<StudentModel> list(){
        List<StudentDO> studentDOList = studentDOMapper.selectAll();
        return studentDOList.stream().map(this::copy).collect(Collectors.toList());
    }

    public int add(StudentModel studentModel){
        if(studentModel!=null&&!haveStudent(studentModel.getName(), studentModel.getPhone())){
            StudentDO studentDO = new StudentDO();
            studentDO.setName(studentModel.getName());
            studentDO.setAge(studentModel.getAge());
            studentDO.setPhone(studentModel.getPhone());
            return studentDOMapper.insertSelective(studentDO);
        }
        return 0;
    }

    public boolean haveStudent(String name, String phone){
        StudentDO studentDO = studentDOMapper.selectByNameAndPhone(name,phone);
        return studentDO!=null;
    }

    public String getRemarkByPhone(String phone){
        List<StudentDO> studentDOList = studentDOMapper.selectByPhone(phone);
        StringBuilder stringBuilder = new StringBuilder();
        if(studentDOList!=null&&studentDOList.size()>0){
            for(Iterator<StudentDO> item = studentDOList.iterator(); item.hasNext(); ) {
                StudentDO studentDO = item.next();
                stringBuilder.append(studentDO.getName());
                if(item.hasNext()){
                    stringBuilder.append(",");
                }
            }
            return stringBuilder.toString();
        }
        return null;
    }

    public Integer bindStudent(String phone,String openId){
        List<StudentDO> studentDOList = studentDOMapper.selectByPhone(phone);
        if(studentDOList!=null&&studentDOList.size()>0){
            for(StudentDO studentDO:studentDOList) {
                studentDO.setSub((long) 1);
                studentDO.setOpenid(openId);
                studentDOMapper.updateByPrimaryKeySelective(studentDO);
            }
            return studentDOList.size();
        }
        return 0;
    }

    private StudentModel copy(StudentDO studentDO){
        if(studentDO==null){
            return null;
        }
        StudentModel studentModel = new StudentModel();
        BeanUtils.copyProperties(studentDO,studentModel);
        return studentModel;
    }
}
