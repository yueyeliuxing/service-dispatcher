package com.zq.service.dispatcher.note;

/**
 * @program: service-dispatcher
 * @description: 服务拦截器
 * @author: zhouqi1
 * @create: 2018-05-30 11:49
 **/

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.TYPE})
@Retention(RUNTIME)
@Documented
public @interface ServiceInterceptor {
}
