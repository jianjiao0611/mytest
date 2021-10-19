package com.jjtest.taskcenter.dao.user;

import com.jjtest.taskcenter.po.TaskLogPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 任务日志dao
 */
@Mapper
public interface TaskLogDao {

    /**
     * 插入日志
     * @param po 日志对象
     * @return 行数
     */
    int insertTaskLog(TaskLogPO po);

    /**
     * 通过日志id查询日志
     * @param taskLogId 日志id
     * @return 日志
     */
    TaskLogPO selectTaskLogByTaskId(Long taskLogId);

    /**
     * 修改日志
     * @param taskLogPO
     * @return
     */
    int updateTaskLog(TaskLogPO taskLogPO);

    /**
     * 通过参数查询日志
     * @param taskId 任务id
     * @param param 参数
     */
    List<TaskLogPO> selectLogByTaskIdAndParam(@Param("taskId") String taskId, @Param("param") String param);
}
