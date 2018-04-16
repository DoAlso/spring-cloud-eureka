package com.eureka.netty.listener;

import com.eureka.common.utils.FastJsonUtil;
import com.eureka.common.utils.LogUtil;
import com.eureka.netty.constant.FreeSwitchEventType;
import org.freeswitch.esl.client.IEslEventListener;
import org.freeswitch.esl.client.transport.event.EslEvent;
import org.freeswitch.esl.client.transport.message.EslHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;

@Component
public class EslCoreEventListener implements IEslEventListener{

    private static Logger logger = LoggerFactory.getLogger(EslCoreEventListener.class);
    @Override
    public void eventReceived(EslEvent eslEvent) {
        String eventName = eslEvent.getEventName();
        LogUtil.info(logger,"接收的事件:{}",eventName);
        FreeSwitchEventType eventType = FreeSwitchEventType.valueOf(eventName);
        Map<String,String> eventHeaders = eslEvent.getEventHeaders();
        Iterator<Map.Entry<String, String>> entries = eventHeaders.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            LogUtil.info(logger,"Key:{},value:{}" , entry.getKey() ,entry.getValue());
        }
         Map<EslHeaders.Name,String> headerMessages = eslEvent.getMessageHeaders();
         Iterator<Map.Entry<EslHeaders.Name, String>> entries1 = headerMessages.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<EslHeaders.Name, String> entry = entries1.next();
            LogUtil.info(logger,"Key:{},value:{}" , FastJsonUtil.toJSONString(entry.getKey()),entry.getValue());
        }
        switch(eventType){
            case CUSTOM:
                LogUtil.info(logger,"CUSTOM");
                break;
            case CHANNEL_CREATE:
                LogUtil.info(logger,"CHANNEL_CREATE");
                break;
            case HEARTBEAT:
                LogUtil.info(logger,"HEARTBEAT");
                break;
            case RE_SCHEDULE:
                LogUtil.info(logger,"RE_SCHEDULE");
                break;
            case CHANNEL_ORIGINATE:
                LogUtil.info(logger,"CHANNEL_ORIGINATE");
                break;
            case CHANNEL_STATE:
                LogUtil.info(logger,"CHANNEL_STATE");
                break;
            case CHANNEL_CALLSTATE:
                LogUtil.info(logger,"CHANNEL_CALLSTATE");
                break;
            case CHANNEL_BRIDGE:
                LogUtil.info(logger,"CHANNEL_BRIDGE");
                break;
            case CHANNEL_UNBRIDGE:
                LogUtil.info(logger,"CHANNEL_UNBRIDGE");
                break;
            case CHANNEL_PROGRESS_MEDIA:
                LogUtil.info(logger,"CHANNEL_PROGRESS_MEDIA");
                break;
            case CALL_UPDATE:
                LogUtil.info(logger,"CALL_UPDATE");
                break;
            case CHANNEL_PROGRESS:
                LogUtil.info(logger,"CHANNEL_PROGRESS");
                break;
            case CHANNEL_OUTGOING:
                LogUtil.info(logger,"CHANNEL_OUTGOING");
                break;
            case CHANNEL_HANGUP:
                LogUtil.info(logger,"CHANNEL_HANGUP");
                break;
            case CHANNEL_HANGUP_COMPLETE:
                LogUtil.info(logger,"CHANNEL_HANGUP_COMPLETE");
                break;
            case CHANNEL_DESTROY:
                LogUtil.info(logger,"CHANNEL_DESTROY");
                break;
            case CHANNEL_EXECUTE:
                LogUtil.info(logger,"CHANNEL_EXECUTE");
                break;
            case CHANNEL_EXECUTE_COMPLETE:
                LogUtil.info(logger,"CHANNEL_EXECUTE_COMPLETE");
                break;
            case PRESENCE_IN:
                LogUtil.info(logger,"PRESENCE_IN");
                break;
            case CHANNEL_ANSWER:
                LogUtil.info(logger,"CHANNEL_ANSWER");
                break;
            case MESSAGE_QUERY:
                LogUtil.info(logger,"MESSAGE_QUERY");
                break;
            case MESSAGE_WAITING:
                LogUtil.info(logger,"MESSAGE_WAITING");
                break;
            case API:
                LogUtil.info(logger,"API");
                break;
            case CODEC:
                LogUtil.info(logger,"CODEC");
                break;
            case RELOADXML:
                LogUtil.info(logger,"RELOADXML");
                break;
            case MODULE_LOAD:
                LogUtil.info(logger,"MODULE_LOAD");
                break;
            case MODULE_UNLOAD:
                LogUtil.info(logger,"MODULE_UNLOAD");
                break;
            case PLAYBACK_START:
                LogUtil.info(logger,"PLAYBACK_START");
                break;
            case PLAYBACK_STOP:
                LogUtil.info(logger,"PLAYBACK_STOP");
                break;
            case RECORD_START:
                LogUtil.info(logger,"RECORD_START");
                break;
            case RECORD_STOP:
                LogUtil.info(logger,"RECORD_STOP");
                break;
             default:
                 break;
        }
    }

    @Override
    public void backgroundJobResultReceived(EslEvent eslEvent) {

    }
}
