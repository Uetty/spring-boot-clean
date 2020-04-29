package com.uetty.sample.springboot.constant;

import org.springframework.context.ApplicationContext;

/**
 * 存储项目全局变量
 */
public class ApplicationVariable {

    /**
     * applicationContext
     */
    public volatile static ApplicationContext APPLICATION_CONTEXT;
    /**
     * 项目状态
     */
    public volatile static int APPLICATION_STATUS = Constant.APP_STATUS_STARTING; // spring容器状态
    /**
     * 项目环境
     */
    public volatile static String SERVER_PROFILE;
    /**
     * 项目版本
     */
    public volatile static String SERVER_EDITION;
}
