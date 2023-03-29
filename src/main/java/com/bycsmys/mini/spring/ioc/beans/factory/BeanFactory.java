package com.bycsmys.mini.spring.beans.factory;

import com.bycsmys.mini.spring.beans.BeansException;

public interface BeanFactory {

    Object getBean(String beanName) throws BeansException;

    Boolean containsBean(String name);

    boolean isSingleton(String name);

    boolean isPrototype(String name);

    Class<?> getType(String name);
}
