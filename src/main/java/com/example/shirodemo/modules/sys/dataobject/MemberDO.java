package com.example.shirodemo.modules.sys.dataobject;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.shirodemo.Utils.Json;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.util.StringUtils;

import java.util.Date;

public class MemberDO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column test..member_info.openid
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    private String openid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column test..member_info.phone
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    private String phone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column test..member_info.nickname
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    private String nickname;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column test..member_info.sex
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    private Byte sex;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column test..member_info.city
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    private String city;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column test..member_info.province
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    private String province;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column test..member_info.headimgurl
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    private String headimgurl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column test..member_info.subscribe_time
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    private Date subscribeTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column test..member_info.subscribe
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    private Boolean subscribe;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column test..member_info.unionid
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    private String unionid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column test..member_info.remark
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    private String remark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column test..member_info.subscribe_scene
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    private String subscribeScene;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column test..member_info.qr_scene_str
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    private String qrSceneStr;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column test..member_info.tagid_list
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    private String tagidList;




    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column test..member_info.openid
     *
     * @return the value of test..member_info.openid
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    public String getOpenid() {
        return openid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column test..member_info.openid
     *
     * @param openid the value for test..member_info.openid
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column test..member_info.phone
     *
     * @return the value of test..member_info.phone
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column test..member_info.phone
     *
     * @param phone the value for test..member_info.phone
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column test..member_info.nickname
     *
     * @return the value of test..member_info.nickname
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column test..member_info.nickname
     *
     * @param nickname the value for test..member_info.nickname
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column test..member_info.sex
     *
     * @return the value of test..member_info.sex
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    public Byte getSex() {
        return sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column test..member_info.sex
     *
     * @param sex the value for test..member_info.sex
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    public void setSex(Byte sex) {
        this.sex = sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column test..member_info.city
     *
     * @return the value of test..member_info.city
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    public String getCity() {
        return city;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column test..member_info.city
     *
     * @param city the value for test..member_info.city
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column test..member_info.province
     *
     * @return the value of test..member_info.province
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    public String getProvince() {
        return province;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column test..member_info.province
     *
     * @param province the value for test..member_info.province
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column test..member_info.headimgurl
     *
     * @return the value of test..member_info.headimgurl
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    public String getHeadimgurl() {
        return headimgurl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column test..member_info.headimgurl
     *
     * @param headimgurl the value for test..member_info.headimgurl
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl == null ? null : headimgurl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column test..member_info.subscribe_time
     *
     * @return the value of test..member_info.subscribe_time
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    public Date getSubscribeTime() {
        return subscribeTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column test..member_info.subscribe_time
     *
     * @param subscribeTime the value for test..member_info.subscribe_time
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    public void setSubscribeTime(Date subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column test..member_info.subscribe
     *
     * @return the value of test..member_info.subscribe
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    public Boolean getSubscribe() {
        return subscribe;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column test..member_info.subscribe
     *
     * @param subscribe the value for test..member_info.subscribe
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    public void setSubscribe(Boolean subscribe) {
        this.subscribe = subscribe;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column test..member_info.unionid
     *
     * @return the value of test..member_info.unionid
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    public String getUnionid() {
        return unionid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column test..member_info.unionid
     *
     * @param unionid the value for test..member_info.unionid
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    public void setUnionid(String unionid) {
        this.unionid = unionid == null ? null : unionid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column test..member_info.remark
     *
     * @return the value of test..member_info.remark
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column test..member_info.remark
     *
     * @param remark the value for test..member_info.remark
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column test..member_info.subscribe_scene
     *
     * @return the value of test..member_info.subscribe_scene
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    public String getSubscribeScene() {
        return subscribeScene;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column test..member_info.subscribe_scene
     *
     * @param subscribeScene the value for test..member_info.subscribe_scene
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    public void setSubscribeScene(String subscribeScene) {
        this.subscribeScene = subscribeScene == null ? null : subscribeScene.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column test..member_info.qr_scene_str
     *
     * @return the value of test..member_info.qr_scene_str
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    public String getQrSceneStr() {
        return qrSceneStr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column test..member_info.qr_scene_str
     *
     * @param qrSceneStr the value for test..member_info.qr_scene_str
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    public void setQrSceneStr(String qrSceneStr) {
        this.qrSceneStr = qrSceneStr == null ? null : qrSceneStr.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column test..member_info.tagid_list
     *
     * @return the value of test..member_info.tagid_list
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    public String getTagidList() {
        return tagidList;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column test..member_info.tagid_list
     *
     * @param tagidList the value for test..member_info.tagid_list
     *
     * @mbg.generated Sun Jul 02 14:31:53 CST 2023
     */
    public void setTagidList(String tagidList) {
        this.tagidList = tagidList == null ? null : tagidList.trim();
    }
}