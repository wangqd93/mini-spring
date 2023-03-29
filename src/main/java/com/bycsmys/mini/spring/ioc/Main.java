package com.bycsmys.mini.spring.ioc;

import com.bycsmys.mini.spring.ioc.beans.BeansException;
import com.bycsmys.mini.spring.ioc.beans.factory.xml.ClassPathXmlApplicationContext;
import com.bycsmys.mini.spring.ioc.model.AServiceRef;

public class Main {

    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        AServiceRef aServiceRef = (AServiceRef) context.getBean("aserviceref");
        aServiceRef.sayHello();
    }
}
