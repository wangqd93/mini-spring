package com.bycsmys.mini.spring.beans;

public class ContextRefreshEvent extends ApplicationEvent {

    private static final long serialversionUID = 1L;

    public ContextRefreshEvent(Object o) {
        super(o);
    }

    public String toString() {
        return this.msg;
    }
}
