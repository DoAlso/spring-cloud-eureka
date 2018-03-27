package com.eureka.users.consumer;

import com.eurake.common.vo.CurrentUser;
import com.eureka.common.constant.Constant;
import com.eureka.common.model.BackEntity;
import com.eureka.common.model.BaseParam;
import com.eureka.common.utils.BackEntityUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RedisClientToUser {
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "setCurrentUserFallBack")
    public BackEntity<?> setCurrentUser(BaseParam<CurrentUser> baseParam){
        BackEntity backEntity = restTemplate.postForObject("http://cache/setLoginUser",baseParam,BackEntity.class);
        return backEntity;
    }

    public BackEntity<?> setCurrentUserFallBack(BaseParam<CurrentUser> baseParam){
        return BackEntityUtil.getReponseResult(null, Constant.ResponseMSG.REQUEST_ERROR,Constant.ResponseCode.SYSTEM_BUSY);
    }
}
