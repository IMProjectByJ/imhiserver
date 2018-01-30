package com.jit.imhi.jiguang.api;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import com.jit.imhi.jiguang.api.Config.Config;
import com.jit.imhi.jiguang.api.push.PushResult;
import com.jit.imhi.jiguang.api.push.model.Platform;
import com.jit.imhi.jiguang.api.push.model.PushPayload;
import com.jit.imhi.jiguang.api.push.model.audience.Audience;
import com.jit.imhi.jiguang.api.push.model.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JPushService {
    protected static final Logger LOG = LoggerFactory.getLogger(JPushService.class);
  /*  public static void main(String[] args) {
        testSendPush("10024","有一条好友添加消息");
    }*/

    /*
     向标记用户发送信息
     */
    public static void SendPush(String tag , String message) {
        JPushClient jpushClient = new JPushClient(Config.masterSecret, Config.appKey);
        PushPayload payload = buildPushObject_android_tag_alertWithTitle(tag, message);

        try {
            PushResult result = jpushClient.sendPush(payload);
            LOG.info("Got result - " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
        }
    }

    /*
    向有标识的用户发送推送
     */

    public static PushPayload buildPushObject_android_tag_alertWithTitle(String tag, String message) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.tag(tag))
                .setNotification(Notification.android(message, "IMHI", null))
                .build();
    }

    /*
     向所有用户发送推送
     */
    public static PushPayload buildPushObject_all(String message) {
        return PushPayload.alertAll(message);
    }
}
