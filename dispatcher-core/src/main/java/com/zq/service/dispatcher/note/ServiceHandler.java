package com.zq.service.dispatcher.note;

/**
 * @program: service-dispatcher
 * @description:  服务执行器注解
 * @author: zhouqi1
 * @create: 2018-05-30 11:49
 **/


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.METHOD})
@Retention(RUNTIME)
@Documented
public @interface ServiceHandler {

    PatternItem[] value();
}
