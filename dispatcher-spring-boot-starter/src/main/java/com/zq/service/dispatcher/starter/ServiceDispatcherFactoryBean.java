package com.zq.service.dispatcher.starter;

import com.zq.service.dispatcher.AnnotationServiceDispatcher;
import com.zq.service.dispatcher.ServiceDispatcher;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @program: service-dispatcher
 * @description: ServiceDistapcher工厂Bean
 * @author: zhouqi1
 * @create: 2019-07-03 17:50
 **/
public class ServiceDispatcherFactoryBean implements FactoryBean<ServiceDispatcher>, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public ServiceDispatcher getObject() throws Exception {
        return new AnnotationServiceDispatcher(applicationContext);
    }

    @Override
    public Class<?> getObjectType() {
        return ServiceDispatcher.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }


}
