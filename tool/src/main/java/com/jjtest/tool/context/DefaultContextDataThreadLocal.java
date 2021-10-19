package com.jjtest.tool.context;

import com.jjtest.tool.model.LoginUserModel;

/**
 * 上下文数据
 */
public class DefaultContextDataThreadLocal {

    private static final ThreadLocal<ContextData> CONTEXT_DATA = new ThreadLocal<>();

    private static final ThreadLocal<LoginUserModel> LOGIN_USER = new ThreadLocal<>();

    public static void setLoginUser(LoginUserModel loginUser) {
        LOGIN_USER.set(loginUser);
    }

    public static LoginUserModel getLoginUserModel() {
        return LOGIN_USER.get();
    }

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
