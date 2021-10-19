package com.jjtest.taskcenter.task.base;

import com.jjtest.taskcenter.po.TaskPO;

public interface ITaskService {

    void run(TaskPO taskPO, String taskParam);

    void runAgain(TaskPO taskPO, Long taskLogId, boolean isForce);

}
