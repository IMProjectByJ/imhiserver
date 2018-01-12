package com.jit.imhi.api;

import com.alibaba.fastjson.JSONObject;
import com.jit.imhi.model.User;
import com.jit.imhi.service.AuthenticationService;
import com.jit.imhi.service.UserService;
import com.jit.imhi.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserApi {
    private UserService userService;
    private AuthenticationService authenticationService;
    @Autowired

    public UserApi(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }
// 通过手机号登录
     /*
     添加token方式， create by liuyunxing


    */
    @GetMapping("{phoneName}/{password}")
    public Object handleLogin(@PathVariable String phoneName, @PathVariable String password)
    {
        JSONObject jsonObject = new JSONObject();
        User user = userService.findUserByPhoneNum(phoneName); //所获得的结果是经过两次加密的
        if (user == null)
        {
            jsonObject.put("err","用户不存在");
            return jsonObject;// 用户名不存在，返回null
        }

         if (!userService.comparePassword(password, user.getUserPassword())) {
             jsonObject.put("err","密码错误");
             return jsonObject;
         }// 密码输入正确，返回User对象,/*??  是否要同时将离线信息穿回？*/

        String token  = authenticationService.getToken(user); // 获得token
        user.setUserPassword(token); // 将用户token包含在用户密码字段中
        return user;

    }
    /*
      通过账号登录
     */

    @GetMapping("id/{userId}/{password}")
    public Object handleIdLogin(@PathVariable Integer userId, @PathVariable String password)
    {
        User user = userService.findUserByUserId(userId);
        JSONObject jsonObject = new JSONObject();
        if (user == null)
        {
            jsonObject.put("err","用户不存在");
            return jsonObject;// 用户名不存在，返回null
        }
        if (!userService.comparePassword(password, user.getUserPassword())) {
            jsonObject.put("err","密码错误");
            return jsonObject;
        }// 密码输入正确，返回User对象,/*??  是否要同时将离线信息穿回？*/

        String token  = authenticationService.getToken(user); // 获得token
        user.setUserPassword(token); // 将用户token包含在用户密码字段中
        return user;

    }
// 注册
    @PostMapping("register")

    public Object add(@RequestBody User user)
    {
        if (user == null)
        {
            return  null;
        }
        if (userService.findUserByPhoneNum(user.getPhoneNum()) != null)
        {
            JSONObject  jsonObject  = new JSONObject();
            jsonObject.put("err","用户已存在");
            return  jsonObject;
        }
        user.setUserPassword(MD5Util.encrypt(user.getUserPassword())); // md5加密
        if ((user = userService.insertUser(user)) == null)
        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("err","注册失败！");
            return  jsonObject;
        }
        System.out.println("--------add--one-------------");
        return user;
    }

}
