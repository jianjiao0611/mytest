package com.jjtest.tool.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * string util
 */
public class StringUtils {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtils.class);

    public static Integer getInteger(String s, Integer defInteger) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            LOGGER.error("字符串转换异常");
            return defInteger;
        }
    }
}
