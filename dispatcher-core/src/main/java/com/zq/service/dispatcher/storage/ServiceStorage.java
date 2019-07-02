package com.zq.service.dispatcher.storage;

import com.zq.service.dispatcher.service.AtomService;

/**
 * @program: service-dispatcher
 * @description: 服务存储
 * @author: zhouqi1
 * @create: 2018-06-28 10:38
 **/
public interface ServiceStorage {
    /**
     *
     *   服务注册
     * @author zhouqi
     * @date 2018/6/28 10:12
     * @param serviceKey
     * @param service
     * @return
     */
    void register(String serviceKey, AtomService service);

    /**
     *
     *   服务查找
     * @author zhouqi
     * @date 2018/6/28 10:12
     * @param serviceKey
     * @return
     */
    AtomService find(String serviceKey);

    /**
     *
     *   是否包含指定的服务
     * @author zhouqi
     * @date 2018/6/28 10:57
     * @param serviceKey
     * @return
     */
    boolean contains(String serviceKey);

}
