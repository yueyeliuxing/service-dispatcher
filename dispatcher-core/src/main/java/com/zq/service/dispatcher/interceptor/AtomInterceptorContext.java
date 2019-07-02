package com.zq.service.dispatcher.interceptor;

import java.lang.reflect.InvocationTargetException;

/**
 * @program: service-dispatcher
 * @description: 拦截器上下文
 * @author: zhouqi1
 * @create: 2018-06-28 11:40
 **/
public interface AtomInterceptorContext {

    Object getParam();

    /**
     *
     *   处理
     * @author zhouqi
     * @date 2018/6/1 14:55
     * @return
     */
    Object dispatch() throws IllegalAccessException, InvocationTargetException;
}
