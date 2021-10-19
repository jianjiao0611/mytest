package com.jjtest.taskcenter.task.service;

import com.jjtest.taskcenter.po.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UserCleanService {

    @Autowired
    private UserCleanStepOne userCleanStepOne;

    @Autowired
    private UserCleanStepTwo userCleanStepTwo;

    @PostConstruct
    private void init() {
        this.userCleanStepOne.setNextCleanDataHandler(userCleanStepTwo);
    }

    public void clean() {
        UserPO userPO = new UserPO();
        userPO.setUserName("jj");
        userCleanStepOne.handler(userPO, null, null);
    }

}
