package com.bycsmys.mini.spring.ioc.beans;

import com.bycsmys.mini.spring.ioc.beans.factory.ConfigurableBeanFactory;
import com.bycsmys.mini.spring.ioc.beans.factory.ConfigurableListableBeanFactory;
import com.bycsmys.mini.spring.ioc.beans.factory.ListableBeanFactory;
import com.bycsmys.mini.spring.ioc.core.env.Environment;
import com.bycsmys.mini.spring.ioc.core.env.EnvironmentCapable;

public interface ApplicationContext extends EnvironmentCapable, ListableBeanFactory, ConfigurableBeanFactory,ApplicationEventPublisher {


    String getApplicationName();

    long getStartupDate();

    ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

    void setEnvironment(Environment environment);

    Environment getEnvironment();

    void addBeanFactoryPostProcessor(BeanFactoryPostProcessor beanFactoryPostProcessor);

    void refresh() throws BeansException, IllegalStateException;

    void close();

    boolean isActive();

}
