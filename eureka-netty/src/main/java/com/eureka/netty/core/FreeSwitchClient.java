package com.eureka.netty.core;

import com.eureka.common.utils.LogUtil;
import com.eureka.netty.config.FreeSwitchProperties;
import com.eureka.netty.listener.EslCoreEventListener;
import org.freeswitch.esl.client.IEslEventListener;
import org.freeswitch.esl.client.inbound.Client;
import org.freeswitch.esl.client.inbound.InboundConnectionFailure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author 胡亚曦
 * 该类通过FreeSwitch的
 * Socket_Event事件控制
 * FreeSwitch
 */
@Component
public class FreeSwitchClient {
    private static Logger logger = LoggerFactory.getLogger(FreeSwitchClient.class);
    @Autowired
    private FreeSwitchProperties properties;
    @Autowired
    private EslCoreEventListener coreEventListener;
    private Client client = new Client();

    @PostConstruct
    public void start(){
        /**
         * 由于freeswitch连接操作会阻塞主线程，
         * 所以此处开启一个线程用于连接FreeSwitch
         * 服务，防止主线程的阻塞
         */
        try {
            LogUtil.info(logger,"开始执行FreeSwitch服务端连接操作......");
            client.connect(properties.getHost(),properties.getPort(),properties.getPassword(),properties.getTimeoutSec());
            client.addEventListener(coreEventListener);
            client.setEventSubscriptions("plain", "all");
            LogUtil.info(logger,"FreeSwitch服务端连接成功......");
        }catch (InboundConnectionFailure e){
            LogUtil.error(logger,"FreeSwitch服务连接失败,错误信息是:{}",e.getMessage());
        }
    }

    public Client getClient() {
        return client;
    }

    @PreDestroy
    public void close(){
        if(client != null){
            client.close();
        }
        LogUtil.info(logger,"FreSwitch连接关闭成功...");
    }
}
