package com.jit.imhi.api;


import com.alibaba.fastjson.JSONObject;
import com.jit.imhi.model.SMSCode;
import com.jit.imhi.service.SendSMSService;
import com.jit.imhi.utils.GetRandom;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("api/SMS")
public class SendSMSApi {

    SendSMSService sendSMSService;
    private static HashMap<String, SMSCode> codeAuthen= new HashMap<String, SMSCode>();

    public SendSMSApi(SendSMSService sendSMSService) {
        this.sendSMSService = sendSMSService;
    }
    /*
     请求发送验证码
     */
    @GetMapping("sendSMS/{phoneNum}/{type}")
    public JSONObject sendSMS(@PathVariable String phoneNum,@PathVariable Integer type) {

            String randomNum = GetRandom.getRandomNumber(6);
          return sendSMSService.sendMsm(phoneNum, randomNum, type);

    }

    /*
       匹配验证码
     */
    @GetMapping("authenCode/{phone}/{num}")

    public JSONObject authenSMS(@PathVariable String phone, @PathVariable String num) {

        System.out.println(phone);
        return  sendSMSService.authonCode(phone, num);
    }
}
