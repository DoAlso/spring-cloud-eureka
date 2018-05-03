package com.eureka.users;

import com.eurake.common.params.LoginParam;
import com.eureka.common.model.BaseParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EurekaUsersApplicationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;


    @Test
    public void contextLoads() {
    }

    @Test
    public void testLogin(){
        BaseParam<LoginParam> baseParam = new BaseParam<>();
        LoginParam loginParam = new LoginParam();
        loginParam.setAccount("admin");
        loginParam.setPassword("123456");
        baseParam.setParams(loginParam);
        String result = this.testRestTemplate.postForEntity("/login",baseParam,String.class).getBody();
        System.out.println(result);
    }

}
