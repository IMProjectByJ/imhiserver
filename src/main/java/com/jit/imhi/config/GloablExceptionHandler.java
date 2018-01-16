package com.jit.imhi.config;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/*
 create by liuyunxing
  处理异常
 */
@ControllerAdvice
public class GloablExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GloablExceptionHandler.class);
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e)
    {
        logger.error(ExceptionUtils.getFullStackTrace(e));
        String msg  = e.getMessage();
        if (msg == null || msg.equals("")) {

            msg = "服务器出错";
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message",msg);
        return jsonObject;

    }

}
