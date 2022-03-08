package com.jjtest.tool.context;

import com.jjtest.tool.model.LoginUserModel;

/**
 * 上下文数据
 */
public class DefaultContextDataThreadLocal {

    private static final ThreadLocal<ContextData> CONTEXT_DATA = new ThreadLocal<>();

    /** 登录用户线程变量*/
    private static final ThreadLocal<LoginUserModel> LOGIN_USER = new ThreadLocal<>();

    /**
     * 设置登录用户
     * @param loginUser 登录用户
     */
    public static void setLoginUser(LoginUserModel loginUser) {
        LOGIN_USER.set(loginUser);
    }

    /**
     * 获取登录用户
     * @return 登录用户
     */
    public static LoginUserModel getLoginUserModel() {
        return LOGIN_USER.get();
    }

    /**
     * 移除当前线程登录用户
     */
    public static void cleanLoginUser() {
        LOGIN_USER.remove();
    }

    public static void setContextData(ContextData contextData) {
        CONTEXT_DATA.set(contextData);
    }

    public static ContextData getContextData() {
        return CONTEXT_DATA.get();
    }

}
