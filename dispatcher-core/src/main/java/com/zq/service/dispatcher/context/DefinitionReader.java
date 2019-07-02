package com.zq.service.dispatcher.context;

/**
 * @program: service-dispatcher
 * @description: definition读入器
 * @author: zhouqi1
 * @create: 2019-07-02 19:24
 **/
public interface DefinitionReader<T> {

    /**
     * 加载Definition
     * @param resource
     */
    void loadDefinitions(T resource);
}
