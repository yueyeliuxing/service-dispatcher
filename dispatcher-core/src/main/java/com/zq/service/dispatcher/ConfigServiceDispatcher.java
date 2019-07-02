package com.zq.service.dispatcher;

import com.zq.service.dispatcher.config.ServiceKey;
import com.zq.service.dispatcher.context.ConfigServiceContext;
import com.zq.service.dispatcher.interceptor.AtomInterceptor;
import com.zq.service.dispatcher.service.AtomService;
import com.zq.service.dispatcher.service.InterceptAtomService;

import java.util.List;

/**
 * @program: service-dispatcher
 * @description: 服务分发器
 * @author: zhouqi1
 * @create: 2018-05-30 12:39
 **/
public class ConfigServiceDispatcher extends AbstractServiceDispatcher implements ServiceDispatcher {

    public ConfigServiceDispatcher() {
        serviceContext = new ConfigServiceContext();
    }

    public void addService(ServiceKey serviceKey, AtomService service) {
        serviceContext.register(serviceKey, service);
    }

    public void addService(ServiceKey serviceKey, AtomService service, List<AtomInterceptor> interceptors) {
        serviceContext.register(serviceKey, new InterceptAtomService(service, interceptors));
    }
}
