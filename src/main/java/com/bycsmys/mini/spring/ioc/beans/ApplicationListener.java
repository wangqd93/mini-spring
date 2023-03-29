package com.bycsmys.mini.spring.ioc.beans;

import java.util.EventListener;

public class ApplicationListener implements EventListener {

    void onApplicationEvent(ApplicationEvent event) {
        System.out.println(event.toString());

    }
}
