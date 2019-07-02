package com.zq.service.dispatcher.context;

import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Set;

/**
 * @program: service-dispatcher
 * @description: 从Ioc容器中获取Definition
 * @author: zhouqi1
 * @create: 2019-07-02 20:03
 **/
public class IocDefinitionContext implements DefinitionContext {

    private DefinitionContext definitionContext;

    public IocDefinitionContext(ApplicationContext applicationContext) {
        this.definitionContext = new DefaultDefinitionContext();

        loadDefinitions(applicationContext);
    }

    /**
     * 加载service interceptor描述
     * @param applicationContext
     */
    private void loadDefinitions(ApplicationContext applicationContext) {
        IocDefinitionReader iocDefinitionReader = new IocDefinitionReader(this);
        iocDefinitionReader.loadDefinitions(applicationContext);
    }


    @Override
    public void addInterceptorDefinition(String key, InterceptorDefinition interceptorDefinition) {
        definitionContext.addInterceptorDefinition(key, interceptorDefinition);
    }

    @Override
    public void addServiceDefinition(String key, ServiceDefinition serviceDefinition) {
        definitionContext.addServiceDefinition(key, serviceDefinition);
    }

    @Override
    public Set<String> getServiceKeys() {
        return definitionContext.getServiceKeys();
    }

    @Override
    public ServiceDefinition getServiceDefinition(String key) {
        return definitionContext.getServiceDefinition(key);
    }

    @Override
    public List<InterceptorDefinition> getInterceptorChain(String key) {
        return definitionContext.getInterceptorChain(key);
    }
}
