package com.zq.service.dispatcher.simple.handler.interceptors;


import com.zq.service.dispatcher.interceptor.AtomInterceptorContext;
import com.zq.service.dispatcher.note.AroundInterceptor;
import com.zq.service.dispatcher.note.PatternItem;
import com.zq.service.dispatcher.note.ServiceInterceptor;
import com.zq.service.dispatcher.simple.handler.convertor.EmailVerifyParam2IpKeyConvertor;
import com.zq.service.dispatcher.simple.handler.ds.GroupDs;
import com.zq.service.dispatcher.simple.handler.ds.NameDs;
import org.springframework.stereotype.Component;

/**
 * @program: service-dispatcher
 * @description: 验证码拦截器
 * @author: zhouqi1
 * @create: 2018-06-01 14:08
 **/
@Component
@ServiceInterceptor
public class GraphicCaptchaInterceptor {


    @AroundInterceptor({@PatternItem(group = GroupDs.SMS_GRAPHIC_CAPTACHA, name = NameDs.DEFAULT, convertor = EmailVerifyParam2IpKeyConvertor.class)})
    public Object interceptor(Object param, AtomInterceptorContext interceptorContext) throws Exception {

        Object obj = interceptorContext.dispatch();


        return obj;
    }
}
