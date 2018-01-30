package com.jit.imhi.socket;


import com.jit.imhi.api.LogininfoApi;
import com.jit.imhi.mapper.GroupuserMapper;
import com.jit.imhi.mapper.NuminfoMapper;
import com.jit.imhi.mapper.UserMapper;
import com.jit.imhi.model.*;
import com.jit.imhi.service.*;
import com.jit.imhi.socket.Save.NumToHave;
import com.jit.imhi.socket.Save.NumToSave;
import com.jit.imhi.socket.Save.SaveMessage;
import com.jit.imhi.socket.Save.ceshi;
import com.jit.imhi.socket.Util.ThisTime;
import com.jit.imhi.socket.onlineaction.FriendsOnline;
import com.jit.imhi.socket.typeaction.Type_Fives;
import com.jit.imhi.socket.typeaction.Type_Six;
import com.sun.javafx.collections.MappingChange;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.net.InetSocketAddress;

import java.security.acl.Group;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.jit.imhi.socket.typeaction.*;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import sun.security.acl.GroupImpl;


public class MyIoHandler extends IoHandlerAdapter {
    Map<String, IoSession> map = new HashMap<String, IoSession>();
    Map<String, String> chating = new HashMap<>();
    Numinfo numinfo1;
    private int count = 0;
    IoSession sendaddfriend;
    Map<String, Numinfo> numinfo;
    Type_Nine type_nine = new Type_Nine();
    @Autowired
    ceshi ce;

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

            String from_id, to_id;
            //  from_id = js.getString("from");
            String message_type = js.getString("message_type");
            System.out.println(message_type + "type类型");
            String user_id = "";
            String to = "";
            SaveMessage saveMessage;
            int new_id;

            switch (message_type) {
                case "5":
                    // Type_Fives type_fives = new Type_Fives(session);
                    break;
                case "6":
                    //    System.out.println(getBeanTest().test().getNikname());
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
                    //将信息存放在数据库中,这个是登陆信息
                    Type_Six type_six = new Type_Six(session);
                    type_six.inorup(logininfo);
                    //将信息存放在Map中
                    user_id = js.getString("from");

                    map.put(user_id, session);
                    System.out.println("map.put  " + user_id + "   " + session);

                    //接下来要写，离线好友信息
                    //Map<String, Map<String, Numinfo>> mapNuminfo
                    numinfo = new HashMap<>();
                    // NumInfoMap.mapNuminfo.put(user_id,numinfo);
                    List<Numinfo> list = new ArrayList<>();
                    list = NumToHave.HaveOffNum(user_id);
                    System.out.println("type6:" + list.size());
                    String key1 = "";
                    for (int i = 0; i < list.size(); i++) {
                        Numinfo numinfo1 = list.get(i);
                        System.out.println("numinfo1 zhi:"+numinfo1.getFriendId());
                        System.out.println("numinfo1 zhi:"+numinfo1.getFriendType());
                        if (numinfo1 != null) {
                            if(numinfo1.getFriendType().equals("1")) {
                                key1 = String.valueOf(list.get(i).getFriendId()) + "|"
                                        + list.get(i).getFriendType();
                            }
                            else if(numinfo1.getFriendType().equals("2")){
                                key1 = String.valueOf(list.get(i).getFriendId()) + "|"
                                        + list.get(i).getFriendType();

                            }  else if(numinfo1.getFriendType().equals("3")){
                                key1 = "9999|"+ list.get(i).getFriendType();

                            }

                            numinfo.put(key1, numinfo1);
                            System.out.println("numinfo1:" + numinfo1.getUserId());
                            System.out.println("numinfo1:" + numinfo1.getNewId());
                            System.out.println("numinfo1:" + key1);
                            //    NumInfoMap.mapNuminfo.get(user_id).put(key, numinfo1);

                        }
                    }
                    //这里需要同时去数据库里取对应的数据
                    //numinfo.put("9999",null);
                    //NumInfoMap.setMapCache(user_id,numinfo);
                    NumInfoMap.mapNuminfo.put(user_id, numinfo);
                    System.out.println("type6:" + user_id + " numinfo数量" + numinfo.size());

                    System.out.println("case6测试2");
                    NumInfoMap.ceshi();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("message_type", "7");
                    jsonObject.put("textcontent", numinfo);
                    session.write(jsonObject);
                    //  numinfo.clear();
                    break;
                //8 添加好友 ，11 加群


                //请求加好友
                case "8":
                    to_id = js.getString("to");
                    from_id = js.getString("from");
                    saveMessage = new SaveMessage();

                    // new_id
                    HistoryMessage historyMessage = saveMessage.InsertMessage(js);
                    new_id = historyMessage.getMessageId();
                    sendaddfriend = null;
                    //准备动作，存入numinfo方式，map，还是数据库
                    numinfo1 = new Numinfo();
                    numinfo1.setFriendType("3");
                    numinfo1.setUserId(Integer.valueOf(to_id));
                    numinfo1.setFriendId(9999);
                    numinfo1.setNewId(new_id);
                    System.out.println(new_id);
                    // System.out.println(mapNuminfo.get(to));


                    sendaddfriend = map.get(to_id);
                    //map.containsValue()
                    System.out.println("获取要发送人的iosession： " + sendaddfriend);
                    if (sendaddfriend != null) {
                        NumInfoMap.mapNuminfo.get(to_id).put("9999|3", numinfo1);
                        System.out.println("sendaddfriend不为空");
                        JSONObject json = new JSONObject();
                        //  JSONObject json1 = JSONObject.fromObject(numinfo1);


                        System.out.println("type:8 =" + historyMessage.getDate());
                        String updatedate = DateToMySQLDateTimeString(historyMessage.getDate());
                        JSONObject json1 = JSONObject.fromObject(historyMessage);
                        json1.put("date", updatedate);
                        System.out.println(json1.toString());
                        json.put("message_type", "8");
                        json.put("textcontent", json1);

                        User user = getUserService().findUserByUserId(Integer.valueOf(from_id));
                        json.put("nikname", user.getNikname());
                        sendaddfriend.write(json);
                    } else {
                        //离线存储
                        System.out.println("sendaddfriend   为空");
                        NumToSave.offToSave(numinfo1);
                    }
                    break;
                //同意好友请求
                case "9":

                    to_id = js.getString("to");
                    from_id = js.getString("from");
                    Date date1 = ThisTime.HaveThisTime();
                    Friend friend = new Friend(Integer.valueOf(from_id), Integer.valueOf(to_id),
                            date1);
                    Friend friend1 = new Friend(Integer.valueOf(to_id), Integer.valueOf(from_id),
                            date1);
                    //要先去数据库里把数据加好

                    type_nine.InsertFriend(friend, friend1);
                    //去数据库里把那条请求的数据置为9
                    HistoryMessage historyMessage2 = new HistoryMessage();
                    historyMessage2.setUserFromId(Integer.valueOf(to_id));
                    historyMessage2.setToId(Integer.valueOf(from_id));
                    historyMessage2.setMessageType(9);
                    type_nine.ChangeTheType(historyMessage2);
                    //接下来，生成一个“你们已经成为好友的消息框”，
                    saveMessage = new SaveMessage();
                    //newJson = new JSONObject();
                    js.put("texttype", "1");
                    js.put("textcontent", "我们已经成为好友了，快来聊天吧");
                    js.put("message_type", "2");
                    String time = DateToMySQLDateTimeString(new ThisTime().HaveThisTime());
                    js.put("date", time);
                    System.out.println(js.toString());
                    HistoryMessage historyMessage1 = saveMessage.InsertMessage(js);

                    new_id = historyMessage1.getMessageId();
                    //同8的存入方式，这里再写一遍
                    sendaddfriend = null;
                    //准备动作，存入numinfo方式，map，还是数据库
                    to = js.getString("to");
                    from_id = js.getString("from");
                    numinfo1 = new Numinfo();
                    numinfo1.setFriendType("1");
                    numinfo1.setUserId(Integer.valueOf(to));
                    numinfo1.setFriendId(Integer.valueOf(from_id));
                    numinfo1.setNewId(new_id);

                    //判断是否在在线
                    sendaddfriend = map.get(to);
                    if (sendaddfriend != null) {
                        //在线
                        if (chating.get(to) != null && chating.get(to).equals(user_id)) {
                            numinfo1.setOldId(new_id);
                            NumInfoMap.mapNuminfo.get(to).put(from_id + "|1", numinfo1);
                        } else {
                            NumInfoMap.mapNuminfo.get(to).put(from_id + "|1", numinfo1);
                        }
                        System.out.println("sendaddfriend不为空");
                        JSONObject json = JSONObject.fromObject(historyMessage1);
                        JSONObject json1 = new JSONObject();
                        String updatedate = DateToMySQLDateTimeString(historyMessage1.getDate());
                        json.put("date", updatedate);
                        json1.put("message_type", "9");
                        json1.put("textcontent", json);
                        sendaddfriend.write(json1);
                    } else {
                        //离线存储
                        System.out.println("sendaddfriend   为空");
                        NumToSave.offToSave(numinfo1);
                    }

                    break;
                //拒绝好友请求
                case "10":
                    //去数据库里把那条请求的数据置为9
                    to_id = js.getString("to");
                    from_id = js.getString("from");
                    HistoryMessage refuseMsg = new HistoryMessage();
                    refuseMsg.setUserFromId(Integer.valueOf(to_id));
                    refuseMsg.setToId(Integer.valueOf(from_id));
                    refuseMsg.setMessageType(10);
                    Type_Nine type_ten = new Type_Nine();
                    type_ten.ChangeTheType(refuseMsg);
                    //这时要给客户端信息了，不过是拒绝的信息
                    sendaddfriend = map.get(to);
                    if (sendaddfriend != null) {
                        System.out.println("sendaddfriend不为空");
                        sendaddfriend.write(js);
                    }
                    break;

                case "2":
                case "11":
                    from_id = js.getString("from");
                    IoSession sendaddfriend = null;
                    String to1 = js.getString("to");
                    saveMessage = new SaveMessage();
                    historyMessage = saveMessage.InsertMessage(js);
                    new_id = historyMessage.getMessageId();
                    System.out.println(new_id);
                    js.put("message_id", new_id);
                    sendaddfriend = null;
                    //准备动作，存入numinfo方式，map，还是数据库
                    // System.out.println(mapNuminfo.get(to));
                    Numinfo numinfo2 = new Numinfo();
                    if (message_type.equals("11")) {
                        //取出群主的ID,准备发送消息

                        Type_Eleven type_eleven = new Type_Eleven();
                        String group_owner = type_eleven.FindGroupOwner(Integer.valueOf(to1));
                        System.out.println("the group " + to1 + " owner :" + group_owner);
                        to1 = group_owner;
                        numinfo2.setFriendType("3");
                    } else {
                        numinfo2.setFriendType("1");
                    }
                    numinfo2.setNewId(new_id);
                    numinfo2.setUserId(Integer.valueOf(to1));
                    //
                    //
                    if (message_type.equals("11")) {
                        numinfo2.setFriendId(9999);
                    } else {
                        numinfo2.setFriendId(Integer.valueOf(js.getString("from")));
                    }

                    sendaddfriend = map.get(to1);
                    JSONObject jsonObject1 = new JSONObject();
                    Date date2 = historyMessage.getDate();
                    JSONObject js1 = JSONObject.fromObject(historyMessage);
                    js1.put("date", DateToMySQLDateTimeString(date2));
                    jsonObject1.put("message_type", "2");

                    jsonObject1.put("textcontent", js1);
                    if (sendaddfriend != null) {
                        System.out.println("sendaddfriend不为空");
                        //  JSONObject json = JSONObject.fromObject(message);

                        if (chating.get(to1) != null && chating.get(to1).equals(user_id)) {
                            numinfo2.setOldId(new_id);

                            NumInfoMap.mapNuminfo.get(to1).put(from_id + "|1", numinfo2);
                        } else {
                            System.out.println(to1);
                            NumInfoMap.mapNuminfo.get(to1).put(from_id + "|1", numinfo2);
                        }
                        sendaddfriend.write(jsonObject1);

                    } else {
                        //离线存储
                        System.out.println("sendaddfriend为空");
                        NumToSave.offToSave(numinfo2);
                        //Type_Eight type_eight = new Type_Eight();
                        //  type_eight.InsertOfflineSmg(js);
                    }
                    break;

                case "13":
                    JSONObject jsonObject2 = JSONObject.fromObject(js.getString("textcontent"));

                    user_id = jsonObject2.getString("from");
                    to = jsonObject2.getString("to");
                    chating.put(user_id, to);


                    String text_type = jsonObject2.getString("texttype");
                    String key = "";
                    String friendtype = "";
                    if (text_type.equals("7")) {
                        friendtype = "1";
                    } else if (text_type.equals("8")) {
                        friendtype = "2";
                    } else {
                        friendtype = "3";
                    }
                    key = to + "|" + friendtype;
                    Numinfo numinfo3 = NumInfoMap.mapNuminfo.get(user_id).get(key);

                    //  System.out.println("type13:"+numinfo.get(user_id).getNewId());

                    if (NumInfoMap.mapNuminfo.get(user_id).isEmpty()) {
                        System.out.println("numinfo is empty");
                    }
                    NumInfoMap.ceshi();
                    System.out.println("type13:" + key);
                    System.out.println("type13:" + NumInfoMap.mapNuminfo.size());


                    //其实是需要最新的id(本地)
                    //  String old_id = js.getString("textcontent");
                    // numinfo3.setOldId(Integer.valueOf(old_id));
                    if(numinfo3 != null)
                    if (numinfo3.getNewId() != null)
                        numinfo3.setOldId(Integer.valueOf(numinfo3.getNewId()));
                    else
                        numinfo3.setOldId(0);
                //    System.out.println("type13:" + numinfo3.getOldId());
                    NumInfoMap.mapNuminfo.get(user_id).put(key, numinfo3);
                    break;
                case "14":

                    System.out.println("jsstr + :" + js);
                    to_id = js.getString("to");
                    from_id = js.getString("from");
                    HistoryMessage historyMessage14 = new HistoryMessage();
                    historyMessage14.setUserFromId(Integer.valueOf(to_id));
                    historyMessage14.setToId(Integer.valueOf(from_id));
                    historyMessage14.setMessageType(14);
                    type_nine.ChangeTheGroupType(historyMessage14);
                    Groupuser groupuser = new Groupuser();
                    groupuser.setGroupId(Integer.valueOf(from_id));
                    groupuser.setGroupMembership("2");
                    groupuser.setMemberId(Integer.valueOf(to_id));
                    getGroupOperateService().insertGroupuser(groupuser);
                    break;
                case "3":
                    String fromid3 = js.getString("from");

                    String to3 = js.getString("to");
                    saveMessage = new SaveMessage();
                    historyMessage = saveMessage.InsertMessage(js);
                    int new_id3 = historyMessage.getMessageId();
                    System.out.println(new_id3);
                    js.put("message_id", new_id3);
                     IoSession sendaddfriend3 = null;
                    //准备动作，存入numinfo方式，map，还是数据库
                    // System.out.println(mapNuminfo.get(to));
                    Numinfo numinfo = new Numinfo();
                    numinfo.setFriendType("2");
                    numinfo.setNewId(new_id3);
                    numinfo.setUserId(Integer.valueOf(fromid3));
                    numinfo.setFriendId(Integer.valueOf(to3));
                    //离线存储
                    System.out.println("数据存入numinfo");

                    List<User> list3 = getGroupUser().getUsers(Integer.valueOf(to3));

                    for (int i = 0; i < list3.size(); i++) {
                        System.out.println("send ioseesion"+list3.get(i).getUserId() );
                        sendaddfriend3 = map.get(String.valueOf(list3.get(i).getUserId()));

//                        Iterator<Map.Entry<String, IoSession>> entries = map.entrySet().iterator();
//                        while (entries.hasNext())
//                        {
//                            Map.Entry<String, IoSession> entry = entries.next();
//                            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//                        }


                        JSONObject jsonObject3 = new JSONObject();
                        Date date3 = historyMessage.getDate();
                        JSONObject js3= JSONObject.fromObject(historyMessage);
                        js3.put("date", DateToMySQLDateTimeString(date3));
                      //  js3.put("from",to3);
                       // js3.put("to",list3.get(i).getUserId());
                        jsonObject3.put("message_type", "3");
                        jsonObject3.put("textcontent", js3);
                        if (sendaddfriend3 != null) {
                            System.out.println("sendaddfriend不为空:"+list3.get(i).getUserId()
                            );

                            String key3 = to3+"|2";
                            NumInfoMap.mapNuminfo.get(String.valueOf(list3.get(i).getUserId())).put(key3,numinfo);
                            if(list3.get(i).getUserId().equals(fromid3))
                                continue;
                            sendaddfriend3.write(jsonObject3);
                        } else {
                            System.out.println("sendaddfriend为空"+list3.get(i).getUserId());
                            numinfo.setUserId(list3.get(i).getUserId());
                            NumToSave.offGroupToSave(numinfo);
                        }
                    }
                    break;
            }


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
        NumInfoMap.ceshi();
        try {


            //从数据库中取出IP和端口号对应的user_id,在Map里将它置为null
            InetSocketAddress addr = (InetSocketAddress) session.getRemoteAddress();
            Type_Six type_six = new Type_Six();
            int user_id = type_six.searchUserId(addr.getAddress().getHostAddress(), String.valueOf(addr.getPort()));
            System.out.println(" closed     user_id  " + user_id);
            if (user_id != 0) {

                Map<String, Numinfo> numinfoMap = NumInfoMap.mapNuminfo.get(String.valueOf(user_id));
                for (Numinfo value : numinfoMap.values()) {
                    // NumToSave.offToSave(value);
                    NumToSave.allInfoSave(value);
                }
                numinfoMap.clear();
                map.put(String.valueOf(user_id), null);
                super.sessionClosed(session);
                session.close(true);
                session = null;
            }
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


    private static ApplicationContext applicationContext;//启动类set入，调用下面set方法

    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

    public ceshi getBeanTest() {
        return (ceshi) applicationContext.getBean(ceshi.class);
    }

    public UserService getUserService() {
        return (UserService) applicationContext.getBean(UserService.class);
    }

    public GroupOperateService getGroupOperateService() {
        return applicationContext.getBean(GroupOperateService.class);
    }

    public NumInfoService getNumInfoService() {
        return applicationContext.getBean(NumInfoService.class);
    }

    public GroupUserService getGroupUser() {
        return applicationContext.getBean(GroupUserService.class);
    }
}
