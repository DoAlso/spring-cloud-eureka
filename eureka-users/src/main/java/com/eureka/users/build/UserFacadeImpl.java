package com.eureka.users.build;

import com.eurake.common.params.LoginParam;
import com.eurake.common.vo.CurrentUser;
import com.eureka.common.constant.Constant;
import com.eureka.common.dao.UserMapper;
import com.eureka.common.model.BackEntity;
import com.eureka.common.model.BaseParam;
import com.eureka.common.model.base.User;
import com.eureka.common.model.base.UserExample;
import com.eureka.common.utils.BackEntityUtil;
import com.eureka.common.utils.CommonUtil;
import com.eureka.common.utils.DateUtil;
import com.eureka.common.utils.MD5HashUtil;
import com.eureka.users.constant.BaseConstant;
import com.eureka.users.consumer.RedisClientToUser;
import com.eureka.users.provider.UserFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class UserFacadeImpl implements UserFacade {
    private static final Logger logger = LoggerFactory.getLogger(UserFacadeImpl.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisClientToUser clientToUser;
    @Override
    public BackEntity login(LoginParam loginParam) throws Exception{
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andAccountEqualTo(loginParam.getAccount());
        User user = userMapper.selectByExample(example).get(0);
        if(user == null){
            return BackEntityUtil.getReponseResult(null,BaseConstant.ResponseMSG.ACCOUNT_NOT_EXITS,BaseConstant.ResponseCode.ACCOUNT_NOT_EXITS);
        }
        if(!user.getPassword().equals(MD5HashUtil.toMD5(loginParam.getPassword()))){
            return BackEntityUtil.getReponseResult(null,BaseConstant.ResponseMSG.PASSWORD_ERROR,BaseConstant.ResponseCode.PASSWORD_ERROR);
        }
        CurrentUser currentUser = new CurrentUser();
        currentUser.setAccount(user.getAccount());
        currentUser.setId(user.getId());
        currentUser.setToken(user.getId()+"_"+UUID.randomUUID().toString().replace("-",""));
        BaseParam baseParam = new BaseParam();
        baseParam.setParams(currentUser);
        clientToUser.setCurrentUser(baseParam);
        return BackEntityUtil.getReponseResult(currentUser,Constant.ResponseMSG.REQUEST_OK,Constant.ResponseCode.REQUEST_OK);
    }

    @Override
    public BackEntity register(String phone) throws Exception{
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andPhoneEqualTo(phone);
        List<User> users = userMapper.selectByExample(example);
        if(users != null && !users.isEmpty()){
            return BackEntityUtil.getReponseResult(null, BaseConstant.ResponseMSG.PHONE_USED,BaseConstant.ResponseCode.PHONE_USED);
        }
        User user = new User();
        user.setAccount(CommonUtil.getStr(6));
        user.setJointime(DateUtil.getLongCurrentTime());
        user.setPhone(phone);
        user.setPassword(MD5HashUtil.toMD5("123456"));
        userMapper.insertSelective(user);
        return BackEntityUtil.getReponseResult(null, Constant.ResponseMSG.REQUEST_OK,Constant.ResponseCode.REQUEST_OK);
    }
}
