package com.bycsmys.mini.spring.beans;

public interface ApplicationEventPublisher {

    void publishEvent(ApplicationEvent event);

    void addApplicationListener(ApplicationListener listener);
}
