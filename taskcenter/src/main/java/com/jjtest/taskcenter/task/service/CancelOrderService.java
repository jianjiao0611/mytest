package com.jjtest.taskcenter.task.service;

import com.jjtest.taskcenter.po.TaskLogPO;
import com.jjtest.taskcenter.po.TaskPO;
import com.jjtest.taskcenter.task.base.AbsNormalTask;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CancelOrderService extends AbsNormalTask {
    @Override
    protected void doTask(TaskPO taskPO, TaskLogPO taskLogPO, List<String> msg) {
        msg.add("开始");

        msg.add("完成");
    }

    @Override
    protected void tryAgain(TaskPO taskPO, TaskLogPO taskLogPO, boolean isForce, List<String> msg) {

    }
}
