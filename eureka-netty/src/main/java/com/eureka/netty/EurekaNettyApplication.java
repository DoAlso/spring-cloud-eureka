package com.eureka.netty;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
public class EurekaNettyApplication {

    public static void main(String[] args){
        SpringApplication.run(EurekaNettyApplication.class,args);
    }
}
