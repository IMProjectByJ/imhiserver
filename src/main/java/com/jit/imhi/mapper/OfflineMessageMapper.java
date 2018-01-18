package com.jit.imhi.mapper;

import com.jit.imhi.model.OfflineMessage;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OfflineMessageMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table offline_message
     *
     * @mbggenerated Sun Jan 14 18:53:58 CST 2018
     */
    int deleteByPrimaryKey(@Param("messagId") Integer messagId, @Param("toId") Integer toId, @Param("fromUserId") Integer fromUserId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table offline_message
     *
     * @mbggenerated Sun Jan 14 18:53:58 CST 2018
     */
    int insert(OfflineMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table offline_message
     *
     * @mbggenerated Sun Jan 14 18:53:58 CST 2018
     */
    OfflineMessage selectByPrimaryKey(@Param("messagId") Integer messagId, @Param("toId") Integer toId, @Param("fromUserId") Integer fromUserId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table offline_message
     *
     * @mbggenerated Sun Jan 14 18:53:58 CST 2018
     */
    List<OfflineMessage> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table offline_message
     *
     * @mbggenerated Sun Jan 14 18:53:58 CST 2018
     */
    int updateByPrimaryKey(OfflineMessage record);

    //yuyisummer 1.14
    int selectById(@Param("toId") Integer toId, @Param("fromUserId") Integer fromUserId, @Param("messageType") String messageType);
}