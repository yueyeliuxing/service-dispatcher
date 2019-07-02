package com.zq.service.dispatcher.storage;

import com.zq.service.dispatcher.service.AtomService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: service-dispatcher
 * @description: 抽象存储类
 * @author: zhouqi1
 * @create: 2018-06-28 10:43
 **/
public class AtomServiceStorage implements ServiceStorage {

    private Map<String, AtomService> atomServiceMap;

    public AtomServiceStorage() {
        atomServiceMap = new ConcurrentHashMap<>();
    }

    @Override
    public void register(String serviceKey, AtomService service) {
        atomServiceMap.put(serviceKey, service);
    }

    @Override
    public AtomService find(String serviceKey) {
        return atomServiceMap.get(serviceKey);
    }

    @Override
    public boolean contains(String serviceKey) {
        return atomServiceMap.containsKey(serviceKey);
    }
}
