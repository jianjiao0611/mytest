package com.jjtest.taskcenter.service;

import com.jjtest.taskcenter.dao.user.TaskDao;
import com.jjtest.taskcenter.po.TaskPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 任务service
 */
@Service
public class TaskService {

    @Autowired
    private TaskDao taskDao;

    public TaskPO selectTaskByTaskId(String taskId) {
        return taskDao.selectTaskByTaskId(taskId);
    }

}
