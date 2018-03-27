package com.eureka.cache.api;

import com.eurake.common.vo.CurrentUser;
import com.eureka.common.constant.Constant;
import com.eureka.common.model.BackEntity;
import com.eureka.common.model.BaseParam;
import com.eureka.common.utils.BackEntityUtil;
import com.eureka.common.utils.FastJsonUtil;
import com.eureka.common.utils.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;


@RestController
public class UserRedisController {
    private static Logger logger = LoggerFactory.getLogger(UserRedisController.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @PostMapping(value = "/setLoginUser")
    public BackEntity setCurrentUser(@RequestBody BaseParam<CurrentUser> baseParam) throws Exception{
        CurrentUser currentUser = baseParam.getParams();
        LogUtil.info(logger,"setCurrentUser params is:{}", FastJsonUtil.toJSONString(currentUser));
        stringRedisTemplate.boundValueOps(String.valueOf(currentUser.getId())).set(FastJsonUtil.toJSONString(currentUser),72, TimeUnit.HOURS);
        return BackEntityUtil.getReponseResult(null, Constant.ResponseMSG.REQUEST_OK,Constant.ResponseCode.REQUEST_OK);
    }


    @PostMapping(value = "/getCurrentUser")
    public BackEntity getCurrentUser(@RequestBody BaseParam<String> baseParam) throws Exception {
        LogUtil.info(logger,"getCurrentUser params is:{}", FastJsonUtil.toJSONString(baseParam.getParams()));
        CurrentUser result = FastJsonUtil.toBean(stringRedisTemplate.boundValueOps(baseParam.getParams()).get(),CurrentUser.class);
        return BackEntityUtil.getReponseResult(result,Constant.ResponseMSG.REQUEST_OK,Constant.ResponseCode.REQUEST_OK);
    }


}
