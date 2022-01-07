package com.jjtest.tool.context;

import com.jjtest.tool.model.InterfaceLogPO;

/**
 * 接口日志上下文常量
 */
public class LogDataThreadLocal {

    private static final ThreadLocal<InterfaceLogPO> INTERFACE_LOG_PO_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 设置日志对象
     * @param logPO
     */
    public static void setInterfaceLog(InterfaceLogPO logPO) {
        INTERFACE_LOG_PO_THREAD_LOCAL.set(logPO);
    }

    /**
     * 获取日志对象
     * @return
     */
    public static InterfaceLogPO getInterfaceLog() {
        return INTERFACE_LOG_PO_THREAD_LOCAL.get();
    }

    public static void removeLog() {
        INTERFACE_LOG_PO_THREAD_LOCAL.remove();
    }
}
