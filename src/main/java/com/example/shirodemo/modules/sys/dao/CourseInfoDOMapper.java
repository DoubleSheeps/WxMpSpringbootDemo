package com.example.shirodemo.modules.sys.dao;

import com.example.shirodemo.modules.sys.dataobject.CourseInfoDO;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CourseInfoDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test..course_info
     *
     * @mbg.generated Wed Jul 05 18:25:47 CST 2023
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test..course_info
     *
     * @mbg.generated Wed Jul 05 18:25:47 CST 2023
     */
    int insert(CourseInfoDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test..course_info
     *
     * @mbg.generated Wed Jul 05 18:25:47 CST 2023
     */
    int insertSelective(CourseInfoDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test..course_info
     *
     * @mbg.generated Wed Jul 05 18:25:47 CST 2023
     */
    CourseInfoDO selectByPrimaryKey(Integer id);

    CourseInfoDO selectByName(String name);

    List<CourseInfoDO> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test..course_info
     *
     * @mbg.generated Wed Jul 05 18:25:47 CST 2023
     */
    int updateByPrimaryKeySelective(CourseInfoDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test..course_info
     *
     * @mbg.generated Wed Jul 05 18:25:47 CST 2023
     */
    int updateByPrimaryKey(CourseInfoDO record);
}