package com.bycsmys.mini.spring.ioc.model;

import com.bycsmys.mini.spring.ioc.beans.factory.annptation.Autowired;

public class BaseService {

    @Autowired
    private BaseBaseService bbs;

    public BaseService() {
    }

    public BaseBaseService getBbs() {
        return bbs;
    }

    public void setBbs(BaseBaseService bbs) {
        this.bbs = bbs;
    }

    public void sayHello() {
        System.out.println("Base Service says Hello");
    }
}
