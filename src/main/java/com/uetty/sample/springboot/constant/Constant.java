package com.uetty.sample.springboot.constant;

/**
 * 全局常量类
 */
public class Constant {

    // 程序环境
    public static final String PROFILE_DEVELOP = "develop";
    public static final String PROFILE_PRODUCT = "product";
    public static final String PROFILE_TEST = "test";

    public static final int APP_STATUS_STARTING = 0; // 容器启动中
    public static final int APP_STATUS_STARTED = 1; // 容器已启动
    public static final int APP_STATUS_STOPPING = 2; // 容器所有bean收到销毁信号
    public static final int APP_STATUS_CLOSE = 3; // 容器所有bean被销毁


}
