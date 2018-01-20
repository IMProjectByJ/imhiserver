package com.jit.imhi.api;


/*
  文件上传与下载api
 */


import com.alibaba.fastjson.JSONObject;
import com.jit.imhi.annotation.CurrentUser;
import com.jit.imhi.annotation.LoginRequired;
import com.jit.imhi.model.HistoryMessage;
import com.jit.imhi.model.User;
import com.jit.imhi.service.FileOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

@RestController
@RequestMapping("/api/fileOperate")
  /*
        函数描述： 上传文件，信息包括 from  to  filename 上传时需要验证用户身份，文件类型包括，对应offlineMessage表

        by : liuyunxing
      */
public class FileOperateApi {

    FileOperateService fileOperateService;
    @Autowired
    public FileOperateApi(FileOperateService fileOperateService)
    {
        this.fileOperateService = fileOperateService;
    }


    @PostMapping("/upload")
    @LoginRequired // 加上此注释表明需要在head 中添加token 参数
    /*
            @CurrentUser参数可以获得请求用户的个人信息
     */
    public JSONObject upLoadFile(@CurrentUser User user, @RequestParam("file")MultipartFile file,
                                 @RequestParam("toId") Integer toId, @RequestParam("messageTpye") Integer messageType) {

        JSONObject jsonObject = new JSONObject();// jsonObject中包含信息://文件在数据库中的存储位置 // 上传状态
        // 将数据存放在本地
        File file1 = fileOperateService.saveFileToNative(file,"IMHI/upLoadFile/"+user.getUserId()+"/");
        System.out.println("-----absolute path"+file1.getAbsolutePath());
        if (file1 != null) //将数据装入数据库
        {
            HistoryMessage historyMessage
                    = fileOperateService.insertFileToHistoryMessage(file.getOriginalFilename(),
                    user.getUserId(),toId,new Date(),messageType,file1.getAbsolutePath());
            if (historyMessage != null)
            {
                jsonObject.put("message","success");//上传状态
                jsonObject.put("filePath",file1.getAbsolutePath());//文件路径
                jsonObject.put("fileName",file.getOriginalFilename()); //文件原始位置
                jsonObject.put("message_id",historyMessage.getMessageId());
                return  jsonObject;

            }
        }

        jsonObject.put("message","fail");
        return  jsonObject;


    }

    @LoginRequired
    @PostMapping("/download")

    // 验证消息是谁的
    public ResponseEntity<FileSystemResource> downLoadFile(@CurrentUser User user,
                                                           @RequestBody Integer messagId){

        HistoryMessage historyMessage = fileOperateService.authenicationFileOwner( messagId,user.getUserId());
        if (historyMessage != null) {

            JSONObject jsonObject = JSONObject.parseObject(historyMessage.getTextContent());// 转化为json字符串
            if (jsonObject != null) {
                String filePath = jsonObject.getString("filePath");
                System.out.println("获得了文件地址为----" + filePath);
                if (filePath != null) {
                    File file = fileOperateService.downloadFileFromNative(filePath);

                    if (file != null) {
                        System.out.println(jsonObject.getString("fileName"));
                        try {
                            return ResponseEntity
                                   .ok()
                                   .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                                           + URLEncoder.encode(jsonObject.getString("fileName"), "UTF-8")+"\"")
                                   .contentLength(file.length())
                                   .contentType(MediaType.parseMediaType("application/octet-stream"))
                                   .body(new FileSystemResource(file));// 返回文件
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                    }
                }

            }

        }
        return  null;

    }


}