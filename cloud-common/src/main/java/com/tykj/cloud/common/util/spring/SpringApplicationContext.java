package com.tykj.cloud.common.util.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * spring 工具类
 *
 * @author lukew
 * @eamil 13507615840@163.com
 * @create 2018-08-06 22:00
 **/
public class SpringApplicationContext implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 获取 applicationContext
     *
     * @return
     */
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 按类型获取bean
     *
     * @param requiredType
     * @param <T>
     * @return
     */
    public <T> T getObject(Class<T> requiredType) {

        return applicationContext.getBean(requiredType);
    }

    /**
     * 按名称获取bean
     *
     * @param name
     * @return
     */
    public Object getObject(String name) {
        return this.applicationContext.getBean(name);
    }

    /**
     * 按名称和类型获取bean
     *
     * @param name
     * @param requiredType
     * @param <T>
     * @return
     */
    public <T> T getObject(String name, Class<T> requiredType) {
        return applicationContext.getBean(name, requiredType);
    }
}
