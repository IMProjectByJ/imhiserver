package com.jit.imhi.service;


import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.jit.imhi.mapper.UserMapper;
import com.jit.imhi.model.SMSCode;
import com.jit.imhi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


/*
 create by liuyunxing
 2018-1-18
 */
@Service
public class SendSMSService {
    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = "LTAIVoiD1f8zBI4X";
    static final String accessKeySecret = "7C18ErHGLsL1MRFQLB4I2GW7tIALRn";
    private static HashMap<String, SMSCode> codeAuthen= new HashMap<String, SMSCode>();
    private UserMapper userMapper;

    @Autowired
    public SendSMSService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    public static SendSmsResponse sendSms(String phoneNum, String RandomNum) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phoneNum);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("IM嗨");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_120410090");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为

        request.setTemplateParam("{ \"code\":\""+RandomNum+"\"}");

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
    }


    public static QuerySendDetailsResponse querySendDetails(String bizId) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber("18020536089");
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);

        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

        return querySendDetailsResponse;
    }



    public JSONObject sendMsm(String phoneNum, String RandomCode, int type) {

        System.out.println("----------------------send-------------------------------" + phoneNum);
        JSONObject jsonObject = new JSONObject();
        SendSmsResponse response = null;
        try {

            // type=1 表示注册， type=2 表示找回密码
            User user = userMapper.selectByPhoneNum(phoneNum);
            if ((user != null && (type == 2) )|| (user == null)&&(type == 1) ){ // 1表示注册账号

/*                response = sendSms("" + phoneNum, RandomCode);
                jsonObject.put("code", response.getCode());
                jsonObject.put("message", response.getMessage());
                jsonObject.put("RequestId",response.getRequestId());
                jsonObject.put("BizId",response.getBizId());*/
                jsonObject.put("code", "OK");
                System.out.println("send code -------  "+RandomCode);
                Thread.sleep(3000L);

            } else  {
                if (type == 2)
                    jsonObject.put("message", "您还没有注册账号");
                else
                    jsonObject.put("message", "您已经注册过了，请不要重复注册");
                return jsonObject;
            }

        } catch ( InterruptedException e ) {
            jsonObject.put("message","错误");
            e.printStackTrace();
        }
        if (jsonObject.getString("code").equals("OK")) {
            codeAuthen.put(phoneNum + "", new SMSCode(RandomCode, new Date()));// 保存
            jsonObject.put("message", "成功");
        }
        return jsonObject;
    }

    public  JSONObject authonCode(String phone, String code) {
        SMSCode smsCode = codeAuthen.get(phone);
        JSONObject jsonObject = new JSONObject();
        Date date = new Date();

        if (smsCode != null) {

            long diff = date.getTime() - smsCode.getDate().getTime();
            if ((diff <= 3000000) && (diff > 0)) {

                if (code.equals(smsCode.getCode())) {

                    jsonObject.put("message", "验证成功");
                    codeAuthen.remove(phone); // 删除

                } else {
                    jsonObject.put("message", "验证码不正确");
                }
            } else {
                jsonObject.put("message", "验证码超时");
            }
        } else {
            jsonObject.put("message", "没有发送验证码");
        }
        return jsonObject;
    }




}
