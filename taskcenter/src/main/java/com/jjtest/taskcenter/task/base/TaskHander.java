package com.jjtest.taskcenter.task.base;

import com.jjtest.taskcenter.constant.TaskServiceEnum;
import com.jjtest.taskcenter.constant.TaskStatusEnum;
import com.jjtest.taskcenter.po.TaskLogPO;
import com.jjtest.taskcenter.po.TaskPO;
import com.jjtest.taskcenter.service.TaskLogService;
import com.jjtest.taskcenter.service.TaskService;
import com.jjtest.tool.exception.ServiceException;
import com.jjtest.tool.util.SpringUtils;
import com.jjtest.tool.util.threadpool.AsyncInvoke;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskHander {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskLogService taskLogService;
    @Autowired
    private AsyncInvoke asyncInvoke;

    /**
     * 执行任务
     * @param taskId 任务id
     * @param param 执行参数
     * @param isRepeat 是否允许重复执行
     * @param isAsync 是否异步
     */
    public void doTask(String taskId, String param, boolean isRepeat, boolean isAsync) {
        TaskPO taskPO = taskService.selectTaskByTaskId(taskId);
        if (taskPO == null) {
            throw new ServiceException("未找到任务");
        }
        if (taskPO.getTaskStatus() == 2) {
            throw new ServiceException("任务未启用");
        }
        if (!isRepeat) {
            List<TaskLogPO> taskLogPOS = taskLogService.selectLogByTaskIdAndParam(taskId, param);
            if (CollectionUtils.isNotEmpty(taskLogPOS)) {
                throw new ServiceException("任务已执行");
            }
        }
        TaskServiceEnum taskEnum = TaskServiceEnum.getTaskServiceEnumByTaskId(taskId);
        if (taskEnum == null) {
            throw new ServiceException("任务枚举不存在");
        }
        ITaskService iTaskService = SpringUtils.getBean(taskEnum.getTaskService());

        if (isAsync) {
            asyncInvoke.submit(() -> {
                iTaskService.run(taskPO, param);
            });
        } else {
            iTaskService.run(taskPO, param);
        }
    }

    /**
     * 重试任务
     * @param taskLogId 日志id
     * @param isForce 是否强制重试
     * @param isAsync 是否异步
     */
    public void tryAgain(Long taskLogId, boolean isForce, boolean isAsync) {
        TaskLogPO taskLogPO = taskLogService.selectTaskLogById(taskLogId);
        if (taskLogPO == null) {
            throw new ServiceException("未找到执行日志");
        }
        TaskPO taskPO = taskService.selectTaskByTaskId(taskLogPO.getTaskId());
        if (taskPO == null) {
            throw new ServiceException("未找到任务");
        }
        TaskServiceEnum taskEnum = TaskServiceEnum.getTaskServiceEnumByTaskId(taskLogPO.getTaskId());
        if (taskEnum == null) {
            throw new ServiceException("任务枚举不存在");
        }
        ITaskService iTaskService = SpringUtils.getBean(taskEnum.getTaskService());
        if (!isForce && taskLogPO.getStatus() == TaskStatusEnum.SUCCESS.getStatus()) {
            throw new ServiceException("任务已经执行过");
        }
        if (isAsync) {
            asyncInvoke.submit(() -> {
                iTaskService.runAgain(taskPO, taskLogId, isForce);
            });
        } else {
            iTaskService.runAgain(taskPO, taskLogId, isForce);
        }

    }

}
