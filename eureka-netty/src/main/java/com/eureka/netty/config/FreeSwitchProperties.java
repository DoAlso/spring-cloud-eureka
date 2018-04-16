package com.eureka.netty.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "freeswitch")
public class FreeSwitchProperties {
    private int port;
    private String host;
    private String password;
    private int timeoutSec;
    public int getPort() {
        if(port == 0){
            port = 8021;
        }
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPassword() {
        if(StringUtils.isBlank(password)){
            password = "ClueCon";
        }
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTimeoutSec() {
        return timeoutSec;
    }

    public void setTimeoutSec(int timeoutSec) {
        this.timeoutSec = timeoutSec;
    }
}
