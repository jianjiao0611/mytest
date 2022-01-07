package com.jjtest.tool.log;

import com.alibaba.fastjson.JSONObject;
import com.jjtest.tool.context.LogDataThreadLocal;
import com.jjtest.tool.model.InterfaceLogPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志utils
 */
public class LogUtils {

    private static final Logger INTERFACE_LOGGER = LoggerFactory.getLogger("interface_log");

    public static void interfaceLog(Object msg, String code) {
        InterfaceLogPO interfaceLog = LogDataThreadLocal.getInterfaceLog();

        if (interfaceLog == null) {
            return;
        }
        interfaceLog.setResultCode(code);
        interfaceLog.setResult(JSONObject.toJSONString(msg));
        INTERFACE_LOGGER.info(JSONObject.toJSONString(interfaceLog));
    }

    public static void interfaceLog(InterfaceLogPO logPO) {
        INTERFACE_LOGGER.info(JSONObject.toJSONString(logPO));
    }

    public static int getOrderNum() {
        InterfaceLogPO interfaceLog = LogDataThreadLocal.getInterfaceLog();
        if (interfaceLog == null) {
            return 0;
        }
        return interfaceLog.getOrderNum();
    }
}
