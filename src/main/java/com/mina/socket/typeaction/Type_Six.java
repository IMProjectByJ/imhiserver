package com.mina.socket.typeaction;
import com.jit.imhi.api.LogininfoApi;
import com.jit.imhi.mapper.GroupchatMapper;
import com.jit.imhi.mapper.LogininfoMapper;
import com.jit.imhi.mapper.OfflineMessageMapper;
import com.jit.imhi.model.Logininfo;
import com.jit.imhi.service.LogininfoService;
import com.mina.socket.Util.MyBatisUtil;
import org.apache.ibatis.session.*;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.util.Map;


public class Type_Six {
    private  IoSession ioSession;
    public Type_Six(){};
    public Type_Six(IoSession ioSession){
        this.ioSession = ioSession;
    }

    static   SqlSessionFactory sqlSessionFactory = null;
   static {
       sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
   }


    public void inorup(Logininfo logininfo) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        LogininfoMapper logininfoMapper = sqlSession.getMapper(LogininfoMapper.class);
       try {
           if(logininfoMapper.selectByPrimaryKey(logininfo.getUserId())!= null ) {
                System.out.println("搜索不为空");
               logininfoMapper.updateByPrimaryKey(logininfo);
           }
               else {
               System.out.println("搜索为空");
               logininfoMapper.insert(logininfo);
           }
           sqlSession.commit();
       } catch (Exception e){
           e.printStackTrace();
       }
        sqlSession.close();

    }
        public int SelectOffline(int fromid,int toid){
            int num = 0;
            SqlSession sqlSession = sqlSessionFactory.openSession();
            OfflineMessageMapper offlineMessageMapper = sqlSession.getMapper(OfflineMessageMapper.class);
            num = offlineMessageMapper.selectById(fromid,toid,"2");
            System.out.println("num = "+num+"fromid = "+fromid +"toid = "+toid);
            sqlSession.close();
            return num;
        }
        public int SelectOffadd(int fromid,int toid) {
            int num = 0;
            SqlSession sqlSession = sqlSessionFactory.openSession();
            OfflineMessageMapper offlineMessageMapper = sqlSession.getMapper(OfflineMessageMapper.class);
            GroupchatMapper groupchatMapper = sqlSession.getMapper(GroupchatMapper.class);


            //这里还没做完，是做群消息的，暂时看起来没用
            num += offlineMessageMapper.selectById(fromid,toid,"2");
            System.out.println("num = "+num+"fromid = "+fromid +"toid = "+toid);
            sqlSession.close();
            return num;
        }
        public int searchUserId(String  ip_addr,String ip_port){
            Integer user_id ;
            SqlSession sqlSession = sqlSessionFactory.openSession();
            System.out.println(ip_addr+"      "+ip_port);
           LogininfoMapper logininfoMapper = sqlSession.getMapper(LogininfoMapper.class);
            System.out.println("测试2");
           user_id = logininfoMapper.searchUserId(ip_addr,ip_port);
           if(user_id == null){
               user_id = 0;
           }
            System.out.println("测试3");
            System.out.println("num = "+user_id+"fromid = "+ip_addr +"toid = "+ip_port);
            sqlSession.close();
            return user_id;
        }
}
