package com.zq.service.dispatcher.context;

import com.zq.service.dispatcher.service.interceptor.AtomInterceptor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @program: service-dispatcher
 * @description: 默认的描述上下文
 * @author: zhouqi1
 * @create: 2019-07-02 19:57
 **/
public class DefaultDefinitionContext implements DefinitionContext {

    private Map<String, List<InterceptorDefinition>> interceptorDefinitionMap;

    private Map<String, ServiceDefinition> serviceDefinitionMap;

    public DefaultDefinitionContext() {
        interceptorDefinitionMap = new HashMap<>();
        serviceDefinitionMap = new ConcurrentHashMap<>();
    }

    @Override
    public void addInterceptorDefinition(String key, InterceptorDefinition interceptorDefinition) {
        synchronized (interceptorDefinitionMap){
            List<InterceptorDefinition> interceptorDefinitions = interceptorDefinitionMap.get(key);
            if(interceptorDefinitions == null){
                interceptorDefinitions = new ArrayList<>();
            }
            interceptorDefinitions.add(interceptorDefinition);
        }
    }

    @Override
    public void addServiceDefinition(String key, ServiceDefinition serviceDefinition) {
        serviceDefinitionMap.put(key, serviceDefinition);
    }

    @Override
    public Set<String> getServiceKeys() {
        return serviceDefinitionMap.keySet();
    }

    @Override
    public ServiceDefinition getServiceDefinition(String key) {
        return serviceDefinitionMap.get(key);
    }

    @Override
    public List<InterceptorDefinition> getInterceptorChain(String key) {
        List<InterceptorDefinition> interceptorDefinitions = interceptorDefinitionMap.get(key);
        if(interceptorDefinitions != null){
            interceptorDefinitions =  interceptorDefinitions.stream().sorted(Comparator.comparing(InterceptorDefinition::getPriority).reversed()).collect(Collectors.toList());
        }
        return interceptorDefinitions;
    }
}
