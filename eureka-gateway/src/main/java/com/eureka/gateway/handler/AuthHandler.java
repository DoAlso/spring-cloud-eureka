package com.eureka.gateway.handler;

import com.eureka.gateway.consumer.CacheClient;
import com.eureka.gateway.filter.AuthFilter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthHandler implements InitializingBean{
    @Autowired
    private CacheClient cacheClient;
    @Override
    public void afterPropertiesSet() throws Exception {
        AuthFilter.setAuthHandler(this);
    }

    /**
     * 权限验证
     * @param path
     * @param path
     * @return
     */
    public boolean checkAuth(String path){
        return true;
    }
}
