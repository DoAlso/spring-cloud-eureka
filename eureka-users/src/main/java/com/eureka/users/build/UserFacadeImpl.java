package com.eureka.users.build;

import com.eurake.common.params.LoginParam;
import com.eureka.common.constant.Constant;
import com.eureka.common.dao.UserMapper;
import com.eureka.common.model.BackEntity;
import com.eureka.common.model.base.User;
import com.eureka.common.model.base.UserExample;
import com.eureka.common.utils.BackEntityUtil;
import com.eureka.common.utils.CommonUtil;
import com.eureka.common.utils.DateUtil;
import com.eureka.common.utils.MD5HashUtil;
import com.eureka.users.constant.BaseConstant;
import com.eureka.users.provider.UserFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserFacadeImpl implements UserFacade {
    private static final Logger logger = LoggerFactory.getLogger(UserFacadeImpl.class);

    @Autowired
    private UserMapper userMapper;
    @Override
    public BackEntity login(LoginParam loginParam) throws Exception{
        return null;
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
