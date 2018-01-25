package com.mina.socket.typeaction;

import com.jit.imhi.mapper.OfflineMessageMapper;
import com.jit.imhi.model.OfflineMessage;
import com.mina.socket.Util.MyBatisUtil;
import com.mina.socket.Util.ThisTime;
import net.sf.json.JSONObject;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.mina.core.session.IoSession;

import java.util.Date;
import java.util.List;

public class Type_Eight {
    private IoSession ioSession;

    public Type_Eight() {
    }

    public Type_Eight(IoSession ioSession) {
        this.ioSession = ioSession;
    }

    static SqlSessionFactory sqlSessionFactory = null;

    static {
        sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
    }

    public void InsertOfflineSmg(JSONObject json) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        OfflineMessageMapper offlineMessageMapper = sqlSession.getMapper(OfflineMessageMapper.class);
        OfflineMessage info = new OfflineMessage();
        info.setFromUserId(Integer.valueOf(json.getString("from")));
        info.setToId(Integer.valueOf(json.getString("to")));
        String message_type = json.getString("message_type");
        info.setMessageType(message_type);
        // info.setTextType(json.getString("texttype"));
        // info.setTextContent(json.getString("textcontent"));
        //
        if (message_type.equals("8"))
            info.setDate(new ThisTime().HaveThisTime());
        else
            info.setDate(new Date(json.getString("date")));
        System.out.println("检测" + info.getTextContent());
        //info.setMsgId(1);
        int i = offlineMessageMapper.insert(info);
        sqlSession.commit();
        sqlSession.close();
        /*
        List<OfflineMessage> list = offlineMessageMapper.selectAll();
        for(int j = 0;j<list.size();j++)
        {
            System.out.println("测试"+j);
            System.out.println(list.get(j).getFromUserId());
            System.out.println(list.get(j).getMsgId());


        }

        System.out.println("测试。。。。。。");
        */
    }
}
