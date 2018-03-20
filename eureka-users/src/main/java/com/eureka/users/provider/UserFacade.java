package com.eureka.users.provider;

import com.eurake.common.params.LoginParam;
import com.eureka.common.model.BackEntity;

public interface UserFacade {

    /**
     * 用户登录支持
     * 手机号+手机验证码登录
     * 账户/邮箱+普通验证码登录
     * @param loginParam
     * @return
     * @throws Exception
     */
    BackEntity login(LoginParam loginParam) throws Exception;

    /**
     * 该注册只支持
     * 手机号码注册
     * 手机号码验证
     * 通过之后会随机
     * 生成一个账户和密码
     * 并通过手机号码发送到
     * 该手机号上
     * @param phone
     * @return
     * @throws Exception
     */
    BackEntity register(String phone) throws Exception;
}
