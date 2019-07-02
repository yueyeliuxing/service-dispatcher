package com.zq.service.dispatcher.context;

import com.zq.service.dispatcher.config.ServiceKey;
import com.zq.service.dispatcher.convertor.TypeConvertor;
import com.zq.service.dispatcher.service.interceptor.AroundAtomInterceptor;
import com.zq.service.dispatcher.service.interceptor.AtomInterceptor;
import com.zq.service.dispatcher.note.*;
import com.zq.service.dispatcher.pools.DefaultThreadPool;
import com.zq.service.dispatcher.reflect.ClassUtils;
import com.zq.service.dispatcher.service.AsyncAtomService;
import com.zq.service.dispatcher.service.AtomService;
import com.zq.service.dispatcher.service.CoreAtomService;
import com.zq.service.dispatcher.service.InterceptAtomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @program: service-dispatcher
 * @description: 基于注解服务上下文
 * @author: zhouqi1
 * @create: 2018-06-28 10:14
 **/
public class CoreServiceContext implements ServiceContext {

    private Logger logger = LoggerFactory.getLogger(CoreServiceContext.class);

    /**
     * 服务缓存
     */
    private Map<String, AtomService> atomServiceMap;

    public CoreServiceContext(DefinitionContext definitionContext) {
        this.atomServiceMap = new ConcurrentHashMap<>();
        loadServices(definitionContext);
    }

    /**
     * definition转换成Service
     * @param definitionContext
     */
    public void loadServices(DefinitionContext definitionContext){
        Set<String> serviceKeys = definitionContext.getServiceKeys();
        if(serviceKeys != null && !serviceKeys.isEmpty()){
            for (String serviceKey : serviceKeys){
                ServiceDefinition serviceDefinition = definitionContext.getServiceDefinition(serviceKey);
                AtomService atomService = convertService(serviceDefinition);

                List<InterceptorDefinition> interceptorDefinitions = definitionContext.getInterceptorChain(serviceKey);
                if(interceptorDefinitions != null && !interceptorDefinitions.isEmpty()){
                    List<AtomInterceptor> interceptors = interceptorDefinitions.stream().map(interceptorDefinition -> convertInterceptor(interceptorDefinition)).collect(Collectors.toList());
                    atomService = new InterceptAtomService(atomService, interceptors);
                }

                if(serviceDefinition.isAsync()){
                    atomService = new AsyncAtomService(atomService);
                }
                atomServiceMap.put(serviceKey, atomService);
            }
        }
    }

    /**
     * serviceDefinition z转换为Service
     * @param serviceDefinition
     * @return
     */
    private AtomService convertService(ServiceDefinition serviceDefinition) {
        Class typeConvertorClazz = serviceDefinition.getConvertor();
        TypeConvertor typeConvertor =  null;
        if(typeConvertorClazz != null) {
            typeConvertor =  (TypeConvertor) ClassUtils.newBean(typeConvertorClazz);
        }
        return new CoreAtomService(serviceDefinition.getTarget(), serviceDefinition.getMethod(), typeConvertor);
    }

    /**
     * interceptorDefinition 转换为Interceptor
     * @param interceptorDefinition
     * @return
     */
    private AtomInterceptor convertInterceptor(InterceptorDefinition interceptorDefinition) {
        Class typeConvertorClazz = interceptorDefinition.getConvertor();
        TypeConvertor typeConvertor = null;
        if(typeConvertorClazz != null){
            typeConvertor = (TypeConvertor) ClassUtils.newBean(typeConvertorClazz);
        }
        return new AroundAtomInterceptor(interceptorDefinition.getTarget(), interceptorDefinition.getMethod(), typeConvertor);
    }

    @Override
    public AtomService find(ServiceKey serviceKey) {
        String key = serviceKey.toServiceKey();
        if(!atomServiceMap.containsKey(key)){
            key =  ServiceKey.newBuilder()
                    .group(serviceKey.getGroup())
                    .name(ServiceKey.DEFAULT_NAME)
                    .version(serviceKey.getVersion())
                    .build()
                    .toServiceKey();
        }
        return atomServiceMap.get(key);
    }
}
