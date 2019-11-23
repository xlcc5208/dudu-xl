package com.xl.project.weixin.api.accessToken;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener//作用是web会把这个类当作一个监听器进行注册和使用
public class AccessTokenListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("启动");
        new AccessTokenThread().start();
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }


}
