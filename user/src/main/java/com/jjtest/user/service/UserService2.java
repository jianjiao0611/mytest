package com.jjtest.user.service;

import com.jjtest.user.po.UserPO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService2 implements IUser {
    @Override
    public UserPO selectUserByUserName(String userName) {
        return null;
    }

    @Override
    public void updateUser(UserPO userPO) {

    }

    @Override
    public void updateUserPhone(UserPO userPO) {

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
