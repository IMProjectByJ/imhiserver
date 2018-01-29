package com.jit.imhi.service;

import com.jit.imhi.mapper.UserMapper;
import com.jit.imhi.model.User;
import com.jit.imhi.utils.GetRandom;
import com.jit.imhi.utils.MD5Util;
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

    public User findFriendByUserID(Integer userId) {
        User user = new User();
        User user1 = userMapper.selectByPrimaryKey(userId);
        user.setNikname(user1.getNikname());
        user.setPhoneNum(user1.getPhoneNum());
        user.setUserId(user1.getUserId());
        return user;
    }

    public User insertUser(User user) {
        Integer id;
        if ((findUserByPhoneNum(user.getPhoneNum()))== null)
        {
            if (user.getNikname() == null)
            {
                user.setNikname(GetRandom.getRandomString(5));
            }
            if (user.getMotto()== null || user.getMotto().length() == 0)
            {
                user.setMotto("这个人很懒，什么都没有留下");
            }
            userMapper.insert(user); // insert
            return user;
        }
        return null;
    }

    public int updateInfoByUserId(User user) {
        return userMapper.updateByPrimaryKey(user);
    }

    public boolean comparePassword(String  userPssword, String dateBasePassword)
    {
        return dateBasePassword.equals(MD5Util.encrypt(userPssword));
    }
}
