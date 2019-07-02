package com.zq.service.dispatcher.config;

import lombok.Getter;

/**
 * @program: service-dispatcher
 * @description: 分派上下文
 * @author: zhouqi1
 * @create: 2018-06-08 16:50
 **/
@Getter
public class ServiceConfig {

    private ServiceKey serviceKey;

    private Object context;

    private ServiceConfig(ServiceKey serviceKey, Object context) {
        this.serviceKey = serviceKey;
        this.context = context;
    }

    public static DispatcherContextBuilder newBuilder(){
        return new DispatcherContextBuilder();
    }

    public static class DispatcherContextBuilder{

        private ServiceKey.DispatcherKeyBuilder dispatcherKeyBuilder;

        private Object context;

        public DispatcherContextBuilder() {
            this.dispatcherKeyBuilder = ServiceKey.newBuilder();
        }

        public DispatcherContextBuilder group(String group){
            dispatcherKeyBuilder.group(group);
            return this;
        }

        public DispatcherContextBuilder name(String name){
            dispatcherKeyBuilder.name(name);
            return this;
        }

        public DispatcherContextBuilder version(String version){
            dispatcherKeyBuilder.version(version);
            return this;
        }

        public DispatcherContextBuilder context(Object context){
            this.context = context;
            return this;
        }

        public ServiceConfig build(){
            return new ServiceConfig(dispatcherKeyBuilder.build(), this.context);
        }

    }
}
