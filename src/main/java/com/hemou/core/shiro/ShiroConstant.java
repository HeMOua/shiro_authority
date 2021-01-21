package com.hemou.core.shiro;

public class ShiroConstant {

    /**
     * 数据库
     */
    public static final int DB_INDEX = 0;
    public static final int SESSION_EXPIRE_TIME = 1800;

    /**
     * 项目 session 标记
     */
    public static final String SHIRO_SESSION_TAG = "shiro-session:";

    /**
     * 查询 session 表达式
     */
    public static final String SHIRO_SESSION_ALL = "*shiro-session:*";

    /**
     * 在线状态属性名
     */
    public static final String ONLINE_STATUS_KEY = "shiro-online-status";

    /**
     * 踢出后到达页面
     */
    public static final String KICK_OUT_URL = "/u/kickout.shtml";

    /**
     * 踢出状态属性名
     */
    public static final String KICK_OUT_STATUS_KEY = "shiro-kick-out";

    /**
     * 在线状态标记
     */
    public static final String ONLINE_STATUS_TAG = "shiro-online:";
}
