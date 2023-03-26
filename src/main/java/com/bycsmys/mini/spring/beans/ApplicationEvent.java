package com.bycsmys.mini.spring.beans;

import java.util.EventObject;

public class ApplicationEvent extends EventObject {

    private static final long serialversionUID = 1L;


    public ApplicationEvent(Object o) {
        super(o);
    }
}
