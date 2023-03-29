package com.bycsmys.mini.spring.mvc.web;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.Iterator;

public class ClassPathXmlResource implements Resource {

    Document document;

    Element rootElement;

    Iterator<Element> elementIterator;

    public ClassPathXmlResource(URL xmlPath) {
        try {
            SAXReader saxReader = new SAXReader();
            this.document = saxReader.read(xmlPath);
            this.rootElement = document.getRootElement();
            this.elementIterator = rootElement.elementIterator();
        } catch (Exception e) {
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
