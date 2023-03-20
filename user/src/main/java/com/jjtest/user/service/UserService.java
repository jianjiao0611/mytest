package com.jjtest.user.service;

import com.jjtest.user.aop.transactional.MyAnnotation;
import com.jjtest.user.dao.UserMapper;
import com.jjtest.user.po.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户
 */
@Service
public class UserService implements IUser {

    @Autowired
    private UserMapper userMapper;

    public UserPO selectUserByUserName(String userName) {
        UserPO po = new UserPO();
        po.setUserName(userName);
        UserPO userPO = userMapper.selectUser(po);
        return userPO;
    }

    public void updateUser(UserPO userPO) {
        userMapper.updateUser(userPO);

        this.updateUserPhone(userPO);
    }

    @MyAnnotation
    public void updateUserPhone(UserPO userPO) {
        userPO.setPhone("666666");
        userMapper.updateUser(userPO);
        if (true) {
            throw new NullPointerException();
        }
    }

    @Override
    public List<UserPO> selectUserList(UserPO userPO) {
        return null;
    }

    @Override
    public UserPO selectUserByUserNameP(String userName, String pwd) {
        return null;
    }


}
