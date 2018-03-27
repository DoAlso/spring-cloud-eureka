package com.eureka.users.constant;

public class BaseConstant {

    /**
     * base服务的请求响应信息
     */
    public static final class ResponseMSG {
        public static final String PHONE_USED = "this phone already exists";
        public static final String ACCOUNT_NOT_EXITS = "account not exits";
        public static final String PASSWORD_ERROR = "password error";
    }

    /**
     * base服务的请求响应码
     */
    public static final class ResponseCode {
        public static final String PHONE_USED = "30001";//手机号已存在
        public static final String ACCOUNT_NOT_EXITS = "30002";//账号错误
        public static final String PASSWORD_ERROR = "30003";//密码错误
    }
}
