package com.zq.service.dispatcher.service;

import com.zq.service.dispatcher.service.interceptor.AtomInterceptor;
import com.zq.service.dispatcher.service.interceptor.AtomInterceptorContext;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @program: service-dispatcher
 * @description: 拦截服务
 * @author: zhouqi1
 * @create: 2018-06-28 11:49
 **/
public class InterceptAtomService extends AbstractAtomService implements AtomService, AtomInterceptorContext {

    private ThreadLocal<AtomicInteger> currentIndex;

    private ThreadLocal<Object> paramContext;

    private volatile List<AtomInterceptor> interceptors;

    private AtomService atomService;

    private volatile boolean started = false;

    public InterceptAtomService(AtomService atomService) {
        this(atomService, new ArrayList<>());
    }

    public InterceptAtomService(AtomService atomService, List<AtomInterceptor> interceptors) {
        currentIndex = new ThreadLocal<>();
        paramContext = new ThreadLocal<>();
        this.interceptors = interceptors;
        this.atomService = atomService;
    }

    public void addInterceptor(AtomInterceptor interceptor){
        interceptors.add(interceptor);
    }

    @Override
    public Object service(Object param) {
        currentIndex.set(new AtomicInteger(0));
        paramContext.set(param);
        return dispatch();
    }

    @Override
    public Object getParam() {
        return paramContext.get();
    }

    @Override
    public Object dispatch() {
        if (interceptors != null && !interceptors.isEmpty()) {
            if(currentIndex.get().get() < interceptors.size()){
                AtomInterceptor interceptor = interceptors.get(currentIndex.get().getAndIncrement());
                Object obj = interceptor.intercept(this);
                if(obj != null){
                    return  obj;
                }
            }
        }
        return atomService.service(getParam());
    }
}
