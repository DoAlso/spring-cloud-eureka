package com.eureka.common.utils;

import com.eureka.common.model.BackEntity;

/**
 * 该类用于接收
 * 请求完成之后的结果
 */
public class BackEntityUtil {

    /**
     * 获取请求返回的数据
     * @param object
     * @param resMsg
     * @param resCode
     * @return
     */
    public static BackEntity getReponseResult(Object object, String resMsg, String resCode){
        BackEntity backEntity = new BackEntity();
        backEntity.setResCode(resCode);
        backEntity.setResData(object);
        backEntity.setResMsg(resMsg);
        return backEntity;
    }
}
