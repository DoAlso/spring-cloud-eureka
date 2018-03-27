package com.eureka.gateway.consumer;

import com.eurake.common.vo.CurrentUser;
import com.eureka.common.constant.Constant;
import com.eureka.common.model.BackEntity;
import com.eureka.common.model.BaseParam;
import com.eureka.common.utils.BackEntityUtil;
import com.eureka.common.utils.FastJsonUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CacheClient {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 获取redis保存的当前登录用户
     * @param key
     * @return
     */
    @HystrixCommand(fallbackMethod = "getCurrentUserFallBack")
    public BackEntity getCurrentUser(String key){
        BaseParam<String> baseParam = new BaseParam<>();
        baseParam.setParams(key);
        String result = restTemplate.postForEntity("http://cache/getCurrentUser",baseParam,String.class).getBody();
        BackEntity backEntity = FastJsonUtil.toBean(result,BackEntity.class);
        return backEntity;
    }

    /**
     * 请求失败后的降级处理
     * @param key
     * @return
     */
    public BackEntity getCurrentUserFallBack(String key){
        return BackEntityUtil.getReponseResult(null, Constant.ResponseMSG.REQUEST_ERROR,Constant.ResponseCode.SYSTEM_BUSY);
    }

    /**
     * 获取当前登录用户
     * 保存在redis中的权限
     * @param key userId
     * @return
     */
    @HystrixCommand(fallbackMethod = "getAuthInfoFallBack")
    public BackEntity getAuthInfo(String key){
        BackEntity backEntity = restTemplate.postForObject("http://cache/getAuthInfo",key,BackEntity.class);
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
