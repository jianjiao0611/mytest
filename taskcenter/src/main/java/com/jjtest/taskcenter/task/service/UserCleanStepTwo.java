package com.jjtest.taskcenter.task.service;

import com.alibaba.fastjson.JSONObject;
import com.jjtest.taskcenter.po.ProductPO;
import com.jjtest.taskcenter.po.TaskPO;
import com.jjtest.taskcenter.po.UserPO;
import org.springframework.stereotype.Service;

@Service
public class UserCleanStepTwo extends AbsCleanDataHandler<UserPO, ProductPO, TaskPO> {

    @Override
    protected void doHandler(UserPO object1, ProductPO object2, TaskPO result) {
        System.out.println("---stepTwo  start----");
        System.out.println(JSONObject.toJSON(object1));
        System.out.println("---stepTwo  end------");
    }
}
