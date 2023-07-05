package com.example.shirodemo.modules.sys.dataobject;

import java.util.Date;

public class CourseDateInfoDO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column test..course_date_info.id
     *
     * @mbg.generated Tue Jul 04 18:11:07 CST 2023
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column test..course_date_info.course_id
     *
     * @mbg.generated Tue Jul 04 18:11:07 CST 2023
     */
    private Integer courseId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column test..course_date_info.start_time
     *
     * @mbg.generated Tue Jul 04 18:11:07 CST 2023
     */
    private Date startTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column test..course_date_info.teacher_id
     *
     * @mbg.generated Tue Jul 04 18:11:07 CST 2023
     */
    private Integer teacherId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column test..course_date_info.place
     *
     * @mbg.generated Tue Jul 04 18:11:07 CST 2023
     */
    private String place;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column test..course_date_info.end_time
     *
     * @mbg.generated Tue Jul 04 18:11:07 CST 2023
     */
    private Date endTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column test..course_date_info.id
     *
     * @return the value of test..course_date_info.id
     *
     * @mbg.generated Tue Jul 04 18:11:07 CST 2023
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column test..course_date_info.id
     *
     * @param id the value for test..course_date_info.id
     *
     * @mbg.generated Tue Jul 04 18:11:07 CST 2023
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column test..course_date_info.course_id
     *
     * @return the value of test..course_date_info.course_id
     *
     * @mbg.generated Tue Jul 04 18:11:07 CST 2023
     */
    public Integer getCourseId() {
        return courseId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column test..course_date_info.course_id
     *
     * @param courseId the value for test..course_date_info.course_id
     *
     * @mbg.generated Tue Jul 04 18:11:07 CST 2023
     */
    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column test..course_date_info.start_time
     *
     * @return the value of test..course_date_info.start_time
     *
     * @mbg.generated Tue Jul 04 18:11:07 CST 2023
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column test..course_date_info.start_time
     *
     * @param startTime the value for test..course_date_info.start_time
     *
     * @mbg.generated Tue Jul 04 18:11:07 CST 2023
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column test..course_date_info.teacher_id
     *
     * @return the value of test..course_date_info.teacher_id
     *
     * @mbg.generated Tue Jul 04 18:11:07 CST 2023
     */
    public Integer getTeacherId() {
        return teacherId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column test..course_date_info.teacher_id
     *
     * @param teacherId the value for test..course_date_info.teacher_id
     *
     * @mbg.generated Tue Jul 04 18:11:07 CST 2023
     */
    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column test..course_date_info.place
     *
     * @return the value of test..course_date_info.place
     *
     * @mbg.generated Tue Jul 04 18:11:07 CST 2023
     */
    public String getPlace() {
        return place;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column test..course_date_info.place
     *
     * @param place the value for test..course_date_info.place
     *
     * @mbg.generated Tue Jul 04 18:11:07 CST 2023
     */
    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column test..course_date_info.end_time
     *
     * @return the value of test..course_date_info.end_time
     *
     * @mbg.generated Tue Jul 04 18:11:07 CST 2023
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column test..course_date_info.end_time
     *
     * @param endTime the value for test..course_date_info.end_time
     *
     * @mbg.generated Tue Jul 04 18:11:07 CST 2023
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "CourseDateInfoDO{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", startTime=" + startTime +
                ", teacherId=" + teacherId +
                ", place='" + place + '\'' +
                ", endTime=" + endTime +
                '}';
    }
}