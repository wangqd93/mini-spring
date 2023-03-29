package com.bycsmys.mini.spring.mvc.test;

import com.bycsmys.mini.spring.mvc.web.RequestMapping;

public class HelloWorldBean {

    @RequestMapping(value = "test")
    public String doGet() {
        return "hello world";
    }

    public String doPost() {
        return "hello world";
    }
}





