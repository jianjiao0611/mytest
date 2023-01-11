package com.jjtest.taskcenter.task;

import com.jjtest.taskcenter.constant.TaskServiceEnum;
import com.jjtest.taskcenter.task.base.TaskHander;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class CancelOrderTask {
    @Autowired
    private TaskHander taskHander;

    @Scheduled(cron = "0 0/3 * * * ?")
    public void cancelOrder() {
        taskHander.doTask(TaskServiceEnum.CANCEL_ORDER_TASK.getTaskId(), "test",true, true);
    }

}
