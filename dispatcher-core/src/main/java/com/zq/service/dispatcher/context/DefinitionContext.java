package com.zq.service.dispatcher.context;

import java.util.List;
import java.util.Set;

/**
 * @program: service-dispatcher
 * @description: 描述上下文
 * @author: zhouqi1
 * @create: 2019-07-02 19:19
 **/
public interface DefinitionContext {

    /**
     * 添加拦截器描述
     * @param key
     * @param interceptorDefinition
     */
    void addInterceptorDefinition(String key, InterceptorDefinition interceptorDefinition);

    /**
     * 添加服务描述
     * @param key
     * @param serviceDefinition
     */
    void addServiceDefinition(String key, ServiceDefinition serviceDefinition);

    /**
     * 得到服务key集合
     * @return
     */
    Set<String> getServiceKeys();

    /**
     * 得到服务描述
     * @param key
     * @return
     */
    ServiceDefinition getServiceDefinition(String key);

    /**
     * 得到拦截器链
     * @param key
     * @return
     */
    List<InterceptorDefinition> getInterceptorChain(String key);
}
