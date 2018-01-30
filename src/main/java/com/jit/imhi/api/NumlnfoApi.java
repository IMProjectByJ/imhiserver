package com.jit.imhi.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jit.imhi.model.HistoryMessage;
import com.jit.imhi.model.Numinfo;
import com.jit.imhi.service.GroupOperateService;
import com.jit.imhi.service.HistoryMessageService;
import com.jit.imhi.service.NumInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/numinfo")
public class NumlnfoApi {
    private NumInfoService numInfoService;
    private HistoryMessageService historyMessageService;
    private GroupOperateService groupOperateService;


    @Autowired
    public NumlnfoApi(NumInfoService numInfoService,HistoryMessageService historyMessageService,
                      GroupOperateService groupOperateService) {
        this.numInfoService = numInfoService;
        this.historyMessageService = historyMessageService;
        this.groupOperateService = groupOperateService;
    }
    @GetMapping("register/{json}")
    public Object  SelectNum(@PathVariable String json){
        String new_id ;
        int num = 0;
        System.out.println("接收到了新的jsonObect"+json.toString());

        JSONObject jsonObject1 = JSON.parseObject(json);
        JSONObject jsonObject =new  JSONObject();
        List<HistoryMessage> list = new ArrayList<>();
        List<HistoryMessage> list1 = new ArrayList<>();
        Numinfo numinfo = new Numinfo();
        numinfo.setFriendId(jsonObject1.getInteger("friend_id"));
        numinfo.setNewId(jsonObject1.getInteger("new_id"));
        numinfo.setFriendType(jsonObject1.getString("friend_type"));
        numinfo.setOldId(jsonObject1.getInteger("old_id"));
        numinfo.setUserId(jsonObject1.getInteger("user_id"));
       String friendType =  numinfo.getFriendType();
        //接下来要通过这些new_id,friendtype为3的要额外去找一次信息
        if(friendType.equals("1")){

            Numinfo numinfo1 = numInfoService.selectByPrimaryKey(jsonObject1.getInteger("user_id"),
                    jsonObject1.getInteger("friend_id"),  jsonObject1.getString("friend_type"));

//            if(!numinfo1.getNewId().equals(jsonObject1.getInteger("new_id")) ) {
            if(!numinfo1.getNewId().equals(numinfo1.getOldId())) {
            //    numinfo.setOldId(jsonObject1.getInteger("new_id"));
                numinfo.setOldId(numinfo1.getOldId());
                numinfo.setNewId(numinfo1.getNewId());
                new_id = numInfoService.SelectNumOne(numinfo);
                numinfo.setFriendType("2");
                numinfo.setNewId(Integer.valueOf(new_id));
                int from = numinfo.getUserId();
                numinfo.setUserId(from);
                list = historyMessageService.selectNotic(numinfo);
            }

        } else if(friendType.equals("2")){
            new_id = numInfoService.SelectNumTwo(numinfo);
            numinfo.setFriendType("3");
            numinfo.setNewId(Integer.valueOf(new_id));
            num = historyMessageService.SelectTheNum(numinfo);
        } else if(friendType.equals("3")){
            new_id = numInfoService.SelectNumOne(numinfo);
            System.out.println("new_id is "+new_id);
            numinfo.setFriendType("8");
            numinfo.setNewId(Integer.valueOf(new_id));
            list = historyMessageService.selectNotic(numinfo);
            System.out.println("查找拒绝消息");


            List<String> groupown = groupOperateService.selectAllOwn(jsonObject1.getInteger("user_id"));
            numinfo.setFriendType("11");
            for(int i = 0;i<groupown.size();i++){
                numinfo.setUserId(Integer.valueOf(groupown.get(i)));
                list1 = historyMessageService.selectNotic(numinfo);
                list.addAll(list1);
            }
            //    numinfo.setFriendType("11");
         //   list1 = historyMessageService.selectNotic(numinfo);



            Numinfo numinfo1 = numInfoService.selectByPrimaryKey(numinfo.getUserId(),9999,"4");
            if(numinfo1!=null) {
                list1 = historyMessageService.selectNotic(numinfo);
            }
            list.addAll(list1);
        }
//        if(list.size() == 0){
//            HistoryMessage historyMessage = new HistoryMessage();
//            historyMessage.setMessageType(0);
//            list.add(historyMessage);
//        }

    return  list;
    }

    @GetMapping("selectgroup/{json}")
    public Object  SelectGroupNum(@PathVariable String json) {
        String new_id ;
        int num = 0;
        System.out.println("接收到了新的jsonObect"+json.toString());

        JSONObject jsonObject1 = JSON.parseObject(json);
        JSONObject jsonObject =new  JSONObject();
        List<HistoryMessage> list = new ArrayList<>();
        Numinfo numinfo = new Numinfo();
        numinfo.setNewId(jsonObject1.getInteger("new_id"));
        numinfo.setFriendType(jsonObject1.getString("friend_type"));
        numinfo.setOldId(jsonObject1.getInteger("old_id"));
        numinfo.setUserId(jsonObject1.getInteger("user_id"));
        numinfo.setFriendId(jsonObject1.getInteger("friend_id"));
        String friendType =  numinfo.getFriendType();
          if(friendType.equals("2")){
            new_id = numInfoService.SelectNumOne(numinfo);
            numinfo.setFriendType("3");
            numinfo.setUserId(jsonObject1.getInteger("friend_id"));
            numinfo.setNewId(Integer.valueOf(new_id));
            list = historyMessageService.selectNotic(numinfo);
        }
        return  list;
    }


}
