package com.example.shirodemo.modules.sys.service;

import com.example.shirodemo.Utils.DateStringUtil;
import com.example.shirodemo.config.TaskExcutor;
import com.example.shirodemo.error.BusinessException;
import com.example.shirodemo.error.EmBusinessError;
import com.example.shirodemo.model.WxUser;
import com.example.shirodemo.modules.sys.controller.VO.CourseDateForm;
import com.example.shirodemo.modules.sys.controller.VO.CourseVO;
import com.example.shirodemo.modules.sys.dao.*;
import com.example.shirodemo.modules.sys.dataobject.*;
import com.example.shirodemo.modules.wx.config.WxMpProperties;
import com.example.shirodemo.modules.wx.service.TemplateMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CourseService {
    Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CourseInfoDOMapper courseInfoDOMapper;
    @Autowired
    private CourseStudentDOMapper courseStudentDOMapper;
    @Autowired
    private CourseDateInfoDOMapper courseDateInfoDOMapper;
    @Autowired
    private StudentDOMapper studentDOMapper;
    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private TemplateMessageService templateMessageService;

    public List<CourseInfoDO> listCourse(){
        return courseInfoDOMapper.getAll();
    }

    @Transactional
    public void addCourse(String name){
        if(courseInfoDOMapper.selectByName(name)!=null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"课程名已存在！");
        }
        CourseInfoDO courseInfoDO = new CourseInfoDO();
        courseInfoDO.setName(name);
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
            courseDateInfoDO.setStartTime(courseTime.getStart());
            courseDateInfoDO.setEndTime(courseTime.getEnd());
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

    /**
     * 检查给定的时间往后1小时45分到2小时15分之间的课程，有则发送课前提醒模板
     * @param date 给定的时间
     */
    public void beforeCourse(Date date){
        //查看下一个1小时45分钟-2小时15分钟的会上课的课程
        Date dateStart = new Date(date.getTime()+105*60*1000);
        Date dateEnd = new Date(date.getTime() + 135*60*1000);
        log.info("查询在{}之间的课程信息",DateStringUtil.getTotalTime(dateStart,dateEnd));
        List<CourseDateInfoDO> courseDateInfoDOS = courseDateInfoDOMapper.selectByDate(dateStart,dateEnd);

        if(courseDateInfoDOS!=null&&courseDateInfoDOS.size()>0){
            log.info("以下课程即将开课：{}，正在推送课前提醒！",courseDateInfoDOS.toString());
            courseDateInfoDOS.forEach(courseDateInfoDO -> {
                List<StudentDO> studentDOList = studentDOMapper.selectByCourseId(courseDateInfoDO.getCourseId());
                if(studentDOList!=null&&studentDOList.size()>0){
                    CourseInfoDO courseInfoDO = courseInfoDOMapper.selectByPrimaryKey(courseDateInfoDO.getCourseId());
                    log.info("课程{}的学生列表：{}",courseInfoDO.getName(),studentDOList.toString());
                    studentDOList.forEach(studentDO -> {
                        templateMessageService.sendClassTipMessage(studentDO.getOpenid(),studentDO.getName(),courseInfoDO.getName(), DateStringUtil.getTotalTime(courseDateInfoDO.getStartTime(),courseDateInfoDO.getEndTime()),courseDateInfoDO.getPlace());
                    });
                }
            });
        }
    }



}
