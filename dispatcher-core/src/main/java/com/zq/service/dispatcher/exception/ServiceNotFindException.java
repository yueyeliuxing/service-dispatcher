package com.zq.service.dispatcher.exception;

/**
 * @program: service-dispatcher
 * @description: 服务没找到异常
 * @author: zhouqi1
 * @create: 2018-06-29 10:06
 **/
public class ServiceNotFindException extends RuntimeException {

    public ServiceNotFindException() {
    }

    public ServiceNotFindException(String message) {
        super(message);
    }

    public ServiceNotFindException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceNotFindException(Throwable cause) {
        super(cause);
    }

    public ServiceNotFindException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
