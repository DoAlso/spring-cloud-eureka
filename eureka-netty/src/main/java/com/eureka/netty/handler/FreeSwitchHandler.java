//package com.eureka.netty.handler;
//
//import com.eureka.netty.constant.FreeSwitchEventType;
//import com.eureka.netty.core.FreeSwitchClient;
//import org.freeswitch.esl.client.IEslEventListener;
//import org.freeswitch.esl.client.inbound.Client;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//
//@Component
//public class FreeSwitchHandler {
//
//    @Autowired
//    private FreeSwitchClient freeSwitchClient;
//    private Client client;
//    private boolean isConnect;
//
//
//    @PostConstruct
//    public void initClient(){
//        client = freeSwitchClient.getClient();
//        if(client != null){
//            isConnect = true;
//        }
//    }
//
//    /**
//     * 添加监听器
//     * @param listener
//     */
//    public void addEventListener(String format,String events,IEslEventListener listener){
//        client.addEventListener(listener);
//        client.setEventSubscriptions(format,events);
//        for(FreeSwitchEventType eventType:FreeSwitchEventType.values()){
//            client.addEventFilter("Event-Name",eventType.name());
//        }
//    }
//
//
//    public  boolean canSend() {
//        return client.canSend();
//    }
//
//    public boolean isConnect(){
//        return this.isConnect;
//    }
//
//
//    /**
//     * 手动触发关闭客户端
//     */
//    public void close(){
//        if(client != null){
//            client.close();
//            isConnect = false;
//        }
//    }
//}
