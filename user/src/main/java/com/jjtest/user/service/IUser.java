package com.jjtest.user.service;

import com.jjtest.user.po.UserPO;

import java.util.List;

public interface IUser {

    UserPO selectUserByUserName(String userName);

    void updateUser(UserPO userPO);

    void updateUserPhone(UserPO userPO);

    List<UserPO> selectUserList(UserPO userPO);

    UserPO selectUserByUserNameP(String userName, String pwd);
}
