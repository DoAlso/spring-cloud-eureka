package com.eureka.netty.handler;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "hello")
public class Receiver {


    @RabbitHandler
    public void process(String value) {
        System.out.println("Receiver : " + value);
    }
}
