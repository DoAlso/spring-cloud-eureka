package com.eureka.netty.core;

import com.rabbitmq.client.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


@Component
public class RabbitMQServer {

    private static final String EXCHANGE = "itc-extern";
    private static final String ROUTING_KEY = "itc-extern-interface";
    private String queueName;
    private Connection connection;
    private Channel channel;
    private ConnectionFactory connectionFactory;

    @PostConstruct
    public void binding() {
        connectionFactory = new CachingConnectionFactory("49.4.65.228", 5672);
        connection = connectionFactory.createConnection();
        channel = connection.createChannel(true);
        try {
            queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName,EXCHANGE,ROUTING_KEY);
            channel.basicConsume(queueName,new DefaultConsumer(channel){
                @Override
                public void handleDelivery (String consumerTag, Envelope envelope, AMQP.BasicProperties properties,byte[] body){
                    String string = new String(body);
                    System.out.println(string);
                }
            });
        }catch (Exception e){

        }
    }

    @PreDestroy
    public void close(){

        if(channel != null){
            try {
                channel.close();
            }catch (Exception e){

            }
        }
        if(connection != null){
            try {
                connection.close();
            }catch (Exception e){

            }
        }
    }



}
