package com.eureka.netty.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {

    @Bean
    public Queue queue() {
        return new Queue("hello");
    }

    @Bean
    public DirectExchange exchange(){
        return new DirectExchange("spring-boot");
    }

    @Bean
    Binding binding(Queue queue,DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("spring-boot-interface");
    }

}
