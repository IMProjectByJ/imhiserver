package com.mina.socket.typeaction;
import com.jit.imhi.api.LogininfoApi;
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
}
