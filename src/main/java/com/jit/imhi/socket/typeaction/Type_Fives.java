package com.jit.imhi.socket.typeaction;

import com.alibaba.fastjson.JSONObject;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;

public class Type_Fives {
    IoSession ioSession;

    public Type_Fives(IoSession ioSession) {
        this.ioSession = ioSession;
    }

    public void send() {
        JSONObject json = new JSONObject();
        json.put("message_type", "6");
        WriteFuture writeFuture = ioSession.write(json);
      /*  writeFuture.awaitUninterruptibly();
        if(writeFuture.isWritten()){
            return true;
        } else {
            return  false;
        }
        */
    }
}
