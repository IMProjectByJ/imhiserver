package com.jit.imhi.service;


import com.jit.imhi.mapper.GroupchatMapper;
import com.jit.imhi.model.Groupchat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;


/*
 create by liuyunxing
 2018-1-18

 */
@Service
public class GroupOperateService {

    GroupchatMapper groupchatMapper;
    FileOperateService fileOperateService;

    final String DEFAULT_HEAD_URL;
    @Autowired

    public  GroupOperateService(GroupchatMapper groupChatMapper, FileOperateService fileOperateService) {

        this.groupchatMapper = groupChatMapper;
        this.fileOperateService = fileOperateService;
        DEFAULT_HEAD_URL = fileOperateService.ROOTPath + "GroupChat/defaultResource/head/grouphead.png";
    }

    /*
     根据id查找返回群信息
     */
    public Groupchat findGroupByGroupId(Integer groupId)
    {
        return  groupchatMapper.selectByPrimaryKey(groupId);
    }

    public List<Groupchat> findGroupByGroupName(String groupName)
    {
        return groupchatMapper.selectByGroupName(groupName);
    }

    /* +群头像功能
      设置触发器，添加群自动将群主信息保存到群表中
     */
    public Groupchat createGroup(Integer userId, String groupName , MultipartFile head) {

        Groupchat groupchat = new Groupchat();
        groupchat.setGroupName(groupName);
        groupchat.setUserId(userId);
        groupchat.setCreateDate( new Date());
        // 将头像存储到本地
        return  createGroup(groupchat, head);
    }

    public Groupchat createGroup(Groupchat groupchat, MultipartFile head) {

        groupchatMapper.insert(groupchat);
        if (head != null) {
            File file = fileOperateService.saveFileToNative(head, "group" + groupchat.getGroupId() + "/img/head");//存储路径
            groupchat.setHeadUrl(file.getAbsolutePath());
        }
        else {
            groupchat.setHeadUrl(DEFAULT_HEAD_URL);
        }
        groupchatMapper.updateByPrimaryKey(groupchat);
        return  groupchat;
    }

    public Groupchat updateGroupInfomatin(Groupchat groupchat, MultipartFile head) {

        File file = fileOperateService.saveFileToNative(head, "group"+groupchat.getGroupId()+"/img/head");//存储路径
        Groupchat groupchat1= findGroupByGroupId(groupchat.getGroupId());
        if (head != null)
            groupchat1.setHeadUrl(file.getAbsolutePath());
        if (groupchat.getGroupName() != null) {
            groupchat1.setGroupName(groupchat.getGroupName());
        }
        groupchatMapper.updateByPrimaryKey(groupchat1);
        return  groupchat;
    }
    /*
     删除群组
     */
    public boolean deleteGroup(Integer groupId) {

        if (groupchatMapper.deleteByPrimaryKey(groupId) == 1) {
            return true;
        }
        return false;
    }

    public boolean authenUser(Integer userId, Integer groupId) {

       Groupchat groupchat =  groupchatMapper.selectByPrimaryKey(groupId);

       if (groupchat == null) {
           return  false;
       } else
       {
           if (groupchat.getUserId() == userId) {
               return true;
           }
       }
       return false;
    }




}