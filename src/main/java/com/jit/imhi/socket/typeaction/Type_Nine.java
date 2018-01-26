package com.jit.imhi.socket.typeaction;

import com.jit.imhi.mapper.FriendMapper;
import com.jit.imhi.mapper.HistoryMessageMapper;
import com.jit.imhi.model.Friend;
import com.jit.imhi.model.HistoryMessage;
import com.jit.imhi.socket.Util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.mina.core.session.IoSession;

public class Type_Nine {
    private IoSession ioSession;
    Friend friend;
    Friend friend1;

    public Type_Nine() {
    }

    static SqlSessionFactory sqlSessionFactory = null;

    static {
        sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
    }

    public  void InsertFriend(Friend friend, Friend friend1) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        FriendMapper friendMapper = sqlSession.getMapper(FriendMapper.class);
        int i = friendMapper.insert(friend);
        if(!friend.getFriendId().equals(friend.getUserId()))
        i += friendMapper.insert(friend1);
        sqlSession.commit();
        sqlSession.close();
        System.out.println("添加好友，条数：" + i);
    }
    public void ChangeTheType(HistoryMessage historyMessage){
        int old_message_type = 8;
        SqlSession sqlSession = sqlSessionFactory.openSession();
        HistoryMessageMapper historyMessageMapper = sqlSession.getMapper(HistoryMessageMapper.class);
        historyMessageMapper.updateType(historyMessage);
        sqlSession.commit();
        sqlSession.close();
    }
}
