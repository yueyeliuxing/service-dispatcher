package com.zq.service.dispatcher.exception;

/**
 * @program: service-dispatcher
 * @description: 服务反射执行异常
 * @author: zhouqi1
 * @create: 2018-06-29 10:06
 **/
public class ServiceReflectException extends RuntimeException {

    public ServiceReflectException() {
    }

    public ServiceReflectException(String message) {
        super(message);
    }

    public ServiceReflectException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceReflectException(Throwable cause) {
        super(cause);
    }

    public ServiceReflectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
