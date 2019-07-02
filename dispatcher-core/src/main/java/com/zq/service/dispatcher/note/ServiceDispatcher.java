package com.zq.service.dispatcher.note;

/**
 * @program: service-dispatcher
 * @description: 服务转发 注解
 * @author: zhouqi1
 * @create: 2018-05-30 11:49
 **/

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceDispatcher {
}
