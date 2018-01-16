package com.jit.imhi.mapper;

import com.jit.imhi.model.Friend;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FriendMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Friend
     *
     * @mbggenerated Sat Jan 13 10:14:02 CST 2018
     */
    int deleteByPrimaryKey(@Param("userId") Integer userId, @Param("friendId") Integer friendId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Friend
     *
     * @mbggenerated Sat Jan 13 10:14:02 CST 2018
     */
    int insert(Friend record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Friend
     *
     * @mbggenerated Sat Jan 13 10:14:02 CST 2018
     */

    //yuyisummer
    List<Friend> selectByUserId(@Param("userId") Integer userId);

    Friend selectByPrimaryKey(@Param("userId") Integer userId, @Param("friendId") Integer friendId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Friend
     *
     * @mbggenerated Sat Jan 13 10:14:02 CST 2018
     */
    List<Friend> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Friend
     *
     * @mbggenerated Sat Jan 13 10:14:02 CST 2018
     */
    int updateByPrimaryKey(Friend record);
}