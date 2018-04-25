package com.eureka.netty;

import com.eureka.netty.handler.Sender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EurekaNettyApplicationTests {

    @Autowired
    private Sender sender;

    @Test
    public void contextLoads() {

    }

    @Test
    public void testAmqp(){
        sender.send();
    }

}
