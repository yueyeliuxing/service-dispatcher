package com.zq.service.dispatcher.convertor;

import com.zq.service.dispatcher.convertor.BaseTypeConvertor;
import com.zq.service.dispatcher.reflect.ClassUtils;
import lombok.Data;
import lombok.ToString;

/**
 * @program: service-dispatcher
 * @description: 转换器配置
 * @author: zhouqi1
 * @create: 2018-06-13 19:18
 **/
@Data
@ToString
public class ConvertorWapper {

    private Class typeConvertorClazz;

    private Class typeClass;

    public Object convert(Object param){
        BaseTypeConvertor baseTypeConvertor = (BaseTypeConvertor) ClassUtils.newBean(typeConvertorClazz, typeClass);
        return baseTypeConvertor.convert(param);
    }


}
