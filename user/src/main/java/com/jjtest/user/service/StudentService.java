package com.jjtest.user.service;

import com.jjtest.user.dao.UserMapper;
import com.jjtest.user.po.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    public void updateUser(UserPO userPO) {
        userMapper.updateUser(userPO);

        userService.updateUserPhone(userPO);
    }

    public void tes(IUser user) {

    }
}
