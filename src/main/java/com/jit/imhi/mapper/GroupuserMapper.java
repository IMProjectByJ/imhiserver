package com.jit.imhi.mapper;

import com.jit.imhi.model.Groupuser;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GroupuserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GroupUser
     *
     * @mbggenerated Tue Jan 16 19:16:40 CST 2018
     */
    int deleteByPrimaryKey(@Param("memberId") Integer memberId, @Param("groupId") Integer groupId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GroupUser
     *
     * @mbggenerated Tue Jan 16 19:16:40 CST 2018
     */
    int insert(Groupuser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GroupUser
     *
     * @mbggenerated Tue Jan 16 19:16:40 CST 2018
     */
    Groupuser selectByPrimaryKey(@Param("memberId") Integer memberId, @Param("groupId") Integer groupId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GroupUser
     *
     * @mbggenerated Tue Jan 16 19:16:40 CST 2018
     */
    List<Groupuser> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GroupUser
     *
     * @mbggenerated Tue Jan 16 19:16:40 CST 2018
     */
    int updateByPrimaryKey(Groupuser record);
}