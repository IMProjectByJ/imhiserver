package com.jit.imhi.api;


/*
  头像上传与下载api
 */

import com.alibaba.fastjson.JSONObject;
import com.jit.imhi.annotation.CurrentUser;
import com.jit.imhi.annotation.LoginRequired;
import com.jit.imhi.model.User;
import com.jit.imhi.service.FileOperateService;
import com.jit.imhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/api/imageOperate")
public class ImageOperateApi {
    UserService userService;
    FileOperateService fileOperateService;
    @Autowired
    public ImageOperateApi(FileOperateService fileOperateService, UserService userService) {
        this.userService = userService;
        this.fileOperateService = fileOperateService;}

    @PostMapping("/imageUpload")
    @LoginRequired
    public JSONObject upLoadImage(@CurrentUser User user, @RequestParam("file")MultipartFile file,
                                  @RequestParam("toId") Integer toId, @RequestParam("messageType") Integer messageType){
        System.out.println("ImageOperateApi测试");

        JSONObject jsonObject = new JSONObject();
        System.out.println(user.toString()); //test-----
        File file1 = fileOperateService.saveFileToNative(file, "IMHI/upLoadImage/"+user.getPhoneNum()+"/");
        System.out.println("-----absolute path" + file1.getAbsolutePath());

        if (file1 != null) {
            user.setHeadUrl(file1.getAbsolutePath());
            System.out.println(user.getHeadUrl());

            int num = userService.updateInfoByUserId(user);
            User user1 = userService.findUserByUserId(user.getUserId());
            System.out.println(user1.getHeadUrl());
            System.out.println("NUM:" + num);
            if (num > 0) {
                jsonObject.put("success", "yes");
            } else {
                jsonObject.put("err", "no");
            }
        } else {
            jsonObject.put("err", "no");
        }

        return jsonObject;
    }

    @LoginRequired
    @PostMapping("/imageDownload")
    public ResponseEntity<FileSystemResource> downLoadFile(@CurrentUser User user,
                                                           @RequestBody Integer messagId){

        String imagePath = user.getHeadUrl();

        System.out.println("获得了文件地址为----" + imagePath);
        if (imagePath != null) {
            File file = fileOperateService.downloadFileFromNative(imagePath);

            if (file != null) {

                    return ResponseEntity
                            .ok()
                            .contentLength(file.length())
                            .contentType(MediaType.parseMediaType("application/octet-stream"))
                            .body(new FileSystemResource(file));// 返回文件

            }
        }
        return  null;

    }
}
