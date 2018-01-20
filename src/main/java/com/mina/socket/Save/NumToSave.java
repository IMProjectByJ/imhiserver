package com.mina.socket.Save;

import com.jit.imhi.mapper.HistoryMessageMapper;
import com.jit.imhi.mapper.NuminfoMapper;
import com.jit.imhi.model.Numinfo;
import com.mina.socket.Util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.mina.core.session.IoSession;

import java.awt.*;

public class NumToSave {
    private IoSession ioSession;

    public NumToSave() {
    }

    static SqlSessionFactory sqlSessionFactory = null;

    static {
        sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
    }

    public static void offToSave(Numinfo numinfo) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        NuminfoMapper numinfoMapper = sqlSession.getMapper(NuminfoMapper.class);
        Numinfo numinfo1 = numinfoMapper.selectByPrimaryKey(numinfo.getUserId(), numinfo.getFriendId()
                , numinfo.getFriendType());
        int i = 0;
        if (numinfo1 != null) {
           numinfoMapper.updateNoneOld(numinfo);
            System.out.println("替换离线信息数量");
        }else {
            i = numinfoMapper.insert(numinfo);
            System.out.println("输入离线信息数量+" + i);
        }


        sqlSession.commit();
        sqlSession.close();
    }

}
