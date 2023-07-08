package com.example.shirodemo.modules.sys.service;

import com.example.shirodemo.Utils.DateStringUtil;
import com.example.shirodemo.error.BusinessException;
import com.example.shirodemo.error.EmBusinessError;
import com.example.shirodemo.model.UserModel;
import com.example.shirodemo.modules.sys.controller.VO.*;
import com.example.shirodemo.modules.sys.dao.*;
import com.example.shirodemo.modules.sys.dataobject.*;
import com.example.shirodemo.modules.wx.service.TemplateMessageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CourseService {
    private final CourseInfoDOMapper courseInfoDOMapper;
    private final CourseStudentDOMapper courseStudentDOMapper;
    private final CourseDateInfoDOMapper courseDateInfoDOMapper;
    private final StudentDOMapper studentDOMapper;
    private final UserDOMapper userDOMapper;
    private final TemplateMessageService templateMessageService;
    private final CourseStudentInfoDOMapper courseStudentInfoDOMapper;

    Logger log = LoggerFactory.getLogger(this.getClass());

    public List<CourseInfoDO> listCourse(){
        return courseInfoDOMapper.selectAll();
    }

    public void courseSign(CourseSignForm form){
        Integer csId = courseStudentDOMapper.getPrimaryKey(form.getCourseDateId(), form.getStudentId());
        StudentDO studentDO = studentDOMapper.selectByPrimaryKey(form.getStudentId());
        UserDO teacherDO = userDOMapper.selectByPrimaryKey(form.getTeacherId());
        if(csId==null||studentDO==null||teacherDO==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"参数有误！");
        }
        CourseStudentInfoDO courseStudentInfoDO = new CourseStudentInfoDO();
        courseStudentInfoDO.setCsId(csId);
        courseStudentInfoDO.setClassNum(form.getClassNum());
        courseStudentInfoDO.setStatus(form.getStatus());
        courseStudentInfoDO.setMark(form.getMark());
        courseStudentInfoDO.setImgUrl(form.getImgUrl());
        courseStudentInfoDOMapper.insertSelective(courseStudentInfoDO);
        String status;
        if(form.getStatus()==1){
            status = "正常出勤";
        }else if(form.getStatus()==2){
            status = "迟到";
        }else if(form.getStatus()==3){
            status = "请假";
        }else {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"参数有误！");
        }
        templateMessageService.sendClassStatusMessage(studentDO.getName(),teacherDO.getName(), form.getCourseName(),DateStringUtil.getTotalTime(form.getStart(),form.getEnd()),form.getPlace(),status,studentDO.getOpenid(),courseStudentInfoDO.getId());
    }

    public List<CourseDateList> getAllCourse(Date start,Integer num) throws ParseException {
        List<CourseDateList> courseDateLists = new ArrayList<>(num);
        Date start1 = new Date(start.getTime());
        Date end1 = DateStringUtil.getTomorrowDate(start1);
        for (int i=0 ; i<num; i++){
            CourseDateList courseDateList = new CourseDateList();
            courseDateList.setIndex(i);
            List<CourseDateInfoDO> courseDateInfoDOS = courseDateInfoDOMapper.selectByDate(start1,end1);
            if(courseDateInfoDOS!=null&&courseDateInfoDOS.size()>0){
                List<CourseDateInfo> courseDateInfos = new ArrayList<>();
                courseDateInfoDOS.forEach(courseDateInfoDO -> {
                    CourseDateInfo courseDateInfo = new CourseDateInfo();
                    CourseInfoDO courseInfoDO = courseInfoDOMapper.selectByPrimaryKey(courseDateInfoDO.getCourseId());
                    UserDO userDO = userDOMapper.selectByPrimaryKey(courseDateInfoDO.getTeacherId());
                    List<StudentDO> studentDOList = studentDOMapper.selectByCourseId(courseDateInfoDO.getId());
                    courseDateInfo.setId(courseDateInfoDO.getId());
                    courseDateInfo.setPlace(courseDateInfoDO.getPlace());
                    courseDateInfo.setStart(courseDateInfoDO.getStartTime());
                    courseDateInfo.setEnd(courseDateInfoDO.getEndTime());
                    courseDateInfo.setCourse(courseInfoDO.getName());
                    courseDateInfo.setTeacher(userDO);
                    courseDateInfo.setStudents(studentDOList);
                    courseDateInfos.add(courseDateInfo);
                });
                courseDateList.setCourses(courseDateInfos);
            }
            courseDateLists.add(courseDateList);
            start1 = DateStringUtil.getTomorrowDate(end1);
            end1 = DateStringUtil.getTomorrowDate(start1);
        }
        return courseDateLists;
    }

    @Transactional
    public void addCourse(CourseForm form){
        if(courseInfoDOMapper.selectByName(form.getName())!=null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"课程名已存在！");
        }
        CourseInfoDO courseInfoDO = new CourseInfoDO();
        courseInfoDO.setName(form.getName());
        courseInfoDO.setTagId(form.getTagId().intValue());
        courseInfoDOMapper.insertSelective(courseInfoDO);
    }


    @Transactional
    public void addCourseDate(CourseDateForm form){
        CourseInfoDO courseInfoDO = courseInfoDOMapper.selectByPrimaryKey(form.getCourseId());
        if(courseInfoDO==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"课程不存在！");
        }
        UserDO userDO = userDOMapper.selectByPrimaryKey(form.getTeacherId());
        if(userDO==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"教师不存在！");
        }
        CourseDateInfoDO courseDateInfoDO = new CourseDateInfoDO();
        courseDateInfoDO.setCourseId(form.getCourseId());
        courseDateInfoDO.setTeacherId(form.getTeacherId());
        courseDateInfoDO.setPlace(form.getPlace());
        CourseStudentDO courseStudentDO = new CourseStudentDO();
        courseStudentDO.setStudentId(form.getStudentId());
        form.getDates().forEach(courseTime -> {
            courseDateInfoDO.setId(null);
            courseDateInfoDO.setStartTime(DateStringUtil.getDateFormString(courseTime.getStart(),"yyyy-MM-dd HH:mm"));
            courseDateInfoDO.setEndTime(DateStringUtil.getDateFormString(courseTime.getEnd(),"yyyy-MM-dd HH:mm"));
            courseDateInfoDOMapper.insertSelective(courseDateInfoDO);
            courseStudentDO.setCourseId(courseDateInfoDO.getId());
            courseStudentDOMapper.insertSelective(courseStudentDO);
        });
    }

    public List<CourseVO> getCourse(Date start, Date end, String openid){
        List<StudentCourseDateDO> studentCourseDateDOS = courseDateInfoDOMapper.selectByDateAndOpenid(start,end,openid);
        if(studentCourseDateDOS!=null&&studentCourseDateDOS.size()>0){
            List<CourseVO> courseVOList = new ArrayList<>();
            studentCourseDateDOS.forEach(studentCourseDateDO -> {
                CourseVO courseVO = new CourseVO(studentCourseDateDO,DateStringUtil.getYear(studentCourseDateDO.getStartTime()),DateStringUtil.getMonth(studentCourseDateDO.getStartTime()),DateStringUtil.getDay(studentCourseDateDO.getStartTime()),DateStringUtil.getDuration(studentCourseDateDO.getStartTime(),studentCourseDateDO.getEndTime()));
                courseVOList.add(courseVO);
            });
            return courseVOList;
        }
        return null;
    }

    public CourseStatus getStatus(CourseStatusForm form){
        CourseStudentInfoDO courseStudentInfoDO = courseStudentInfoDOMapper.selectByPrimaryKey(form.getId());
        if(courseStudentInfoDO==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"未找到课堂状态记录");
        }

        CourseStudentDO courseStudentDO = courseStudentDOMapper.selectByPrimaryKey(courseStudentInfoDO.getCsId());
        StudentDO studentDO = studentDOMapper.selectByPrimaryKey(courseStudentDO.getStudentId());
        if(studentDO==null||studentDO.getSub().intValue()!=1||!studentDO.getOpenid().equals(form.getOpenid())){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"openid有误！");
        }
        CourseDateInfoDO courseDateInfoDO = courseDateInfoDOMapper.selectByPrimaryKey(courseStudentDO.getCourseId());
        CourseInfoDO courseInfoDO = courseInfoDOMapper.selectByPrimaryKey(courseDateInfoDO.getCourseId());
        UserDO userDO = userDOMapper.selectByPrimaryKey(courseDateInfoDO.getTeacherId());
        CourseStatus courseStatus = new CourseStatus();
        courseStatus.setCourseDateId(courseDateInfoDO.getId());
        courseStatus.setStudent(studentDO.getName());
        courseStatus.setTeacher(userDO.getName());
        courseStatus.setTime(DateStringUtil.getTotalTime(courseDateInfoDO.getStartTime(),courseDateInfoDO.getEndTime()));
        courseStatus.setCourse(courseInfoDO.getName());
        courseStatus.setPlace(courseDateInfoDO.getPlace());
        courseStatus.setMark(courseStudentInfoDO.getMark());
        courseStatus.setStatus(courseStudentInfoDO.getStatus());
        courseStatus.setClassNum(courseStudentInfoDO.getClassNum());
        courseStatus.setImgUrl(courseStudentInfoDO.getImgUrl());
        return courseStatus;
    }

    /**
     * 检查给定的时间段之间的课程，有则发送课前提醒模板
     * @param dateStart 开始
     * @param dateEnd 结束
     */
    public void courseTips(Date dateStart, Date dateEnd,String templateId){
        log.info("查询在{}之间的课程信息",DateStringUtil.getTotalTime(dateStart,dateEnd));
        List<CourseDateInfoDO> courseDateInfoDOS = courseDateInfoDOMapper.selectByDate(dateStart,dateEnd);
        if(courseDateInfoDOS!=null&&courseDateInfoDOS.size()>0){
            log.info("以下课程即将开课：{}，正在推送课前提醒！",courseDateInfoDOS.toString());
            courseDateInfoDOS.forEach(courseDateInfoDO -> {
                List<StudentDO> studentDOList = studentDOMapper.selectByCourseId(courseDateInfoDO.getId());
                if(studentDOList!=null&&studentDOList.size()>0){
                    CourseInfoDO courseInfoDO = courseInfoDOMapper.selectByPrimaryKey(courseDateInfoDO.getCourseId());
                    templateMessageService.sendClassTipMessage(studentDOList,courseInfoDO.getName(), DateStringUtil.getTotalTime(courseDateInfoDO.getStartTime(),courseDateInfoDO.getEndTime()),courseDateInfoDO.getPlace(),templateId);
                }
            });
        }else {
            log.info("{}之间没有即将开课的课程信息",DateStringUtil.getTotalTime(dateStart,dateEnd));
        }
    }

}
