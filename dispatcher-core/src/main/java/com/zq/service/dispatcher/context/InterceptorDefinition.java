package com.zq.service.dispatcher.context;

import com.zq.service.dispatcher.service.interceptor.ArrangePriority;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.lang.reflect.Method;

/**
 * @program: service-dispatcher
 * @description: 拦截器描述
 * @author: zhouqi1
 * @create: 2019-07-02 17:56
 **/
@Data
@ToString
@NoArgsConstructor
public class InterceptorDefinition {

    /**
     * key
     */
    private String key;

    /**
     * 优先级 {@linkplain ArrangePriority}
     */
    private byte priority;

    /**
     * 参数转换器class
     */
    private Class convertor;

    /**
     * 目标类
     */
    private Object target;

    /**
     * 目标方法
     */
    private Method method;

    public InterceptorDefinition(String key, byte priority, Class convertor, Object target, Method method) {
        this.key = key;
        this.priority = priority;
        this.convertor = convertor;
        this.target = target;
        this.method = method;
    }
}
