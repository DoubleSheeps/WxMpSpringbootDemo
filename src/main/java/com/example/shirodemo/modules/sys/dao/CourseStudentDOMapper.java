package com.example.shirodemo.modules.sys.dao;

import com.example.shirodemo.modules.sys.dataobject.CourseStudentDO;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseStudentDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test..course_student
     *
     * @mbg.generated Tue Jul 04 17:41:28 CST 2023
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test..course_student
     *
     * @mbg.generated Tue Jul 04 17:41:28 CST 2023
     */
    int insert(CourseStudentDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test..course_student
     *
     * @mbg.generated Tue Jul 04 17:41:28 CST 2023
     */
    int insertSelective(CourseStudentDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test..course_student
     *
     * @mbg.generated Tue Jul 04 17:41:28 CST 2023
     */
    CourseStudentDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test..course_student
     *
     * @mbg.generated Tue Jul 04 17:41:28 CST 2023
     */
    int updateByPrimaryKeySelective(CourseStudentDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test..course_student
     *
     * @mbg.generated Tue Jul 04 17:41:28 CST 2023
     */
    int updateByPrimaryKey(CourseStudentDO record);
}