package com.example.shirodemo.modules.sys.dao;

import com.example.shirodemo.modules.sys.dataobject.CourseDateInfoDO;
import com.example.shirodemo.modules.sys.dataobject.StudentCourseDateDO;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CourseDateInfoDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test..course_date_info
     *
     * @mbg.generated Tue Jul 04 18:11:07 CST 2023
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test..course_date_info
     *
     * @mbg.generated Tue Jul 04 18:11:07 CST 2023
     */
    int insert(CourseDateInfoDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test..course_date_info
     *
     * @mbg.generated Tue Jul 04 18:11:07 CST 2023
     */
    int insertSelective(CourseDateInfoDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test..course_date_info
     *
     * @mbg.generated Tue Jul 04 18:11:07 CST 2023
     */
    CourseDateInfoDO selectByPrimaryKey(Integer id);

    List<CourseDateInfoDO> selectByDate(Date date1,Date date2);

    List<StudentCourseDateDO> selectByDateAndOpenid(Date date1, Date date2, String openid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test..course_date_info
     *
     * @mbg.generated Tue Jul 04 18:11:07 CST 2023
     */
    int updateByPrimaryKeySelective(CourseDateInfoDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test..course_date_info
     *
     * @mbg.generated Tue Jul 04 18:11:07 CST 2023
     */
    int updateByPrimaryKey(CourseDateInfoDO record);
}