package com.bycsmys.mini.spring.ioc.beans.factory.xml;

import com.bycsmys.mini.spring.ioc.beans.Resource;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.Iterator;

public class ClassPathXmlResource implements Resource {
    Document document;
    Element rootElement;
    Iterator<Element> elementIterator;

    public ClassPathXmlResource(String fileName) {
        try {
            SAXReader saxReader = new SAXReader();
            URL resource = this.getClass().getClassLoader().getResource(fileName);
            this.document = saxReader.read(resource);
            this.rootElement = document.getRootElement();
            this.elementIterator = this.rootElement.elementIterator();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public boolean hasNext() {
        return this.elementIterator.hasNext();
    }

    @Override
    public Object next() {
        return this.elementIterator.next();
    }
}
