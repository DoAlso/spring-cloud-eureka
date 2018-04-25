package com.eureka.netty.api;

import com.eureka.common.constant.Constant;
import com.eureka.common.model.BackEntity;
import com.eureka.common.utils.BackEntityUtil;
//import com.eureka.netty.core.FreeSwitchClient;
import com.eureka.netty.handler.Sender;
import org.freeswitch.esl.client.transport.message.EslMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FreeSwitchController {

    @Autowired
    private Sender sender;
//    @Autowired
//    private FreeSwitchClient switchClient;
//
//    @GetMapping("/command")
//    public BackEntity command(){
//        EslMessage str = switchClient.getClient().sendSyncApiCommand("sofia status profile internal reg","");
//        return BackEntityUtil.getReponseResult(str, Constant.ResponseMSG.REQUEST_OK,Constant.ResponseCode.REQUEST_OK);
//    }
//
//
//    @GetMapping("/call")
//    public BackEntity call(){
//        String str = switchClient.getClient().sendAsyncApiCommand("originate user/1001","");
//        return BackEntityUtil.getReponseResult(str,Constant.ResponseMSG.REQUEST_OK,Constant.ResponseCode.REQUEST_OK);
//    }

    @GetMapping("/send")
    public void send(){
        sender.send();
    }

}
