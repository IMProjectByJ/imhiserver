package com.jit.imhi.service;


/*

   create by liuynuxing
 */

import com.jit.imhi.mapper.GroupuserMapper;
import com.jit.imhi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GroupUserService {


    @Autowired
    GroupuserMapper groupuserMapper;


    public List<User> getUsersInfo(Integer groupId) {

       return groupuserMapper.selectUsers(groupId);
    }
}
