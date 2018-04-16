package com.eureka.netty.constant;

public enum FreeSwitchEventType {

    //跟呼叫相关的通道事件
    CHANNEL_CREATE,//通道创建事件
    CHANNEL_ANSWER,//通道应答事件
    CHANNEL_BRIDGE,//通道桥接事件
    CHANNEL_UNBRIDGE,//通道非桥接事件
    CHANNEL_HANGUP,//通道挂断事件
    CHANNEL_HANGUP_COMPLETE,//通道挂断完成事件
    CUSTOM,//
    CHANNEL_DESTROY,//通道销毁事件

    CHANNEL_ORIGINATE,//通道产生事件
    CHANNEL_OUTGOING,//通道输出事件
    CHANNEL_STATE,//通道状态事件
    CHANNEL_CALLSTATE,
    CALL_UPDATE,
    CHANNEL_PROGRESS,
    CHANNEL_EXECUTE,
    CHANNEL_EXECUTE_COMPLETE,
    CHANNEL_PROGRESS_MEDIA,
    PLAYBACK_START,
    PLAYBACK_STOP,
    HEARTBEAT,//心跳包
    SESSION_HEARTBEAT,//计费心跳包心跳包

    RE_SCHEDULE,//重置计划事件

    /****** MESSAGE事件 *********/
    MESSAGE_QUERY,//消息查询事件
    MESSAGE_WAITING,//消息警告事件

    RELOADXML,
    MODULE_LOAD,
    MODULE_UNLOAD,

    RECORD_START,
    RECORD_STOP,
    CODEC,
    API,//api请求事件

    PRESENCE_IN,//
}
