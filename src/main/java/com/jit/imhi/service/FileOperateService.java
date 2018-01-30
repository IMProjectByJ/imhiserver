package com.jit.imhi.service;


import com.alibaba.fastjson.JSONObject;
import com.jit.imhi.mapper.HistoryMessageMapper;
import com.jit.imhi.mapper.UserMapper;
import com.jit.imhi.model.HistoryMessage;
import com.jit.imhi.model.User;
import org.bouncycastle.asn1.cms.TimeStampedData;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.awt.SunGraphicsCallback;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;

@Service

public class FileOperateService {

    HistoryMessageMapper historyMessageMapper;
    String ROOTPath = null;
    @Autowired
    FileOperateService(HistoryMessageMapper historyMessageMapper){
        this.historyMessageMapper = historyMessageMapper;
        //ROOTPath = getClass().getResource("/").getFile().toString();// 获得本地地址
        //ROOTPath = "/home/star/testFile/IMHI";
        ROOTPath = "C:/home/star/testFile/IMHI";

    }
    // 文件消息类型为3
    public  int  insertFileToHistoryMessage(HistoryMessage historyMessage){
        return historyMessageMapper.insert(historyMessage);
    }

    public  HistoryMessage insertFileToHistoryMessage(String fileName, Integer fromId, Integer toId, Date date, Integer MessageTpye, String filePath){

        // 函数功能：将消息记录保存到数据库
        HistoryMessage historyMessage = new HistoryMessage();
        historyMessage.setDate(date);
        historyMessage.setMessageType(MessageTpye); // 设置是群聊消息还是私聊消息){ // 记录到数据库
        historyMessage.setTextType(3); // 文件消息类型为3
        historyMessage.setTextContent(null); // 设置文件路径
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("filePath",filePath);
        jsonObject.put("fileName",fileName);
        historyMessage.setTextContent(jsonObject.toJSONString()); // 保存文件名和文件路径
        historyMessage.setUserFromId(fromId);
        historyMessage.setToId(toId);
        int id = insertFileToHistoryMessage(historyMessage);
        historyMessage.setUserFromId(id);
        return  historyMessage; // 返回数据库中的信息
    }

    public File saveFileToNative(MultipartFile file , String Path){

        // 将文件保存到本地
        System.out.println(getClass().getResource("/").getFile().toString());
        // ---------- for test above
        String fileName = file.getOriginalFilename();// 获得上传文件的名称
        String  suffix= fileName.substring(fileName.lastIndexOf("."));
        String nameWithoutSuffix = fileName.replaceAll(suffix,"");
        System.out.println("上传文件名称为"+fileName);
        File  saveFile = new File(ROOTPath+Path+nameWithoutSuffix+new Date().getTime()+suffix);//将文件加上时间戳

        if (!saveFile.getParentFile().exists())
                saveFile.getParentFile().mkdirs(); // 存储到指定路径
        try {
            file.transferTo(saveFile);
            return saveFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    /*
      函数功能：从本地下载文件
     */

    public File downloadFileFromNative(String filePath){

        File file = new File(filePath);
        return  file;
    }

    public HistoryMessage authenicationFileOwner(Integer MessageId, Integer userId){

        HistoryMessage historyMessage = historyMessageMapper.selectByPrimaryKey(MessageId);// 查找文件
        if (historyMessage == null)
            return null;
        if (historyMessage.getUserFromId().equals(userId) || historyMessage.getToId() == userId) {
            // 查找这条消息是否属于此用户
                return  historyMessage;

        }
        return  null;// 并不是
    }



    //public  int  insertFileToUnlineMessage()

}
