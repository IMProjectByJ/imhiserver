package com.mina.socket.Save;

import com.jit.imhi.mapper.HistoryMessageMapper;
import com.jit.imhi.mapper.OfflineMessageMapper;
import com.jit.imhi.model.HistoryMessage;
import com.jit.imhi.model.OfflineMessage;
import com.mina.socket.Util.MyBatisUtil;
import com.mina.socket.Util.ThisTime;
import net.sf.json.JSONObject;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.mina.core.session.IoSession;

public class SaveMessage {
    private IoSession ioSession;
    public SaveMessage(){}
    static SqlSessionFactory sqlSessionFactory = null;
    static {
        sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
    }
    public HistoryMessage InsertMessage(JSONObject json){
        SqlSession sqlSession = sqlSessionFactory.openSession();

        HistoryMessageMapper historyMessageMapper = sqlSession.getMapper(HistoryMessageMapper.class);
        HistoryMessage info = new HistoryMessage();
        info.setUserFromId(Integer.valueOf(json.optString("from")));
        info.setMessageType(Integer.valueOf(json.optString("message_type")));
        info.setDate(new ThisTime().HaveThisTime());

        Integer to;
        if (!json.has("to")){
            // json.put("to", 1);
            to = 1;
        } else{
            to = Integer.valueOf(json.optString("to"));
        }
        info.setToId(to);
        if (json.has("texttype")){
            // json.put("to", 1);
            info.setTextType(Integer.valueOf(json.optString("texttype")));

        }
        if (json.has("textcontent")){
            // json.put("to", 1);
            info.setTextContent(json.optString("textcontent"));
        }

        System.out.println("检测"+info.getTextContent());
        //info.setMsgId(1);
        int i = historyMessageMapper.insert(info);
        sqlSession.commit();
        System.out.println("savemessage.java "+i);
        HistoryMessage hs = historyMessageMapper.selectByPrimaryKey(info.getMessageId());
        sqlSession.close();
        return hs;
    }
}
