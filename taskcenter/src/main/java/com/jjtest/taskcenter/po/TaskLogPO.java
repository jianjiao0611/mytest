package com.jjtest.taskcenter.po;

import lombok.Data;

import java.util.Date;

/**
 * 任务日志po
 */
@Data
public class TaskLogPO {

    private Long id;
    /**
     * 任务id
     */
    private String taskId;
    /**
     * 任务名
     */
    private String taskName;
    /**
     * 状态：0、执行中 1、执行成功 2、执行失败
     */
    private Integer status;
    /**
     * 任务开始时间
     */
    private Date taskStartTime;
    /**
     * 任务结束时间
     */
    private Date taskEndTime;
    /**
     * 执行结果
     */
    private String result;
    /**
     * 执行参数
     */
    private String taskParam;
}
