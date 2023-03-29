package com.bycsmys.mini.spring.ioc.beans.factory;

import com.bycsmys.mini.spring.ioc.beans.BeansException;

import java.util.Map;

public interface ListableBeanFactory {

    boolean containsBeanDefinition(String beanName);

    int getBeanDefinitionCount();


    String[] getBeanDefinitionNames();


    String[] getBeanNamesForType(Class<?> type);

    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;


}
