package com.mina.socket.typeaction;

import com.jit.imhi.mapper.GroupchatMapper;
import com.mina.socket.Util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.mina.core.session.IoSession;

public class Type_Eleven {
    private IoSession ioSession;
    public Type_Eleven() {
    }
    static SqlSessionFactory sqlSessionFactory = null;
    static {
        sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
    }
    public String FindGroupOwner(int group_id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        GroupchatMapper groupchatMapper = sqlSession.getMapper(GroupchatMapper.class);
        int user_id = groupchatMapper.selectOwner(group_id);
        return String.valueOf(user_id);
    }
}
