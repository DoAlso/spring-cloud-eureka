package com.eureka.users.api;

import com.eurake.common.params.LoginParam;
import com.eureka.common.constant.Constant;
import com.eureka.common.model.BackEntity;
import com.eureka.common.model.BaseParam;
import com.eureka.common.utils.BackEntityUtil;
import com.eureka.common.utils.FastJsonUtil;
import com.eureka.common.utils.LogUtil;
import com.eureka.users.provider.UserFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserFacade userFacade;

    @PostMapping("/register")
    public BackEntity register(@RequestBody BaseParam<String> baseParam) {
        LogUtil.info(logger,"request params is:{}", FastJsonUtil.toJSONString(baseParam));
        try{
            return userFacade.register(baseParam.getParams());
        }catch (Exception e){
            LogUtil.error(logger,"register error:{}",e.getMessage());
            return BackEntityUtil.getReponseResult(null, e.getMessage(), Constant.ResponseCode.SYSTEM_BUSY);
        }
    }

    @PostMapping("/login")
    public BackEntity login(@RequestBody BaseParam<LoginParam> baseParam){
        LogUtil.info(logger,"request params is:{}", FastJsonUtil.toJSONString(baseParam));
        try{
            return userFacade.login(baseParam.getParams());
        }catch(Exception e){
            LogUtil.error(logger,"register error:{}",e.getMessage());
            return BackEntityUtil.getReponseResult(null, e.getMessage(), Constant.ResponseCode.SYSTEM_BUSY);
        }
    }
}
