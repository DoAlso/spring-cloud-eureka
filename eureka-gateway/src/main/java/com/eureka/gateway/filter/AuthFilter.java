package com.eureka.gateway.filter;

import com.eurake.common.vo.CurrentUser;
import com.eureka.common.constant.Constant;
import com.eureka.common.utils.FastJsonUtil;
import com.eureka.common.utils.LogUtil;
import com.eureka.gateway.handler.AuthHandler;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class AuthFilter extends ZuulFilter{
    private static Logger logger = LoggerFactory.getLogger(AuthFilter.class);
    private static AuthHandler authHandler;

    public static void setAuthHandler(AuthHandler authHandler) {
        AuthFilter.authHandler = authHandler;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return (boolean)ctx.get("verify");
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        CurrentUser user = (CurrentUser) ctx.get(Constant.CommomKey.CURRENT_USER);
        LogUtil.info(logger,"current authorized user:{}", FastJsonUtil.toJSONString(user));
        //设置全局用户信息,供其他微服务操作
        ctx.addZuulRequestHeader(Constant.CommomKey.CURRENT_USER,FastJsonUtil.toJSONString(user));
        //验证当前的操作权限
        LogUtil.info(logger,"current request uri is:{}",request.getRequestURI());
        if(!authHandler.checkAuth(request.getRequestURI())){
            ctx.setResponseStatusCode(401);
            ctx.setResponseBody(Constant.ResponseMSG.NO_AUTH);
            ctx.setSendZuulResponse(false);
            return null;
        }
        return null;
    }
}
