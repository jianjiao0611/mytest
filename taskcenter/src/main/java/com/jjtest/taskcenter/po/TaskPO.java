package com.jjtest.taskcenter.po;

import lombok.Data;

/**
 * 任务po
 */
@Data
public class TaskPO {
    /**
     * id
     */
    private String taskId;
    /**
     * 任务名
     */
    private String taskName;
    /**
     * cron表达式
     */
    private String taskCron;
    /**
     * cron表达式说明
     */
    private String taskCronDesc;
    /**
     * 任务状态：1、启用 2、禁用
     */
    private Integer taskStatus;
    /**
     * 固定参数
     */
    private String taskParamFixedJson;

}
