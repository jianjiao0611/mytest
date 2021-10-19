package com.jjtest.taskcenter.task.base;

import com.jjtest.taskcenter.constant.TaskStatusEnum;
import com.jjtest.taskcenter.po.TaskLogPO;
import com.jjtest.taskcenter.po.TaskPO;
import com.jjtest.taskcenter.service.TaskLogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbsNormalTask implements ITaskService {

    /**
     * 日志service
     */
    @Autowired
    private TaskLogService taskLogService;

    @Override
    public void run(TaskPO taskPO, String taskParam) {
        TaskLogPO taskLogPO = null;
        TaskStatusEnum statusEnum = null;
        List<String> msg = new ArrayList<>(10);
        try {
            statusEnum = TaskStatusEnum.IN_EXECUTION;
            taskLogPO = new TaskLogPO();
            taskLogPO.setTaskId(taskPO.getTaskId());
            taskLogPO.setTaskName(taskPO.getTaskName());
            taskLogPO.setTaskParam(taskParam);
            taskLogPO.setTaskStartTime(new Date());
            taskLogPO.setStatus(TaskStatusEnum.IN_EXECUTION.getStatus());
            taskLogService.insertTaskLog(taskLogPO);
            this.doTask(taskPO, taskLogPO, msg);
            taskLogPO.setTaskEndTime(new Date());
            statusEnum = TaskStatusEnum.SUCCESS;
        } catch (Exception e) {
            statusEnum = TaskStatusEnum.FAIL;
        } finally {
            taskLogPO.setStatus(statusEnum.getStatus());
            taskLogPO.setResult(msg.stream().collect(Collectors.joining(",")));
            taskLogService.updateTaskLog(taskLogPO);
        }
    }

    @Override
    public void runAgain(TaskPO taskPO, Long taskLogId, boolean isForce) {
        TaskLogPO taskLogPO = null;
        List<String> msg = new ArrayList<>(10);
        TaskStatusEnum statusEnum = null;
        try {
            statusEnum = TaskStatusEnum.IN_EXECUTION;
            taskLogPO = taskLogService.selectTaskLogById(taskLogId);
            taskLogPO.setStatus(statusEnum.getStatus());
            taskLogPO.setTaskStartTime(new Date());
            taskLogService.updateTaskLog(taskLogPO);
            this.tryAgain(taskPO, taskLogPO, isForce, msg);
            taskLogPO.setTaskEndTime(new Date());
            statusEnum = TaskStatusEnum.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            statusEnum = TaskStatusEnum.FAIL;
        } finally {
            taskLogPO.setStatus(statusEnum.getStatus());
            taskLogPO.setResult(msg.stream().collect(Collectors.joining(",")));
            taskLogService.updateTaskLog(taskLogPO);
        }
    }

    /**
     * 执行任务
     * @param taskPO 任务po
     * @param taskLogPO 任务日志po
     * @param msg 执行结果
     */
    protected abstract void doTask(TaskPO taskPO, TaskLogPO taskLogPO, List<String> msg);

    /**
     * 重试任务
     * @param taskPO 任务po
     * @param taskLogPO 任务日志po
     * @param isForce 是否强制重试
     * @param msg 执行结果
     */
    protected abstract void tryAgain(TaskPO taskPO, TaskLogPO taskLogPO, boolean isForce, List<String> msg);

}
