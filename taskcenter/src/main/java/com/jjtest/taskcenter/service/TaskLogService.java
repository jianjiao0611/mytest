package com.jjtest.taskcenter.service;

import com.jjtest.taskcenter.dao.user.TaskLogDao;
import com.jjtest.taskcenter.po.TaskLogPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 任务日志service
 */
@Service
public class TaskLogService {

    @Autowired
    private TaskLogDao taskLogDao;

    /**
     * 通过日志id查询日志信息
     * @param taskLogId 日志id
     * @return 日志
     */
    public TaskLogPO selectTaskLogById(Long taskLogId) {
        return taskLogDao.selectTaskLogByTaskId(taskLogId);
    }

    /**
     * 插入日志
     * @param taskLogPO 日志对象
     */
    public void insertTaskLog(TaskLogPO taskLogPO) {
        taskLogDao.insertTaskLog(taskLogPO);
    }

    /**
     * 修改日志结果
     * @param taskLogPO 日志信息
     */
    public void updateTaskLog(TaskLogPO taskLogPO) {
        taskLogDao.updateTaskLog(taskLogPO);
    }

    /**
     * 通过参数查询日志
     * @param taskId 任务id
     * @param param 参数
     */
    public List<TaskLogPO> selectLogByTaskIdAndParam(String taskId, String param) {
        return taskLogDao.selectLogByTaskIdAndParam(taskId, param);
    }

}
