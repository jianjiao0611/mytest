package com.jjtest.taskcenter.dao.user;

import com.jjtest.taskcenter.po.TaskPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskDao {

    TaskPO selectTaskByTaskId(String taskId);
}
