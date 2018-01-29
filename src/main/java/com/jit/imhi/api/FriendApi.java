package com.jit.imhi.api;

import com.alibaba.fastjson.JSONObject;
import com.jit.imhi.service.FriendService;
import com.jit.imhi.service.HistoryMessageService;
import com.jit.imhi.service.NumInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/friend")
public class FriendApi {

    private FriendService friendService;
    private HistoryMessageService historyMessageService;
    private NumInfoService numInfoService;

    @Autowired

    public FriendApi(FriendService friendService, HistoryMessageService historyMessageService, NumInfoService numInfoService) {
        this.friendService = friendService;
        this.historyMessageService = historyMessageService;
        this.numInfoService = numInfoService;
    }

    @GetMapping("delFriend/{userId}/{friendId}/{messageType}")
    public Object handleDelFriend(@PathVariable Integer userId, @PathVariable Integer friendId, @PathVariable Integer messageType){

        System.out.println("删除好友");
        JSONObject jsonObject = new JSONObject();

        if (userId == null || friendId == null || userId == 0 || friendId == 0) {
            System.out.println("数据不全");
            jsonObject.put("error", "error");
            return jsonObject;
        }

        int num1 = friendService.delFriend(userId, friendId);
        int num2 = friendService.delFriend(friendId, userId);

        if (num1 != 0) {
            if (num2 != 0) {
                jsonObject.put("success", "success");
                return jsonObject;
            }
        }

        jsonObject.put("error", "error");
        return jsonObject;
    }

    @GetMapping("delHisatory/{userId}/{friendId}/{messageType}")
    public Object handleDelHistory(@PathVariable Integer userId, @PathVariable Integer friendId, @PathVariable Integer messageType) {
        System.out.println("删除历史记录");
        JSONObject jsonObject = new JSONObject();

        if (userId == null || friendId == null || userId == 0 || friendId == 0) {
            System.out.println("数据不全");
            jsonObject.put("error", "error");
            return jsonObject;
        }

        int num1 = historyMessageService.delFriend(userId, friendId, messageType);
        int num2 = historyMessageService.delFriend(friendId, userId, messageType);

        if (num1 != 0) {
            if (num2 != 0) {
                jsonObject.put("success", "success");
                return jsonObject;
            }
        }

        jsonObject.put("error", "error");
        return jsonObject;

    }

    @GetMapping("delNumInfo/{userId}/{friendId}/{friendType}")
    public Object handleDelNumInfo(@PathVariable Integer userId, @PathVariable Integer friendId, @PathVariable String friendType) {
        System.out.println("删除NumInfo");
        JSONObject jsonObject = new JSONObject();

        if (userId == null || friendId == null || userId == 0 || friendId == 0) {
            System.out.println("数据不全");
            jsonObject.put("error", "error");
            return jsonObject;
        }

        int num1 = numInfoService.delNumInfo(userId, friendId, friendType);
        int num2 = numInfoService.delNumInfo(friendId, userId, friendType);


        if (num1 != 0) {
            if (num2 != 0) {
                jsonObject.put("success", "success");
                return jsonObject;
            }
        }

        jsonObject.put("error", "error");
        return jsonObject;

    }

}
