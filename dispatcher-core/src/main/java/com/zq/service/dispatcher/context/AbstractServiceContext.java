package com.zq.service.dispatcher.context;

import com.zq.service.dispatcher.config.ServiceKey;
import com.zq.service.dispatcher.service.AtomService;
import com.zq.service.dispatcher.storage.AtomServiceStorage;
import com.zq.service.dispatcher.storage.ServiceStorage;

/**
 * @program: service-dispatcher
 * @description: 基于注解服务上下文
 * @author: zhouqi1
 * @create: 2018-06-28 10:14
 **/
public abstract class AbstractServiceContext implements ServiceContext {

    protected ServiceStorage  serviceStorage;

    public AbstractServiceContext() {
        serviceStorage = new AtomServiceStorage();
    }

    @Override
    public void register(ServiceKey serviceKey, AtomService service) {
        serviceStorage.register(serviceKey.toServiceKey(), service);
    }

    @Override
    public AtomService find(ServiceKey serviceKey) {
        String key = serviceKey.toServiceKey();
        if(!serviceStorage.contains(key)){
            key =  ServiceKey.newBuilder()
                    .group(serviceKey.getGroup())
                    .name(ServiceKey.DEFAULT_NAME)
                    .version(serviceKey.getVersion())
                    .build()
                    .toServiceKey();
        }
        return serviceStorage.find(key);
    }
}
