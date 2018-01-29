package com.jit.imhi.api;


import com.alibaba.fastjson.JSONObject;
import com.jit.imhi.model.User;
import com.jit.imhi.service.GroupUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("api/groupuser")
public class GroupUserApi {

    @Autowired
    GroupUserService groupUserService;


    /*

      create by liuyunxing
      功能： 返回群内成员的详细信息，其中成员在群中的身份包含在password字段中返回
     */
    @GetMapping("getUsers/{groupId}")

    public JSONObject getGroupUsers(@PathVariable Integer groupId) {

        JSONObject jsonObject = new JSONObject();
        List<User> userList = groupUserService.getUsersInfo(groupId);

        if (userList == null) {
            jsonObject.put("message", "无查询结果");
        } else {
            jsonObject.put("message", "查询成功");
        }
        jsonObject.put("users", userList);

        return jsonObject;
    }

}
