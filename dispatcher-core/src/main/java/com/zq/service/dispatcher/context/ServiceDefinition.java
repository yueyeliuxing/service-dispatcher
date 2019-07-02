package com.zq.service.dispatcher.context;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.lang.reflect.Method;

/**
 * @program: service-dispatcher
 * @description: 服务描述
 * @author: zhouqi1
 * @create: 2019-07-02 17:55
 **/
@Data
@ToString
@NoArgsConstructor
public class ServiceDefinition {

    /**
     * key
     */
    private String key;

    /**
     * 参数转换器class
     */
    private Class convertor;

    /**
     * 是否异步
     * @return
     */
    private boolean async;

    /**
     * 目标类
     */
    private Object target;

    /**
     * 目标方法
     */
    private Method method;

    public ServiceDefinition(String key, Class convertor, boolean async, Object target, Method method) {
        this.key = key;
        this.convertor = convertor;
        this.async = async;
        this.target = target;
        this.method = method;
    }
}
