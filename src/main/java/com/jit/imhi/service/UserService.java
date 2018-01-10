package com.jit.imhi.service;

import com.jit.imhi.mapper.UserMapper;
import com.jit.imhi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper)
    {
        this.userMapper = userMapper;
    }

    public User findUserByPhoneNum(String phoneNum)
    {
        return  userMapper.selectByPhoneNum(phoneNum);
    }
    public User findUserByUserId(Integer userId)
    {
       // System.out.println("in UserSService " + userId);
        return  userMapper.selectByPrimaryKey(userId);
    }
    public User insertUser(User user)
    {
        Integer id;
        if ((id = userMapper.insert(user)) != null)
        {
            return findUserByPhoneNum(user.getPhoneNum());
        }
        return null;
    }

    public int updateInfoByUserId(User user)
    {
        return userMapper.updateByPrimaryKey(user) ;
    }
}
