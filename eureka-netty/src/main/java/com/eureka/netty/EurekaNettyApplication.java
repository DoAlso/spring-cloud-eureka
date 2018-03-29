package com.eureka.netty;

import com.eureka.netty.core.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class EurekaNettyApplication implements CommandLineRunner{

    @Autowired
    private NettyServer nettyServer;

    public static void main(String[] args){
       SpringApplication.run(EurekaNettyApplication.class,args);
    }

    @Override
    public void run(String... args) {
        nettyServer.start();
    }
}
