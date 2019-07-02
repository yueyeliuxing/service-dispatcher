package com.zq.service.dispatcher.convertor;

/**
 * @program: service-dispatcher
 * @description: 类型转换器
 * @author: zhouqi1
 * @create: 2018-06-13 10:11
 **/
public interface TypeConvertor<P, R> {

    R convert(P param);
}
