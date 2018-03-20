package com.eureka.cache.api;

import com.eurake.common.vo.CurrentUser;
import com.eureka.cache.exception.CustomException;
import com.eureka.common.constant.Constant;
import com.eureka.common.model.BackEntity;
import com.eureka.common.model.BaseParam;
import com.eureka.common.utils.BackEntityUtil;
import com.eureka.common.utils.FastJsonUtil;
import com.eureka.common.utils.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;


@RestController
public class UserRedisController {
    private static Logger logger = LoggerFactory.getLogger(UserRedisController.class);

    @Autowired
    private RedisTemplate redisTemplate;


    @PostMapping(value = "/setLoginUser")
    public BackEntity setCurrentUser(@RequestBody BaseParam<CurrentUser> baseParam){
        CurrentUser currentUser = baseParam.getParams();
        LogUtil.info(logger,"setCurrentUser params is:{}", FastJsonUtil.toJSONString(currentUser));
        try{
            redisTemplate.boundValueOps(currentUser.getId()).set(currentUser,72, TimeUnit.HOURS);
            return BackEntityUtil.getReponseResult(null, Constant.ResponseMSG.REQUEST_OK,Constant.ResponseCode.REQUEST_OK);
        }catch (Exception e){
            return BackEntityUtil.getReponseResult(null, e.getMessage(), Constant.ResponseCode.SYSTEM_BUSY);
        }
    }
}
