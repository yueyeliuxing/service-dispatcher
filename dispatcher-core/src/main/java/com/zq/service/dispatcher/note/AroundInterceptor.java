package com.zq.service.dispatcher.note;

import java.lang.annotation.*;

/**
 * @program: service-dispatcher
 * @description: 系统处理
 * @author: zhouqi1
 * @create: 2018-05-30 15:04
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AroundInterceptor {

    PatternItem[] value();
}
