package com.jit.imhi.model;

import java.util.Date;

public class User {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.user_id
     *
     * @mbggenerated Wed Jan 17 22:49:33 CST 2018
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.phone_num
     *
     * @mbggenerated Wed Jan 17 22:49:33 CST 2018
     */
    private String phoneNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.nikname
     *
     * @mbggenerated Wed Jan 17 22:49:33 CST 2018
     */
    private String nikname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.head_url
     *
     * @mbggenerated Wed Jan 17 22:49:33 CST 2018
     */
    private String headUrl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.age
     *
     * @mbggenerated Wed Jan 17 22:49:33 CST 2018
     */
    private Integer age;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.gender
     *
     * @mbggenerated Wed Jan 17 22:49:33 CST 2018
     */
    private String gender;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.birth
     *
     * @mbggenerated Wed Jan 17 22:49:33 CST 2018
     */
    private Date birth;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.user_password
     *
     * @mbggenerated Wed Jan 17 22:49:33 CST 2018
     */
    private String userPassword;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.motto
     *
     * @mbggenerated Wed Jan 17 22:49:33 CST 2018
     */
    private String motto;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.user_id
     *
     * @return the value of user.user_id
     *
     * @mbggenerated Wed Jan 17 22:49:33 CST 2018
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.user_id
     *
     * @param userId the value for user.user_id
     *
     * @mbggenerated Wed Jan 17 22:49:33 CST 2018
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.phone_num
     *
     * @return the value of user.phone_num
     *
     * @mbggenerated Wed Jan 17 22:49:33 CST 2018
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.phone_num
     *
     * @param phoneNum the value for user.phone_num
     *
     * @mbggenerated Wed Jan 17 22:49:33 CST 2018
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum == null ? null : phoneNum.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.nikname
     *
     * @return the value of user.nikname
     *
     * @mbggenerated Wed Jan 17 22:49:33 CST 2018
     */
    public String getNikname() {
        return nikname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.nikname
     *
     * @param nikname the value for user.nikname
     *
     * @mbggenerated Wed Jan 17 22:49:33 CST 2018
     */
    public void setNikname(String nikname) {
        this.nikname = nikname == null ? null : nikname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.head_url
     *
     * @return the value of user.head_url
     *
     * @mbggenerated Wed Jan 17 22:49:33 CST 2018
     */
    public String getHeadUrl() {
        return headUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.head_url
     *
     * @param headUrl the value for user.head_url
     *
     * @mbggenerated Wed Jan 17 22:49:33 CST 2018
     */
    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl == null ? null : headUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.age
     *
     * @return the value of user.age
     *
     * @mbggenerated Wed Jan 17 22:49:33 CST 2018
     */
    public Integer getAge() {
        return age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.age
     *
     * @param age the value for user.age
     *
     * @mbggenerated Wed Jan 17 22:49:33 CST 2018
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.gender
     *
     * @return the value of user.gender
     *
     * @mbggenerated Wed Jan 17 22:49:33 CST 2018
     */
    public String getGender() {
        return gender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.gender
     *
     * @param gender the value for user.gender
     *
     * @mbggenerated Wed Jan 17 22:49:33 CST 2018
     */
    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.birth
     *
     * @return the value of user.birth
     *
     * @mbggenerated Wed Jan 17 22:49:33 CST 2018
     */
    public Date getBirth() {
        return birth;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.birth
     *
     * @param birth the value for user.birth
     *
     * @mbggenerated Wed Jan 17 22:49:33 CST 2018
     */
    public void setBirth(Date birth) {
        this.birth = birth;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.user_password
     *
     * @return the value of user.user_password
     *
     * @mbggenerated Wed Jan 17 22:49:33 CST 2018
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.user_password
     *
     * @param userPassword the value for user.user_password
     *
     * @mbggenerated Wed Jan 17 22:49:33 CST 2018
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.motto
     *
     * @return the value of user.motto
     *
     * @mbggenerated Wed Jan 17 22:49:33 CST 2018
     */
    public String getMotto() {
        return motto;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.motto
     *
     * @param motto the value for user.motto
     *
     * @mbggenerated Wed Jan 17 22:49:33 CST 2018
     */
    public void setMotto(String motto) {
        this.motto = motto == null ? null : motto.trim();
    }


    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", phoneNum='" + phoneNum + '\'' +
                ", nikname='" + nikname + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", birth=" + birth +
                ", userPassword='" + userPassword + '\'' +
                ", motto='" + motto + '\'' +
                '}';
    }

}