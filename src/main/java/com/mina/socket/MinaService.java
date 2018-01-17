package com.mina.socket;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class MinaService {

    private static final int PORT = 8888;
    private static final int ideltimeout = 10;
    private static final int heartbeatrate = 10;
    private static int HEARTBEATREQUEST = 0x11;
    private static int HEARTBEATRESPONSE = 0x12;
    static int num = 0;

    public static void start() {

        IoAcceptor acceptor = new NioSocketAcceptor();


        acceptor.getFilterChain().addLast(
                "codec",
                new ProtocolCodecFilter(new TextLineCodecFactory(Charset
                        .forName("UTF-8"), LineDelimiter.WINDOWS.getValue(),
                        LineDelimiter.WINDOWS.getValue())));
/*
        KeepAliveMessageFactory heartBeatFactory = new KeepAliveMessageFactoryImpl();
        KeepAliveFilter heartBeat = new KeepAliveFilter(heartBeatFactory,
                IdleStatus.BOTH_IDLE);
        heartBeat.setForwardEvent(true);
        heartBeat.setRequestInterval(heartbeatrate);
        heartBeat.setRequestTimeout(heartbeatrate);
        acceptor.getFilterChain().addLast("heartbeat", heartBeat);
*/
        acceptor.setHandler(new MyIoHandler());

        try {

            acceptor.bind(new InetSocketAddress(PORT));
            System.out.println("启动成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class KeepAliveMessageFactoryImpl implements
            KeepAliveMessageFactory {

        public Object getRequest(IoSession arg0) {
            // System.out.println(arg0.toString());
            // System.out.println("请求预设信息"+HEARTBEATREQUEST );
            // return HEARTBEATREQUEST ;
            return null;
        }

        public Object getResponse(IoSession arg0, Object arg1) {
            // System.out.println(arg0.toString());
            System.out.println("响应预设信息" + HEARTBEATRESPONSE);
            return HEARTBEATRESPONSE;
            // return null;
        }

        public boolean isRequest(IoSession arg0, Object arg1) {
            // System.out.println(arg0.toString());

            if (arg1.toString().equals(String.valueOf(HEARTBEATREQUEST))) {

                System.out.println("是请求包");

                return true;
            }
            // System.out.println("不是请求包");
            return false;

        }

        public boolean isResponse(IoSession arg0, Object arg1) {

            //
             // if(arg1.toString().equals(String.valueOf(HEARTBEATRESPONSE))) {
            //System.out.println("是响应包"); return true; }

            // System.out.println("不是响应包");
            return false;

        }
    }

    private static class KeepAliveRequestTimeoutHandlerImpl implements
            KeepAliveRequestTimeoutHandler {

        public void keepAliveRequestTimedOut(KeepAliveFilter filter,
                                             IoSession session) {
            System.out.println("心跳超时！");
            session.close(true);
            session = null;
            System.out.println("超时关闭远程连接，另一端应该已经不能再使用发送信息功能");

        }

    }
}
