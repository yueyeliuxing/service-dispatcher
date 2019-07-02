package com.zq.service.dispatcher;

import com.zq.service.dispatcher.config.ServiceConfig;
import com.zq.service.dispatcher.config.ServiceKey;
import com.zq.service.dispatcher.context.ServiceContext;
import com.zq.service.dispatcher.exception.ServiceNotFindException;
import com.zq.service.dispatcher.service.AtomService;

import java.lang.reflect.InvocationTargetException;

/**
 * @program: service-dispatcher
 * @description: 服务分发器
 * @author: zhouqi1
 * @create: 2018-05-30 12:39
 **/
public abstract class AbstractServiceDispatcher implements ServiceDispatcher {

    protected ServiceContext serviceContext;

    @Override
    public Object dispatcher(ServiceConfig serviceConfig) {
        ServiceKey serviceKey = serviceConfig.getServiceKey();
        Object context = serviceConfig.getContext();

        AtomService atomService = serviceContext.find(serviceKey);
        if(atomService == null){
            throw new ServiceNotFindException(String.format("not find serviceKey:%s -> handler ", serviceKey.toServiceKey()));
        }
        return atomService.service(context);
    }


}
