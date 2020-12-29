package com.jjtest.user.service;

import com.jjtest.user.dao.UserMapper;
import com.jjtest.user.po.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public UserPO selectUserByUserName(String userName) {
        UserPO po = new UserPO();
        po.setUserName(userName);
        UserPO userPO = userMapper.selectUser(po);
        return userPO;
    }

}
