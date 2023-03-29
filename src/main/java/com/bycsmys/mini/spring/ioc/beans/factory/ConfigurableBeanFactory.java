package com.bycsmys.mini.spring.beans.factory;

import com.bycsmys.mini.spring.beans.BeanFactoryPostProcessor;
import com.bycsmys.mini.spring.beans.factory.config.SingletonBeanRegistry;

public interface ConfigurableBeanFactory extends SingletonBeanRegistry, BeanFactory {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanFactoryPostProcessor beanFactoryPostProcessor);

    int getBeanPostProcessorCount();

    void registerDependentBean(String beanName, String dependentBeanName);

    String[] getDependentBeans(String beanName);

    String[] getDependenciesForBean(String beanName);

}
