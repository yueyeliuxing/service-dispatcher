package com.zq.service.dispatcher.interceptor;

import java.lang.reflect.InvocationTargetException;

/**
 * @program: service-dispatcher
 * @description: 拦截器
 * @author: zhouqi1
 * @create: 2018-06-28 11:36
 **/
public interface AtomInterceptor {

    /**
     * 优先级
     * @return
     */
    byte priority();

    /**
     * 拦截方法
     * @param interceptorContext
     * @return
     */
    Object intercept(AtomInterceptorContext interceptorContext);
}
