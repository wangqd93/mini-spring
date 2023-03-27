package com.bycsmys.mini.spring.beans;

import com.bycsmys.mini.spring.beans.factory.BeanFactory;

public interface BeanFactoryPostProcessor {

    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;


    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;


    void setBeanFactory(BeanFactory beanFactory);


}
