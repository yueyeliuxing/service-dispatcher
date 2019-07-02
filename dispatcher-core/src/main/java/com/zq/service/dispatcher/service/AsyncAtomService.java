package com.zq.service.dispatcher.service;

import com.zq.service.dispatcher.pools.DefaultThreadPool;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Executor;

/**
 * @program: service-dispatcher
 * @description: 异步服务
 * @author: zhouqi1
 * @create: 2018-06-28 11:01
 **/
public class AsyncAtomService extends AbstractAtomService implements AtomService {

    private AtomService atomService;

    private Executor executor;

    public AsyncAtomService(AtomService atomService) {
        this.atomService = atomService;
        this.executor = new DefaultThreadPool();
    }

    @Override
    public Object service(Object param) {
        executor.execute(()->{
            try{
                atomService.service(param);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        return null;
    }

    public AtomService getAtomService() {
        return atomService;
    }

    public void setAtomService(AtomService atomService) {
        this.atomService = atomService;
    }
}
