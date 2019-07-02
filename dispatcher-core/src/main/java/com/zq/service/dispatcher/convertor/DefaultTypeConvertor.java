package com.zq.service.dispatcher.convertor;

/**
 * @program: service-dispatcher
 * @description: 默认类型转换器
 * @author: zhouqi1
 * @create: 2018-06-13 10:12
 **/
public class DefaultTypeConvertor extends BaseTypeConvertor<Object, Object> {

    @Override
    public Object convert(Object obj) {
        return obj;
    }
}
