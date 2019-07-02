package com.zq.service.dispatcher.simple.handler.handler;


import com.zq.service.dispatcher.note.PatternItem;
import com.zq.service.dispatcher.note.ServiceDispatcher;
import com.zq.service.dispatcher.note.ServiceHandler;
import com.zq.service.dispatcher.simple.handler.ds.GroupDs;
import com.zq.service.dispatcher.simple.handler.ds.NameDs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: service-dispatcher
 * @description: 默认邮件验证码处理器
 * @author: zhouqi1
 * @create: 2018-06-22 17:03
 **/
@Component
@ServiceDispatcher
public class DefaultEmailCaptchaHandler {

    @ServiceHandler({@PatternItem(group = GroupDs.EMAIL_CAPTACHA, name = NameDs.DEFAULT)})
    public void sendEmailCaptcha(Object emailCaptchaParam) {

    }

    @ServiceHandler({@PatternItem(group = GroupDs.VERIFY_EMAIL_CAPTACHA, name = NameDs.DEFAULT)})
    public void validateEmailCaptcha(Object emailCaptchaVerifyParam) {


    }
}
