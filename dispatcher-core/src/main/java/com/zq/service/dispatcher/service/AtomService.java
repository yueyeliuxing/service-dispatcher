package com.zq.service.dispatcher.service;

import java.lang.reflect.InvocationTargetException;

/**
 * @program: service-dispatcher
 * @description: 服务接口
 * @author: zhouqi1
 * @create: 2018-06-28 10:10
 **/
public interface AtomService extends ServiceLifecycle{

    Object service(Object param);
}
