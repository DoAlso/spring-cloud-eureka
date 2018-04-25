package com.eureka.netty.handler;

import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @RabbitListener(queues = "ajax")
    @RabbitHandler
    public void process(String value) {
        System.out.println("Receiver : " + value);
    }
}
