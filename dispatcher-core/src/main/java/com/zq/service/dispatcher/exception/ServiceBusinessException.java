package com.zq.service.dispatcher.exception;

/**
 * @program: service-dispatcher
 * @description: 服务业务异常
 * @author: zhouqi1
 * @create: 2018-06-29 10:06
 **/
public class ServiceBusinessException extends RuntimeException {

    public ServiceBusinessException() {
    }

    public ServiceBusinessException(String message) {
        super(message);
    }

    public ServiceBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceBusinessException(Throwable cause) {
        super(cause);
    }

    public ServiceBusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
