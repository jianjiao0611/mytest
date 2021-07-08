package com.jjtest.user.service;

import com.jjtest.user.po.UserPO;

public interface IUser {

    UserPO selectUserByUserName(String userName);

    void updateUser(UserPO userPO);

    void updateUserPhone(UserPO userPO);
}
