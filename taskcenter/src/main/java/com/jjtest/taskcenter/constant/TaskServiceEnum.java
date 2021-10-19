package com.jjtest.taskcenter.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;

@Getter
@AllArgsConstructor
public enum TaskServiceEnum {

    CANCEL_ORDER_TASK("cancel_order_task", "取消订单", "cancelOrderService");

    private String taskId;

    private String taskName;

    private String taskService;

    public static TaskServiceEnum getTaskServiceEnumByTaskId(String taskId) {
        if (StringUtils.isBlank(taskId)) {
            return null;
        }
        for (TaskServiceEnum taskServiceEnum : values()) {
            if (taskServiceEnum.taskId.equals(taskId)) {
                return  taskServiceEnum;
            }
        }
        return null;
    }

}
