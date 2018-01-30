package com.jit.imhi.socket.Save;

import com.jit.imhi.mapper.FriendMapper;
import com.jit.imhi.mapper.GroupchatMapper;
import com.jit.imhi.mapper.GroupuserMapper;
import com.jit.imhi.mapper.NuminfoMapper;
import com.jit.imhi.model.Friend;
import com.jit.imhi.model.Numinfo;
import com.jit.imhi.socket.Util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.mina.core.session.IoSession;

import java.util.ArrayList;
import java.util.List;

public class NumToHave {
    private IoSession ioSession;
    public NumToHave(){}
    static SqlSessionFactory sqlSessionFactory = null;
    static {
        sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
    }
    public static  List<Numinfo> HaveOffNum(String user_id){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        FriendMapper friendMapper = sqlSession.getMapper(FriendMapper.class);
        GroupuserMapper groupuserMapper = sqlSession.getMapper(GroupuserMapper.class);
        NuminfoMapper numinfoMapper = sqlSession.getMapper(NuminfoMapper.class);
        GroupchatMapper groupchatMapper = sqlSession.getMapper(GroupchatMapper.class);
        List<Numinfo> list = new ArrayList<>() ;
        List<Friend> friendlist = new ArrayList<>();
        List<String> grouplist = new ArrayList<>();
        List<String> groupowner = new ArrayList<>();
        Integer userid = Integer.valueOf(user_id);
        friendlist = friendMapper.selectByUserId(userid);
       // grouplist = groupuserMapper.selectByUserID(userid);
        grouplist = groupuserMapper.selectByUserIdNum(userid);
        groupowner = groupchatMapper.selectAllOwn(userid);
        Numinfo numinfo = null;

        for(int i = 0;i<friendlist.size();i++){
            numinfo = numinfoMapper.selectAllBy(userid,friendlist.get(i).getFriendId(),"1");
            if(numinfo!= null)
            list.add(numinfo);

        }
        for(int i = 0;i<grouplist.size();i++){
            //numinfo = numinfoMapper.selectGroupInfo(Integer.valueOf(grouplist.get(i)),"2");
//          System.out.println(  numinfoMapper.selectGroupInfo(Integer.valueOf(grouplist.get(i)),"2").getUserId());
//            System.out.println(  numinfoMapper.selectGroupInfo(Integer.valueOf(grouplist.get(i)),"2").getNewId());
//            System.out.println(  numinfoMapper.selectGroupInfo(Integer.valueOf(grouplist.get(i)),"2").getFriendType());
           Numinfo numinfo1 = numinfoMapper.selectAllBy(userid,Integer.valueOf(grouplist.get(i)),"2");

            System.out.println("grouplist.size:"+i);
            if(numinfo1 != null)
            list.add(numinfo1);
            else
                System.out.println("grouplist numinfo1 is null");
        }
        /*
        for(int i = 0;i<groupowner.size();i++){
            numinfo = numinfoMapper.selectAllBy(userid,Integer.valueOf(grouplist.get(i)),"3");
            if(numinfo != null)
            list.add(numinfo);
        }
        */
        numinfo = numinfoMapper.selectAllBy(userid,9999,"3");
        if(numinfo != null)
            list.add(numinfo);
        sqlSession.close();
        //测试有没有成功

        for(int i = 0;i<list.size();i++){
            System.out.println("numtohave"+list.get(i).getFriendId());
        }
        return  list;
    }

    public static List<String> SelectForMap(){
        List<String> list = new ArrayList<>();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        NuminfoMapper numinfoMapper = sqlSession.getMapper(NuminfoMapper.class);
        list = numinfoMapper.selectForMap();
        return list;

    }
}
