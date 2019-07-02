package com.zq.service.dispatcher.reflect;

import java.lang.reflect.InvocationTargetException;

/**
 * @program: service-dispatcher
 * @description: 反射工具类
 * @author: zhouqi1
 * @create: 2018-06-13 19:21
 **/
public class ClassUtils {

    /**
     *
     *   获取类型实例
     * @author zhouqi
     * @date 2018/6/13 19:23
     * @param clazz
     * @return
     */
    public static Object newBean(Class clazz){
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     *   获取类型实例
     * @author zhouqi
     * @date 2018/6/13 19:23
     * @param clazz
     * @param params 构造器参数
     * @return
     */
    public static Object newBean(Class clazz, Object... params){
        try {
            Class[] types = new Class[params.length];
            for(int i = 0; i<params.length;i++){
                types[i] = params[i].getClass();
            }
            return clazz.getConstructor(types).newInstance(params);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
}
