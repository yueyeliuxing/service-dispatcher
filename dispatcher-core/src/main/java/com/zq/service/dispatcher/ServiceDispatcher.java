package com.zq.service.dispatcher;

import com.zq.service.dispatcher.config.ServiceConfig;

import java.lang.reflect.InvocationTargetException;

/**
 * @program: service-dispatcher
 * @description: 服务分发器
 * @author: zhouqi1
 * @create: 2018-05-30 12:39
 **/
public interface ServiceDispatcher {

    /**
     *
     *   服务转发
     * @author zhouqi
     * @date 2018/6/29 10:24
     * @param serviceConfig
     * @return
     * @throws com.zq.service.dispatcher.exception.ServiceNotFindException
     * @throws com.zq.service.dispatcher.exception.ServiceBusinessException
     * @throws com.zq.service.dispatcher.exception.ServiceReflectException
     */
    Object dispatcher(ServiceConfig serviceConfig);

}
