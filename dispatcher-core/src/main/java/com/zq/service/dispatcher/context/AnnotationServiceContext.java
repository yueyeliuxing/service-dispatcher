package com.zq.service.dispatcher.context;

import com.zq.service.dispatcher.config.ServiceKey;
import com.zq.service.dispatcher.convertor.TypeConvertor;
import com.zq.service.dispatcher.interceptor.AroundAtomInterceptor;
import com.zq.service.dispatcher.interceptor.AtomInterceptor;
import com.zq.service.dispatcher.note.*;
import com.zq.service.dispatcher.pools.DefaultThreadPool;
import com.zq.service.dispatcher.reflect.ClassUtils;
import com.zq.service.dispatcher.service.AsyncAtomService;
import com.zq.service.dispatcher.service.AtomService;
import com.zq.service.dispatcher.service.CoreAtomService;
import com.zq.service.dispatcher.service.InterceptAtomService;
import com.zq.service.dispatcher.convertor.ConvertorWapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: service-dispatcher
 * @description: 基于注解服务上下文
 * @author: zhouqi1
 * @create: 2018-06-28 10:14
 **/
public class AnnotationServiceContext extends AbstractServiceContext implements ServiceContext {

    private Logger logger = LoggerFactory.getLogger(AnnotationServiceContext.class);

    private ApplicationContext applicationContext;

    public AnnotationServiceContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        loadServices();
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void loadServices(){
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
                                TypeConvertor typeConvertor =  null;
                                if(typeConvertorClazz != null) {
                                    typeConvertor =  (TypeConvertor) ClassUtils.newBean(typeConvertorClazz);
                                }
                                AtomService atomService = new CoreAtomService(handler, method, typeConvertor);
                                if(patternItem.async()){
                                    atomService = new AsyncAtomService(atomService, new DefaultThreadPool());
                                }
                                logger.info("加载key->{}服务", dispatcherKey);
                                serviceStorage.register(dispatcherKey, atomService);

                            }
                        }
                    }
                }
            }
        }

        Map<String, Object> interceptorMap =  applicationContext.getBeansWithAnnotation(ServiceInterceptor.class);
        if(interceptorMap != null && !interceptorMap.isEmpty()){
            for(String beanName : interceptorMap.keySet()){
                Object consumer = interceptorMap.get(beanName);
                Class<?> clazz = consumer.getClass();
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
                                if(!serviceStorage.contains(dispatcherKey)){
                                    continue;
                                }

                                Class typeConvertorClazz = patternItem.convertor();
                                TypeConvertor typeConvertor = null;
                                if(typeConvertorClazz != null){
                                    typeConvertor = (TypeConvertor) ClassUtils.newBean(typeConvertorClazz);
                                }
                                AtomInterceptor atomInterceptor = new AroundAtomInterceptor(consumer, method, typeConvertor, priority);
                                AtomService atomService = serviceStorage.find(dispatcherKey);
                                InterceptAtomService interceptAtomService = null;
                                if(atomService instanceof CoreAtomService) {
                                    interceptAtomService = new InterceptAtomService(atomService);
                                    interceptAtomService.addInterceptor(atomInterceptor);
                                    serviceStorage.register(dispatcherKey, interceptAtomService);
                                }else if(atomService instanceof InterceptAtomService) {
                                    interceptAtomService = (InterceptAtomService)atomService;
                                    interceptAtomService.addInterceptor(atomInterceptor);
                                    serviceStorage.register(dispatcherKey, interceptAtomService);
                                }else if(atomService instanceof AsyncAtomService){
                                    AsyncAtomService asyncAtomService = (AsyncAtomService)atomService;
                                    AtomService targetService = asyncAtomService.getAtomService();
                                    if(targetService instanceof CoreAtomService) {
                                        interceptAtomService = new InterceptAtomService(targetService);
                                    }else if(targetService instanceof InterceptAtomService) {
                                        interceptAtomService = (InterceptAtomService)targetService;
                                    }
                                    interceptAtomService.addInterceptor(atomInterceptor);
                                    asyncAtomService.setAtomService(interceptAtomService);
                                    serviceStorage.register(dispatcherKey, asyncAtomService);
                                }
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
