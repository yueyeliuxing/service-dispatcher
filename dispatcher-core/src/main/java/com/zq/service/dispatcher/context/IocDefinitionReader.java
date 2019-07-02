package com.zq.service.dispatcher.context;


import com.zq.service.dispatcher.config.ServiceKey;
import com.zq.service.dispatcher.note.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: service-dispatcher
 * @description: 注解 读取器
 * @author: zhouqi1
 * @create: 2019-07-02 19:29
 **/
public class IocDefinitionReader implements DefinitionReader<ApplicationContext> {

    private Logger logger = LoggerFactory.getLogger(IocDefinitionReader.class);

    private DefinitionContext definitionContext;

    public IocDefinitionReader(DefinitionContext definitionContext) {
        this.definitionContext = definitionContext;
    }

    @Override
    public void loadDefinitions(ApplicationContext applicationContext) {
        Environment environment = applicationContext.getEnvironment();

        Map<String, Object> handlerMap =  applicationContext.getBeansWithAnnotation(ServiceDispatcher.class);
        if(handlerMap != null && !handlerMap.isEmpty()){
            for(String beanName : handlerMap.keySet()){
                Object handler = handlerMap.get(beanName);
                Class<?> clazz = handler.getClass();
                Method[] methods = clazz.getDeclaredMethods();
                if(methods != null && methods.length > 0){
                    for(Method method : methods){
                        method.setAccessible(true);
                        if(method.isAnnotationPresent(ServiceHandler.class)){
                            ServiceHandler serviceHandler = method.getAnnotation(ServiceHandler.class);
                            PatternItem[] patternItems = serviceHandler.value();
                            if(patternItems == null || patternItems.length == 0){
                                continue;
                            }
                            for(PatternItem patternItem : patternItems){
                                String group = patternItem.group();
                                String name = patternItem.name();
                                String version = patternItem.version();

                                if(isPatternRule(group)){
                                    group = patternReplace(environment, group);
                                }

                                if(isPatternRule(name)){
                                    name = patternReplace(environment, name);
                                }

                                String dispatcherKey = ServiceKey.newBuilder()
                                        .group(group).name(name).version(version).build().toServiceKey();

                                Class typeConvertorClazz = patternItem.convertor();
                                logger.info("加载key->{}服务:{}", dispatcherKey,  handler.getClass().getName()+method.getName());
                                definitionContext.addServiceDefinition(dispatcherKey, new ServiceDefinition(dispatcherKey, typeConvertorClazz, patternItem.async(), handler, method));
                            }
                        }
                    }
                }
            }
        }

        Map<String, Object> interceptorMap =  applicationContext.getBeansWithAnnotation(ServiceInterceptor.class);
        if(interceptorMap != null && !interceptorMap.isEmpty()){
            for(String beanName : interceptorMap.keySet()){
                Object interceptor = interceptorMap.get(beanName);
                Class<?> clazz = interceptor.getClass();
                Method[] methods = clazz.getDeclaredMethods();
                if(methods != null && methods.length > 0){
                    for(Method method : methods){
                        method.setAccessible(true);
                        if(method.isAnnotationPresent(AroundInterceptor.class)){
                            AroundInterceptor aroundInterceptor = method.getAnnotation(AroundInterceptor.class);
                            PatternItem[] patternItems = aroundInterceptor.value();
                            if(patternItems == null || patternItems.length == 0){
                                continue;
                            }
                            for(PatternItem patternItem : patternItems){
                                String group = patternItem.group();
                                String name = patternItem.name();
                                String version = patternItem.version();

                                if(isPatternRule(group)){
                                    group = patternReplace(environment, group);
                                }

                                if(isPatternRule(name)){
                                    name = patternReplace(environment, name);
                                }

                                String dispatcherKey = ServiceKey.newBuilder()
                                        .group(group).name(name).version(version).build().toServiceKey();
                                byte priority = patternItem.priority();
                                Class typeConvertorClazz = patternItem.convertor();
                                logger.info("加载key->{}拦截器：{}", dispatcherKey, interceptor.getClass().getName()+method.getName());
                                definitionContext.addInterceptorDefinition(dispatcherKey, new InterceptorDefinition(dispatcherKey, priority, typeConvertorClazz, interceptor, method));
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 是否包含正则
     * @param source
     * @return
     */
    private boolean isPatternRule(String source){
        String regex = "\\$\\{(.+?)\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        return matcher.find();
    }

    /**
     * 正则替换
     * @param environment
     * @param source
     * @return
     */
    private String patternReplace(Environment environment, String source){
        String regex = "\\$\\{(.+?)\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            String key = matcher.group(1);// 键名
            String value = environment.getProperty(key);// 键值
            source = source.replace("${"+key+"}", value);
        }
        return source;
    }
}
