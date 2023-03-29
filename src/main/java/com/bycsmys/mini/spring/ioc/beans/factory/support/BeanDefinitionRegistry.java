package com.bycsmys.mini.spring.ioc.beans.factory.support;

import com.bycsmys.mini.spring.ioc.beans.factory.config.BeanDefinition;

public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String name, BeanDefinition definition);

    void removeBeanDefinition(String name);

    BeanDefinition getBeanDefinition(String name);

    boolean containsBeanDefinition(String name);
}
