package com.jjtest.taskcenter.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 任务状态枚举
 */
@Getter
@AllArgsConstructor
public enum TaskStatusEnum {

    IN_EXECUTION(0, "执行中"),
    SUCCESS(1, "成功"),
    FAIL(2, "失败");

    private int status;

    private String desc;
}
