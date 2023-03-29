package com.bycsmys.mini.spring.ioc.beans.factory;

import com.bycsmys.mini.spring.ioc.beans.BeansException;

public interface BeanFactory {

    Object getBean(String beanName) throws BeansException;

    Boolean containsBean(String name);

    boolean isSingleton(String name);

    boolean isPrototype(String name);

    Class<?> getType(String name);
}
