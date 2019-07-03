package com.zq.service.dispatcher;

import com.zq.service.dispatcher.context.CoreServiceContext;
import com.zq.service.dispatcher.context.IocDefinitionContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @program: service-dispatcher
 * @description: 服务分发器
 * @author: zhouqi1
 * @create: 2018-05-30 12:39
 **/
public class AnnotationServiceDispatcher extends AbstractServiceDispatcher implements ServiceDispatcher {

    public AnnotationServiceDispatcher(ApplicationContext applicationContext) {
        super(new IocDefinitionContext(applicationContext));
    }
}
