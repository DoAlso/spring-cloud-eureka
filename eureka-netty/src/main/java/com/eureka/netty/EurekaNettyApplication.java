package com.eureka.netty;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;


@SpringCloudApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class EurekaNettyApplication implements CommandLineRunner{

    public static void main(String[] args){
       SpringApplication.run(EurekaNettyApplication.class,args);
    }

    @Override
    public void run(String... args) throws Exception {

    }

}
