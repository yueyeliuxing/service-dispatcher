package com.zq.service.dispatcher.service;

/**
 * @program: service-dispatcher
 * @description: 服务生命周期
 * @author: zhouqi1
 * @create: 2018-06-28 12:00
 **/
public interface ServiceLifecycle {

    void init();

    void destroy();
}
