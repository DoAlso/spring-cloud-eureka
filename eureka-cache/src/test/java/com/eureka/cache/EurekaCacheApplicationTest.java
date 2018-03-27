package com.eureka.cache;

import com.eureka.common.model.BackEntity;
import com.eureka.common.model.BaseParam;
import com.eureka.common.utils.FastJsonUtil;
import com.eureka.common.utils.LogUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EurekaCacheApplicationTest {
    private static Logger logger = LoggerFactory.getLogger(EurekaCacheApplicationTest.class);
    @Autowired
    private TestRestTemplate restTemplate;
    @Test
    public void contextLoads() {
    }

    @Test
    public void testGetCurrentUser(){
        BaseParam baseParam = new BaseParam<>();
        baseParam.setParams("2");
        BackEntity backEntity = this.restTemplate.postForObject("/getCurrentUser",baseParam,BackEntity.class);
        LogUtil.info(logger,"test result is:{}",FastJsonUtil.toJSONString(backEntity));
    }

}
