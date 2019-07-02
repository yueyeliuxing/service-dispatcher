package com.zq.service.dispatcher.service;

import com.zq.service.dispatcher.convertor.ConvertorWapper;
import com.zq.service.dispatcher.convertor.TypeConvertor;
import com.zq.service.dispatcher.exception.ServiceBusinessException;
import com.zq.service.dispatcher.exception.ServiceReflectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @program: service-dispatcher
 * @description: 核心原子服务
 * @author: zhouqi1
 * @create: 2018-06-28 11:01
 **/
public class CoreAtomService extends AbstractAtomService implements AtomService {

    private Logger logger = LoggerFactory.getLogger(CoreAtomService.class);

    private Object target;

    private Method method;

    private TypeConvertor typeConvertor;

    public CoreAtomService(Object target, Method method, TypeConvertor typeConvertor) {
        this.target = target;
        this.method = method;
        this.typeConvertor = typeConvertor;
    }

    @Override
    public Object service(Object param) {
        try{
            if(typeConvertor != null){
                param = typeConvertor.convert(param);
            }
            return method.invoke(target, param);
        }catch (InvocationTargetException e){
            throw new ServiceBusinessException(e.getTargetException().getMessage());
        }catch (Exception e){
            logger.error("服务执行异常", e);
            throw new ServiceReflectException(e);
        }

    }
}
