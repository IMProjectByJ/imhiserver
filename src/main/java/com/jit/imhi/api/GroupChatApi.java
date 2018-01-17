package com.jit.imhi.api;

import com.alibaba.fastjson.JSONObject;
import com.jit.imhi.annotation.CurrentUser;
import com.jit.imhi.annotation.LoginRequired;
import com.jit.imhi.model.Groupchat;
import com.jit.imhi.model.User;
import com.jit.imhi.service.GroupOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/*
 create by liuyunxing
 2018-1-17
 */
@RestController
@RequestMapping("api/groupchat")
public class GroupChatApi {

    GroupOperateService groupOperateService ;
    @Autowired
    public  GroupChatApi(GroupOperateService groupOperateService) {

        this.groupOperateService = groupOperateService;

    }

    @GetMapping("findGroup/id/{groupId}")

    public JSONObject handleFindGroup(@PathVariable Integer groupId) {

        JSONObject jsonObject = new JSONObject();

        Groupchat groupchat = groupOperateService.findGroupByGroupId(groupId);
        if (groupchat  == null) {
            jsonObject.put("message","没有结果");
        }
        else {
            jsonObject.put("message","查询成功");
            jsonObject.put("result",groupchat);
        }
        return jsonObject;
    }

    @GetMapping("findGroup/name/{groupName}")

    public JSONObject handleFindGropByName(@PathVariable String groupName ) {

        JSONObject jsonObject = new JSONObject();
        List<Groupchat> ls = groupOperateService.findGroupByGroupName(groupName);
        if (ls == null) {
            jsonObject.put("message","没有结果");
        } else
        {
            jsonObject.put("message","查询成功");
            jsonObject.put("result",ls);
        }
        return  jsonObject;
    }

    @PostMapping("createGroup")
    @LoginRequired

    public JSONObject handleCreateGroup(@CurrentUser User user, @RequestParam("groupName") String groupName,
                                        @RequestParam("head")MultipartFile file) {

        Groupchat groupchat =  groupOperateService.createGroup(user.getUserId(), groupName, file);
        JSONObject jsonObject = new JSONObject();
        if (groupchat == null) {
            jsonObject.put("message", "创建失败");
        }else {
            jsonObject.put("message","创建成功");
            jsonObject.put("result",jsonObject);
        }
        return  jsonObject;
    }

    @GetMapping("updateGroupName/{groupId}/{groupName}")
    @LoginRequired

    public JSONObject handleUpdate(@CurrentUser User user,
                                   @PathVariable String groupName,
                                   @PathVariable Integer groupId,
                                   @RequestParam("file") MultipartFile file) {
        JSONObject jsonObject = new JSONObject();
        if (groupOperateService.authenUser(user.getUserId(),groupId)) {
            // 验证成功
            Groupchat groupchat = new Groupchat();
            groupchat.setGroupName(groupName);
            if (file!= null && file.getSize() != 0)
            groupchat = groupOperateService.updateGroupInfomatin(groupchat, file);
            if (groupchat == null) {
                jsonObject.put("message","修改资料失败");
            }else
            {
                jsonObject.put("message","修改资料成功");
                jsonObject.put("result",groupchat);
            }
        } else
        {
            jsonObject.put("message","没有操作权限");
        }
        return  jsonObject;
    }

    @GetMapping("loadheadpic/{url}")
    @LoginRequired

    public ResponseEntity<FileSystemResource> handleHeadPic(@CurrentUser User user,
                                                            @PathVariable String picUrl) {

        File head;

        if (picUrl!= null ) {
            head = new File(picUrl);
            if (head.exists()) {
                return ResponseEntity
                        .ok()
                        .contentLength(head.length())
                        .contentType(MediaType.parseMediaType("application/octet-stream"))
                        .body(new FileSystemResource(head));// 返回文件
            }

        }
        return null;

    }
}
