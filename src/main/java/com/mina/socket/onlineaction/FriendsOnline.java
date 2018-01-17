package com.mina.socket.onlineaction;

import com.jit.imhi.mapper.FriendMapper;
import com.jit.imhi.mapper.LogininfoMapper;
import com.jit.imhi.model.Friend;
import com.mina.socket.Util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.mina.core.session.IoSession;

import java.util.List;

public class FriendsOnline {
    private IoSession ioSession;

    public FriendsOnline(IoSession ioSession) {
        this.ioSession = ioSession;
    }

    static SqlSessionFactory sqlSessionFactory = null;

    static {
        sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
    }

    public List<Friend> FriendIsOnline(int userId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        FriendMapper friendMapper = sqlSession.getMapper(FriendMapper.class);
        List<Friend> list = friendMapper.selectByUserId(userId);
        sqlSession.close();
        return list;

    }
}
