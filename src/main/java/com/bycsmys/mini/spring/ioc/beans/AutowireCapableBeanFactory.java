package com.bycsmys.mini.spring.beans;

import com.bycsmys.mini.spring.beans.factory.BeanFactory;

public interface AutowireCapableBeanFactory extends BeanFactory {


    int AUTOWIRE_NO = 0;
    int AUTOWIRE_BY_NAME = 1;
    int AUTOWIRE_BY_TYPE = 2;

    Object applyBeanPostProcessorBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    Object applyBeanPostProcessorAfterInitialization(Object existingBean, String beanName) throws BeansException;

}