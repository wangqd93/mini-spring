package com.bycsmys.mini.spring.ioc.beans;

public interface ApplicationEventPublisher {

    void publishEvent(ApplicationEvent event);

    void addApplicationListener(ApplicationListener listener);
}
