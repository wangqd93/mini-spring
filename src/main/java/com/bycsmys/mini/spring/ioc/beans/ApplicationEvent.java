package com.bycsmys.mini.spring.ioc.beans;

import java.util.EventObject;

public class ApplicationEvent extends EventObject {

    private static final long serialversionUID = 1L;

    protected String msg = null;


    public ApplicationEvent(Object o) {
        super(o);
        this.msg = o.toString();
    }



}
