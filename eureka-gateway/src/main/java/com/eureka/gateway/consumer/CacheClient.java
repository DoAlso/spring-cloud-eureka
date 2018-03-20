package com.eureka.gateway.consumer;

import com.eurake.common.vo.CurrentUser;
import com.eureka.common.constant.Constant;
import com.eureka.common.model.BackEntity;
import com.eureka.common.utils.BackEntityUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CacheClient {
    @Autowired(required = false)
    private RestTemplate restTemplate;

    /**
     * 获取redis保存的当前登录用户
     * @param key
     * @return
     */
    @HystrixCommand(fallbackMethod = "getCurrentUserFallBack")
    public BackEntity getCurrentUser(String key){
        BackEntity backEntity = restTemplate.postForObject("http://storage/getCurrentUser",key,BackEntity.class);
        return backEntity;
    }

    /**
     * 请求失败后的降级处理
     * @param key
     * @return
     */
    public BackEntity getCurrentUserFallBack(String key){
        CurrentUser user = new CurrentUser();
        user.setToken("1_dfg784kjs455kfg9454kjfg");
        user.setAccount("admin");
        return BackEntityUtil.getReponseResult(user, Constant.ResponseMSG.REQUEST_ERROR,Constant.ResponseCode.SYSTEM_BUSY);
    }

    /**
     * 获取当前登录用户
     * 保存在redis中的权限
     * @param key userId
     * @return
     */
    @HystrixCommand(fallbackMethod = "getAuthInfoFallBack")
    public BackEntity getAuthInfo(String key){
        BackEntity backEntity = restTemplate.postForObject("http://storage/getAuthInfo",key,BackEntity.class);
        return backEntity;
    }

    /**
     * 请求失败后的降级处理
     * @param key
     * @return
     */
    public BackEntity getAuthInfoFallBack(String key){
        return BackEntityUtil.getReponseResult(null, Constant.ResponseMSG.REQUEST_ERROR,Constant.ResponseCode.SYSTEM_BUSY);
    }
}
