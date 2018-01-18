package com.mina.socket;


import com.jit.imhi.api.LogininfoApi;
import com.jit.imhi.model.Friend;
import com.jit.imhi.model.Logininfo;
import com.jit.imhi.service.LogininfoService;
import com.mina.socket.Save.SaveMessage;
import com.mina.socket.Util.ThisTime;
import com.mina.socket.onlineaction.FriendsOnline;
import com.mina.socket.typeaction.Type_Fives;
import com.mina.socket.typeaction.Type_Six;
import com.sun.javafx.collections.MappingChange;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.net.InetSocketAddress;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mina.socket.typeaction.*;

public class MyIoHandler extends IoHandlerAdapter {
    Map<String, IoSession> map = new HashMap<String, IoSession>();
    Map<String, IoSession> friend = new HashMap<>();
    Map<String, Map<String, IoSession>> mapfriend = new HashMap<>();
    private int count = 0;

    public void sessionCreated(IoSession session) {
        System.out.print("新客户连接\t");
    }

    public void sessionOpened(IoSession session) throws Exception {
        count++;
        System.out.println("第 " + count + " 个 client 登陆！address：  "
                + session.getRemoteAddress());
        String a = String.valueOf(count);
        //原本是打算放在type5里的，但考虑这里就能直接进行操作了
        Type_Fives type_fives = new Type_Fives(session);
        System.out.println("测试是否成功发送信息");
        type_fives.send();
/*
        if(type_fives.send())
            System.out.println("success");
        else
            System.out.println("failed");map.put(a, session);
        System.out.println("测试2");
        */
    }

    @Override
    public void messageReceived(IoSession session, Object message) {


        System.out.println(message.toString());
        if (session != null) {
            System.out.println("接收信息");
            //判断信息,获取JSONObject
            //    JSONArray js1 = JSONArray.fromObject(message);
            JSONObject js = JSONObject.fromObject(message);
            //将信息存入总的历史信息里



            String message_type = js.getString("message_type");
            System.out.println(message_type + "type类型");
            switch (message_type) {
                case "5":
                    // Type_Fives type_fives = new Type_Fives(session);
                case "6":
                    System.out.println("类型准备");
                    Logininfo logininfo = new Logininfo();
                    // Date date = ThisTime();
                    ThisTime thisTime = new ThisTime();
                    Date date = thisTime.HaveThisTime();
                    logininfo.setLoginDate(date);
                    logininfo.setUserId(Integer.valueOf(js.getString("from")));
                    InetSocketAddress addr = (InetSocketAddress) session.getRemoteAddress();
                    logininfo.setIpAddr(addr.getAddress().getHostAddress());
                    logininfo.setIpPort(String.valueOf(addr.getPort()));
                    //将信息存放在数据库中
                    Type_Six type_six = new Type_Six(session);
                    type_six.inorup(logininfo);
                    //将信息存放在Map中
                    String user_id = js.getString("from");
                    map.put(user_id, session);
                    FriendsOnline friendsOnline = new FriendsOnline(session);
                    List<Friend> list = friendsOnline.FriendIsOnline(Integer.valueOf(user_id));
                    //新list存放friendid，以及离线消息数目
                    Map<String, Integer> mapoffline = new HashMap<>();


                    JSONObject jsonObject = new JSONObject();
                    String FriendId = "";
                    for (int i = 0; i < list.size(); i++) {

                        FriendId = list.get(i).getFriendId().toString();
                        IoSession sendfrendonline = map.get(FriendId);
                        friend.put(FriendId, sendfrendonline);
                        //离线时，好友发送信息数量
                        int j = new Type_Six().SelectOffline(Integer.valueOf(user_id), Integer.valueOf(FriendId));
                        mapoffline.put(
                                FriendId, j);
                        //如果这个好友在线，可以通过取出的iosession去发送上线信息,这里有个BUG，
                        //暂时不用这个功能了
                        if (sendfrendonline != null) {
                            //jsonObject.put("from", user_id);
                            // jsonObject.put("message_type", "7");
                            //   sendfrendonline.write(jsonObject);
                        } else {
                            System.out.println("sendfrendonline is null" + " and " + FriendId);
                        }
                    }
                    //接下来要写，离线好友信息

                    //好友列表
                    jsonObject.put("message_type", "7");
                    jsonObject.put("textcontent", mapoffline);
                    session.write(jsonObject);
                    mapfriend.put(user_id, friend);
                    break;
                //8 添加好友 ，11 加群
                case "2":
                case "3":
                case "8":
                case "11":
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SaveMessage saveMessage = new SaveMessage();
                            saveMessage.InsertMessage(js);
                        }
                    }).start();

                    IoSession sendaddfriend = null;
                    String to = js.getString("to");
                    if (message_type == "11") {
                        //取出群主的ID,准备发送消息
                        Type_Eleven type_eleven = new Type_Eleven();
                        String group_owner = type_eleven.FindGroupOwner(Integer.valueOf(to));
                        System.out.println("the group " + to + " owner :" + group_owner);
                        to = group_owner;
                    }
                    sendaddfriend = map.get(to);

                    if (sendaddfriend != null) {
                        System.out.println("sendaddfriend不为空");
                        JSONObject json = JSONObject.fromObject(message);
                        sendaddfriend.write(json);
                    } else {
                        //离线存储
                        System.out.println("sendaddfriend为空");
                        Type_Eight type_eight = new Type_Eight();
                        type_eight.InsertOfflineSmg(js);
                    }
                    //  Type_Eight type_eight = new Type_Eight();
                    // type_eight.InsertOfflineSmg(js);
                    break;


            }



/*
            IoSession io = map.get(js.get("to"));
            System.out.println(js.toString());
            JSONObject json = JSONObject.fromObject(message);
            io.write(json);
*/

        } else {
            System.out.println("session is null");

        }
    }

    public void messageSent(IoSession session, Object message) {

        System.out.println("服务器发送消息成功。。。");
    }

    @Override
    public void sessionClosed(IoSession session) {

        System.out.println("断开连接" + session.toString());
        try {


            //从数据库中取出IP和端口号对应的user_id,在Map里将它置为null
            InetSocketAddress addr = (InetSocketAddress) session.getRemoteAddress();
            Type_Six type_six = new Type_Six();
            int user_id = type_six.searchUserId(addr.getAddress().getHostAddress(), String.valueOf(addr.getPort()));
            System.out.println(" closed     user_id  " + user_id);
            map.put(String.valueOf(user_id), null);

            super.sessionClosed(session);
            session.close(true);
            session = null;
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            System.out.println("断开错误");
            e.printStackTrace();
        }

    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status)
//            throws Exception {
    {
        System.out.println(session.toString());
        System.out.println("服务器进入空闲状态...");
        try {
            super.sessionIdle(session, status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        System.out.println("服务器异常.");
        // System.out.print(cause);
        super.exceptionCaught(session, cause);

    }

    public static String DateToMySQLDateTimeString(Date date) {
        final String[] MONTH = {
                "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
        };
        StringBuffer ret = new StringBuffer();
        String dateToString = date.toString();  //like "Sat Dec 17 15:55:16 CST 2005"
        ret.append(dateToString.substring(24, 24 + 4));//append yyyy
        String sMonth = dateToString.substring(4, 4 + 3);
        for (int i = 0; i < 12; i++) {      //append mm
            if (sMonth.equalsIgnoreCase(MONTH[i])) {
                if ((i + 1) < 10)
                    ret.append("-0");
                else
                    ret.append("-");
                ret.append((i + 1));
                break;
            }
        }

        ret.append("-");
        ret.append(dateToString.substring(8, 8 + 2));
        ret.append(" ");
        ret.append(dateToString.substring(11, 11 + 8));

        return ret.toString();
    }
/*
    public Date ThisTime() {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String dateTime = df.format(date);
        Date date1 = null;
        try {
            date1 = df.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }
    */
}
