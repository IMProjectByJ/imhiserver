package com.jit.imhi.api;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.jit.imhi.mapper.FriendMapper;
import com.jit.imhi.model.Friend;
import com.jit.imhi.model.User;
import com.jit.imhi.service.FriendService;
import com.jit.imhi.service.UserService;
import com.jit.imhi.utils.MD5Util;
import com.mina.socket.MyIoHandler;
import jdk.nashorn.internal.scripts.JS;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserApi {
    private UserService userService;
    private FriendService friendService;

    @Autowired

    public UserApi(UserService userService, FriendService friendService) {
        this.userService = userService;
        this.friendService = friendService;
    }

    // 通过手机号登录xd
    @GetMapping("{phoneName}/{password}")
    public Object handleLogin(@PathVariable String phoneName, @PathVariable String password) {
        JSONObject jsonObject = new JSONObject();
        User user = userService.findUserByPhoneNum(phoneName); //两次加密
        if (user == null) {
            jsonObject.put("err", "用户不存在");
            return jsonObject;// 用户名不存在，返回null
        }

        if (user.getUserPassword().equals(MD5Util.encrypt(password))) {
            return user;
        }// 密码输入正确，返回User对象,/*??  是否要同时将离线信息穿回？*/
        jsonObject.put("err", "密码错误");

        return jsonObject;

    }

    @GetMapping("id/{userId}/{password}")
    public Object handleIdLogin(@PathVariable Integer userId, @PathVariable String password) {
        User user = userService.findUserByUserId(userId);
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("err", "用户不存在");
            return jsonObject;// 用户名不存在，返回null
        }
        if (user.getUserPassword().equals(MD5Util.encrypt(password))) {
            return user;
        }
        jsonObject.put("err", "密码错误");
        return jsonObject;
    }

    // 注册
    @PostMapping("register")

    public Object add(@RequestBody User user) {
        if (user == null) {
            return null;
        }
        if (userService.findUserByPhoneNum(user.getPhoneNum()) != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("err", "用户已存在");
            return jsonObject;
        }
        user.setUserPassword(MD5Util.encrypt(user.getUserPassword())); // md5加密
        if ((user = userService.insertUser(user)) == null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("err", "注册失败！");
            return jsonObject;
        }
        System.out.println("--------add--one-------------");
        return user;
    }

    //响应搜索好友
    @GetMapping("add_search_by_number/{phoneNum}")
    public Object handlePhoneNumber(@PathVariable String phoneNum) {
        JSONObject jsonObject = new JSONObject();
        User user = userService.findUserByPhoneNum(phoneNum);
        System.out.println(user.getNikname());
        if (user == null) {
            jsonObject.put("err", "phone用户不存在");
            System.out.println("err");
            return jsonObject;
        }
        jsonObject.put("add_search", user);
        return jsonObject;
    }

    @GetMapping("add_search_by_uid/{userId}")
    public Object handleUerId(@PathVariable Integer userId) {
        JSONObject jsonObject = new JSONObject();
        User user = userService.findUserByUserId(userId);
        if (user == null) {
            jsonObject.put("err", "id用户不存在");
            return jsonObject;
        }
        jsonObject.put("add_search", user);
        return jsonObject;
    }

    @GetMapping("find_friend_list/{userId}")
    public Object findFriendList(@PathVariable Integer userId) {

        System.out.println("\n\n\n测试输出" + userId + "\n\n");
        List<Friend> list;
        list = friendService.selectByUserId(userId);
        System.out.println(list.size() + " 数量");
        List<User> retval = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {

            User user = new User();
            User user1 = userService.findUserByUserId(list.get(i).getFriendId());
            user.setUserId(user1.getUserId());
            user.setNikname(user1.getNikname());
            user.setPhoneNum(user1.getPhoneNum());
            user.setHeadUrl(user1.getHeadUrl());
            retval.add(user);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("retval", retval);
        return jsonObject;
    }
    //响应详细信息
    @GetMapping("details/{userId}")
    public Object handleDetails(@PathVariable Integer userId){
        JSONObject jsonObject = new JSONObject();
        User user = userService.findUserByUserId(userId);



       String  str = MyIoHandler.DateToMySQLDateTimeString(user.getBirth());

        System.out.println("cehsi:"+str);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        Date date = new Date(str);
//        try {
//           date=sdf.parse(str);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        user.setBirth(date);
        System.out.println("cehsi:"+user.getBirth());
        if (user == null)
        {
            jsonObject.put("err", "用户不存在");
            System.out.println("err");
            return jsonObject;
        }

        jsonObject.put("details", user);
        return jsonObject;

    }
//   System.out.println("响应用户更新个人信息");

    //响应用户更新个人信息
    @GetMapping("information/{information}")
    public Object handleInformation(@PathVariable String information){
        System.out.println("响应用户更新个人信息"+information);
        User user = new Gson().fromJson(information, User.class);
        System.out.println("测试");
        System.out.println(user.toString());
        JSONObject jsonObject = new JSONObject();

        if (user == null) {
            jsonObject.put("err", "failed");
            System.out.println("user is null");
            return jsonObject;
        } else {
            System.out.println("user is yes");
            jsonObject.put("success", "success");
            return jsonObject;
        }
    }

}
