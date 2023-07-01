package com.example.shirodemo.dao;

import com.example.shirodemo.dataobject.UserDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test..user_info
     *
     * @mbg.generated Wed Jun 28 15:05:32 CST 2023
     */
    int deleteByPrimaryKey(Integer uid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test..user_info
     *
     * @mbg.generated Wed Jun 28 15:05:32 CST 2023
     */
    int insert(UserDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test..user_info
     *
     * @mbg.generated Wed Jun 28 15:05:32 CST 2023
     */
    int insertSelective(UserDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test..user_info
     *
     * @mbg.generated Wed Jun 28 15:05:32 CST 2023
     */
    UserDO selectByPrimaryKey(Integer uid);

    UserDO selectByAccount(String account);

    List<UserDO> selectALL();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test..user_info
     *
     * @mbg.generated Wed Jun 28 15:05:32 CST 2023
     */
    int updateByPrimaryKeySelective(UserDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test..user_info
     *
     * @mbg.generated Wed Jun 28 15:05:32 CST 2023
     */
    int updateByPrimaryKey(UserDO record);
}