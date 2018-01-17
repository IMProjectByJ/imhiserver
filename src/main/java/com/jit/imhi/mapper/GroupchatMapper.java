package com.jit.imhi.mapper;

import com.jit.imhi.model.Groupchat;

import java.util.List;

public interface GroupchatMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GroupChat
     *
     * @mbggenerated Wed Jan 17 23:52:47 CST 2018
     */
    int deleteByPrimaryKey(Integer groupId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GroupChat
     *
     * @mbggenerated Wed Jan 17 23:52:47 CST 2018
     */
    int insert(Groupchat record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GroupChat
     *
     * @mbggenerated Wed Jan 17 23:52:47 CST 2018
     */
    Groupchat selectByPrimaryKey(Integer groupId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GroupChat
     *
     * @mbggenerated Wed Jan 17 23:52:47 CST 2018
     */
    List<Groupchat> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GroupChat
     *
     * @mbggenerated Wed Jan 17 23:52:47 CST 2018
     */
    int updateByPrimaryKey(Groupchat record);

    List<Groupchat> selectByGroupName(String groupName);
}