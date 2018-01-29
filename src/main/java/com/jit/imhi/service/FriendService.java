package com.jit.imhi.service;

import com.jit.imhi.mapper.FriendMapper;
import com.jit.imhi.model.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class FriendService {
    private FriendMapper friendMapper;

    @Autowired
    public FriendService(FriendMapper friendMapper) {
        this.friendMapper = friendMapper;
    }

    public List<Friend> selectByUserId(Integer userId) {
        List<Friend> list;
        System.out.println("测试是否使用了selectByUserId");
        list = friendMapper.selectByUserId(userId);
        return list;
    }

    public int delFriend(Integer userId, Integer friendId) {
        int num = friendMapper.deleteByPrimaryKey(userId, friendId);

        return num;
    }
}
