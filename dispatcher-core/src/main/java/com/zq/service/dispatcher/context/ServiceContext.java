package com.zq.service.dispatcher.context;

import com.zq.service.dispatcher.config.ServiceKey;
import com.zq.service.dispatcher.service.AtomService;

/**
 * @program: service-dispatcher
 * @description: 服务上下文
 * @author: zhouqi1
 * @create: 2018-06-28 10:08
 **/
public interface ServiceContext {

    /**
     *
     *   服务注册
     * @author zhouqi
     * @date 2018/6/28 10:12
     * @param serviceKey
     * @param service
     * @return
     */
    void register(ServiceKey serviceKey, AtomService service);

    /**
     *
     *   获取服务
     * @author zhouqi
     * @date 2018/6/28 13:09
     * @param serviceKey
     * @return
     */
    AtomService find(ServiceKey serviceKey);
}
