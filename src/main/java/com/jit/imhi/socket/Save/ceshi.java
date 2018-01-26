package com.jit.imhi.socket.Save;

import com.jit.imhi.mapper.UserMapper;
import com.jit.imhi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ceshi {
    UserMapper userMapper ;
    @Autowired
    public ceshi(UserMapper userMapper) {
        this.userMapper = userMapper;
        System.out.println("--- have in test");
    }

    public User test(){
        System.out.println("in test in test in test");
        return  userMapper.selectByPrimaryKey(10022 );
       // return "test111";
    }
}
