package com.eureka.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class EurekaCacheApplication {

    public static void main(String args[]){
        SpringApplication.run(EurekaCacheApplication.class,args);
    }
}
