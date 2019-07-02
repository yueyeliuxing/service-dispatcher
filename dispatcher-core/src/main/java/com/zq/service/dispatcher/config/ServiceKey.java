package com.zq.service.dispatcher.config;

import lombok.Getter;
import lombok.ToString;

/**
 * @program: service-dispatcher
 * @description: 分派key
 * @author: zhouqi1
 * @create: 2018-06-08 14:25
 **/
@Getter
@ToString
public class ServiceKey {

    public static final String DEFAULT_NAME = "default-default";

    private String group;

    private String name;

    private String version;

    private ServiceKey(String group, String name, String version) {
        this.group = group;
        this.name = name;
        this.version = version;
    }

    public static DispatcherKeyBuilder newBuilder(){
        return new DispatcherKeyBuilder();
    }

    public static class DispatcherKeyBuilder {

        private String group;

        private String name;

        private String version;

        public DispatcherKeyBuilder group(String group) {
            this.group = group;
            return this;
        }

        public DispatcherKeyBuilder name(String name) {
            this.name = name;
            return this;
        }

        public DispatcherKeyBuilder version(String version) {
            this.version = version;
            return this;
        }

        public ServiceKey build(){
            return new ServiceKey(group, name, version);
        }
    }

    public String toServiceKey(){
        return String.format("%s_%s_%s", group, name, version == null ? "0.0.0.1" : version);
    }
}
