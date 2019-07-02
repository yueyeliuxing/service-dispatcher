package com.zq.service.dispatcher.pools;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: service-dispatcher
 * @description: 默认线程池
 * @author: zhouqi1
 * @create: 2018-06-08 17:09
 **/
public class DefaultThreadPool implements Executor{

    private ExecutorService defaultExecutorService;

    public DefaultThreadPool() {
        defaultExecutorService = new ThreadPoolExecutor(
                5,
                15,
                100,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(1000),
                new ThreadFactory(){
                    private AtomicInteger threadindex = new AtomicInteger(0);
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setDaemon(true);
                        thread.setName(String.format("service-dispatcher-default-thread-%s", threadindex.incrementAndGet()));
                        return thread;
                    }
                });
    }

    @Override
    public void execute(Runnable command) {
        defaultExecutorService.execute(command);
    }
}
