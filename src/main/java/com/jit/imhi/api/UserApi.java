package com.jit.imhi.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.jit.imhi.model.Friend;
import com.jit.imhi.model.User;
import com.jit.imhi.service.AuthenticationService;
import com.jit.imhi.service.FriendService;
import com.jit.imhi.service.SendSMSService;
import com.jit.imhi.service.UserService;
import com.jit.imhi.utils.MD5Util;
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
    private AuthenticationService authenticationService;
    private SendSMSService sendSMSService;

    @Autowired

    public UserApi(UserService userService, AuthenticationService authenticationService,
                   FriendService friendService, SendSMSService sendSMSService) {
        this.userService = userService;
        this.friendService = friendService;
        this.authenticationService = authenticationService;
        this.sendSMSService = sendSMSService;
    }


// 通过手机号登录
     /*
     添加token方式， create by liuyunxing


    */

    // 通过手机号登录xd
    @GetMapping("{phoneName}/{password}")
    public Object handleLogin(@PathVariable String phoneName, @PathVariable String password) {
        JSONObject jsonObject = new JSONObject();
        User user = userService.findUserByPhoneNum(phoneName); //两次加密

        if (user == null) {
            jsonObject.put("err", "用户不存在");
            System.out.println("用户不存在");
            return jsonObject;// 用户名不存在，返回null
        }

        if (!userService.comparePassword(password, user.getUserPassword())) {
            jsonObject.put("err", "密码错误");
            System.out.println("密码错误");
            return jsonObject;

        }// 密码输入正确，返回User对象,/*??  是否要同时将离线信息穿回？*/

        String token = authenticationService.getToken(user); // 获得token
        user.setUserPassword(token); // 将用户token包含在用户密码字段中
        System.out.println("正确返回");
        return user;

    }
    /*
      通过账号登录
     */

    @GetMapping("id/{userId}/{password}")
    public Object handleIdLogin(@PathVariable Integer userId, @PathVariable String password) {
        User user = userService.findUserByUserId(userId);
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("err", "用户不存在");
            return jsonObject;// 用户名不存在，返回null
        }
        if (!userService.comparePassword(password, user.getUserPassword())) {
            jsonObject.put("err", "密码错误");
            return jsonObject;
        }// 密码输入正确，返回User对象,/*??  是否要同时将离线信息穿回？*/

        String token = authenticationService.getToken(user); // 获得token
        user.setUserPassword(token); // 将用户token包含在用户密码字段中
        return user;

    }

    /*
      modify by liuyunxng
      1-23
     */
    // 注册
    @PostMapping("register/{code}")

    public JSONObject add(@RequestBody User user, @PathVariable String code) {

        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            return null;
        }
        JSONObject authonNum = sendSMSService.authonCode(user.getPhoneNum(), code);
        if (authonNum.get("message").equals("验证成功")) {
            if (userService.findUserByPhoneNum(user.getPhoneNum()) != null) {
                jsonObject.put("message", "用户已存在");
                return jsonObject;
            }
            user.setUserPassword(MD5Util.encrypt(user.getUserPassword())); // md5加密
            if ((user = userService.insertUser(user)) == null) {
                jsonObject.put("message", "注册失败！");
                return jsonObject;
            }
            System.out.println("--------add--one-------------");
            jsonObject.put("message","注册成功");
            System.out.println(user.toString());
            String userId = user.getUserId()+"";
            jsonObject.put("userId",userId);
            return jsonObject;
        } else {
            return authonNum;
        }
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
        user.setUserPassword("");
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
        user.setUserPassword("");
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
            user.setUserPassword("");
            retval.add(user);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("retval", retval);
        return jsonObject;
    }

    //响应详细信息
    @GetMapping("details/{userId}")
    public Object handleDetails(@PathVariable Integer userId) {
        JSONObject jsonObject = new JSONObject();
        User user = userService.findUserByUserId(userId);

        String jsonTomap = new Gson().toJson(user);
        System.out.println(jsonTomap);
        jsonObject = JSON.parseObject(jsonTomap);
        System.out.println(jsonObject.get("birth"));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = sdf.format(user.getBirth());
        System.out.println(str);

        jsonObject.put("birth", str);

        System.out.println("cehsi" + user.getBirth());
        if (user == null) {
            jsonObject.put("err", "用户不存在");
            System.out.println("err");
            return jsonObject;
        }
        //jsonObject.put("details", user);
        return jsonObject;

    }
//   System.out.println("响应用户更新个人信息");

    //响应用户更新个人信息
    @GetMapping("information/{information}")
    public Object handleInformation(@PathVariable String information) {
        System.out.println("响应用户更新个人信息" + information);
        JSONObject jsonObject = new JSONObject();
        jsonObject = JSON.parseObject(information);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(jsonObject.getString("birth"));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        jsonObject.put("birth", date);
        String str = new Gson().toJson(jsonObject);
        System.out.println(str);

        User user = new Gson().fromJson(str, User.class);
        System.out.println("测试");
        System.out.println(user.toString());
        JSONObject return_json = new JSONObject();

        int num = userService.updateInfoByUserId(user);
        System.out.println(num);

        if (user == null) {
            return_json.put("err", "failed");
            System.out.println("user is null");
            return return_json;
        } else {
            System.out.println("user is yes");
            return_json.put("success", "success");
            return return_json;
        }
    }

    @GetMapping("ForgetPassword/{phone}/{password}/{numCode}") // 密码经md5加密

    public JSONObject handleForget(@PathVariable String phone,
                                   @PathVariable String password, @PathVariable String numCode) {

        System.out.println("-----------forget-----------");


        JSONObject jsonObject = new JSONObject();
        JSONObject authonNum = sendSMSService.authonCode(phone, numCode);
        if (authonNum.getString("message").equals("验证成功")) {

            System.out.println("验证成功");

            User user = userService.findUserByPhoneNum(phone);
            if (user != null) {
                user.setUserPassword(MD5Util.encrypt(password));
                userService.updateInfoByUserId(user);
                jsonObject.put("message", "修改成功");
            } else {
                jsonObject.put("message", "账号不存在");
            }
        } else {
            jsonObject.put("message", "验证码不正确");
        }

        return jsonObject;
    }

}
