package com.jit.imhi.mapper;

import com.jit.imhi.model.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface UserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Tue Jan 09 14:13:09 CST 2018
     */
    @Delete({
            "delete from user",
            "where user_id = #{userId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Tue Jan 09 14:13:09 CST 2018
     */
    @Insert({
            "insert into user (user_id, phone_num, ",
            "nikname, head_url, ",
            "age, gender, birth, ",
            "user_password, motto)",
            "values (#{userId,jdbcType=INTEGER}, #{phoneNum,jdbcType=CHAR}, ",
            "#{nikname,jdbcType=VARCHAR}, #{headUrl,jdbcType=VARCHAR}, ",
            "#{age,jdbcType=INTEGER}, #{gender,jdbcType=VARCHAR}, #{birth,jdbcType=DATE}, ",
            "#{userPassword,jdbcType=VARCHAR}, #{motto,jdbcType=VARCHAR})"
    })
    Integer insert(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Tue Jan 09 14:13:09 CST 2018
     */
    @Select({
            "select",
            "user_id, phone_num, nikname, head_url, age, gender, birth, user_password, motto",
            "from user",
            "where user_id = #{userId,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="phone_num", property="phoneNum", jdbcType=JdbcType.CHAR),
            @Result(column="nikname", property="nikname", jdbcType=JdbcType.VARCHAR),
            @Result(column="head_url", property="headUrl", jdbcType=JdbcType.VARCHAR),
            @Result(column="age", property="age", jdbcType=JdbcType.INTEGER),
            @Result(column="gender", property="gender", jdbcType=JdbcType.VARCHAR),
            @Result(column="birth", property="birth", jdbcType=JdbcType.DATE),
            @Result(column="user_password", property="userPassword", jdbcType=JdbcType.VARCHAR),
            @Result(column="motto", property="motto", jdbcType=JdbcType.VARCHAR)
    })

    User selectByPrimaryKey(Integer userId);
    @Select({
            "select",
            "user_id, phone_num, nikname, head_url, age, gender, birth, user_password, motto",
            "from user",
            "where phone_num = #{phoneNum,jdbcType=CHAR}"
    })
    @Results({
            @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="phone_num", property="phoneNum", jdbcType=JdbcType.CHAR),
            @Result(column="nikname", property="nikname", jdbcType=JdbcType.VARCHAR),
            @Result(column="head_url", property="headUrl", jdbcType=JdbcType.VARCHAR),
            @Result(column="age", property="age", jdbcType=JdbcType.INTEGER),
            @Result(column="gender", property="gender", jdbcType=JdbcType.VARCHAR),
            @Result(column="birth", property="birth", jdbcType=JdbcType.DATE),
            @Result(column="user_password", property="userPassword", jdbcType=JdbcType.VARCHAR),
            @Result(column="motto", property="motto", jdbcType=JdbcType.VARCHAR)
    })

    User selectByPhoneNum(String phoneNum);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Tue Jan 09 14:13:09 CST 2018
     */
    @Select({
            "select",
            "user_id, phone_num, nikname, head_url, age, gender, birth, user_password, motto",
            "from user"
    })
    @Results({
            @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="phone_num", property="phoneNum", jdbcType=JdbcType.CHAR),
            @Result(column="nikname", property="nikname", jdbcType=JdbcType.VARCHAR),
            @Result(column="head_url", property="headUrl", jdbcType=JdbcType.VARCHAR),
            @Result(column="age", property="age", jdbcType=JdbcType.INTEGER),
            @Result(column="gender", property="gender", jdbcType=JdbcType.VARCHAR),
            @Result(column="birth", property="birth", jdbcType=JdbcType.DATE),
            @Result(column="user_password", property="userPassword", jdbcType=JdbcType.VARCHAR),
            @Result(column="motto", property="motto", jdbcType=JdbcType.VARCHAR)
    })
    List<User> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Tue Jan 09 14:13:09 CST 2018
     */
    @Update({
            "update user",
            "set phone_num = #{phoneNum,jdbcType=CHAR},",
            "nikname = #{nikname,jdbcType=VARCHAR},",
            "head_url = #{headUrl,jdbcType=VARCHAR},",
            "age = #{age,jdbcType=INTEGER},",
            "gender = #{gender,jdbcType=VARCHAR},",
            "birth = #{birth,jdbcType=DATE},",
            "user_password = #{userPassword,jdbcType=VARCHAR},",
            "motto = #{motto,jdbcType=VARCHAR}",
            "where user_id = #{userId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(User record);

    //yusyiummer 1.19 select for map
    List<String> selectForMap();

}