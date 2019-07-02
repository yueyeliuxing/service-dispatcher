package com.zq.service.dispatcher.service.interceptor;

/**
 * @program: service-dispatcher
 * @description: 拦截器
 * @author: zhouqi1
 * @create: 2018-06-28 11:36
 **/
public interface AtomInterceptor {

    /**
     * 拦截方法
     * @param interceptorContext
     * @return
     */
    Object intercept(AtomInterceptorContext interceptorContext);
}
