package com.zq.service.dispatcher.note;

import com.zq.service.dispatcher.convertor.DefaultTypeConvertor;
import com.zq.service.dispatcher.interceptor.ArrangePriority;

import java.lang.annotation.*;

/**
 * @program: service-dispatcher
 * @description: 模式匹配
 * @author: zhouqi1
 * @create: 2018-06-01 16:38
 **/
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PatternItem {

    String group();

    String name();

    String version() default "0.0.0.1";

    Class convertor() default DefaultTypeConvertor.class;

    boolean async() default false;

    byte priority() default ArrangePriority.LOW;
}
