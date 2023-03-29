package com.bycsmys.mini.spring;

import com.bycsmys.mini.spring.beans.BeansException;
import com.bycsmys.mini.spring.beans.factory.xml.ClassPathXmlApplicationContext;
import com.bycsmys.mini.spring.model.AServiceRef;

public class Main {

    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        AServiceRef aServiceRef = (AServiceRef) context.getBean("aserviceref");
        aServiceRef.sayHello();
    }
}
