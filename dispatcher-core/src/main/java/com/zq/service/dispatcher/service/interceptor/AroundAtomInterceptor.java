package com.zq.service.dispatcher.service.interceptor;

import com.zq.service.dispatcher.convertor.TypeConvertor;
import com.zq.service.dispatcher.exception.ServiceBusinessException;
import com.zq.service.dispatcher.exception.ServiceReflectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @program: service-dispatcher
 * @description: 环绕拦截器
 * @author: zhouqi1
 * @create: 2018-06-28 11:37
 **/
public class AroundAtomInterceptor implements AtomInterceptor {

    private Logger logger = LoggerFactory.getLogger(AroundAtomInterceptor.class);

    private Object target;

    private Method method;

    private TypeConvertor typeConvertor;

    public AroundAtomInterceptor(Object target, Method method, TypeConvertor typeConvertor) {
        this.target = target;
        this.method = method;
        this.typeConvertor = typeConvertor;
    }

    @Override
    public Object intercept(AtomInterceptorContext interceptorContext) {
        try{
            Object param = interceptorContext.getParam();
            if(typeConvertor != null){
                param = typeConvertor.convert(param);
            }
            return method.invoke(target, param, interceptorContext);
        }catch (InvocationTargetException e){
            throw new ServiceBusinessException(e.getTargetException());
        }catch (Exception e){
            logger.error("拦截器执行异常", e);
            throw new ServiceReflectException(e);
        }
    }
}
