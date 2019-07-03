package com.zq.service.dispatcher.starter;

import com.zq.service.dispatcher.AnnotationServiceDispatcher;
import com.zq.service.dispatcher.ServiceDispatcher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @program: service-dispatcher
 * @description: 自动配置
 * @author: zhouqi1
 * @create: 2019-07-02 20:49
 **/
@Configuration
@ConditionalOnClass(AnnotationServiceDispatcher.class)
public class DispatcherAutoConfigure {

    @Bean
    @ConditionalOnMissingBean
    ServiceDispatcherFactoryBean serviceDispatcher (){
        return  new ServiceDispatcherFactoryBean();
    }
}
