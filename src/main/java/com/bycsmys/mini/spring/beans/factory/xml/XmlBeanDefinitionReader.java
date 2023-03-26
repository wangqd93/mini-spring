package com.bycsmys.mini.spring.beans.factory.xml;

import com.bycsmys.mini.spring.beans.AutowireCapableBeanFactory;
import com.bycsmys.mini.spring.beans.factory.config.*;
import com.bycsmys.mini.spring.beans.Resource;
import com.bycsmys.mini.spring.beans.factory.support.SimpleBeanFactory;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class XmlBeanDefinitionReader {

    AutowireCapableBeanFactory autowireCapableBeanFactory;

    public XmlBeanDefinitionReader(AutowireCapableBeanFactory autowireCapableBeanFactory) {
        this.autowireCapableBeanFactory = autowireCapableBeanFactory;

    }

    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanId = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");

            BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);

            // 解析construct-arg
            List<Element> constructElements = element.elements("constructor-arg");
            ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
            for (Element e : constructElements) {
                String type = e.attributeValue("type");
                String name = e.attributeValue("name");
                String value = e.attributeValue("value");
                constructorArgumentValues.addGenericArgumentValue(new ConstructorArgumentValue(type, name, value));
            }
            beanDefinition.setConstructArgumentValues(constructorArgumentValues);

            //  解析 propertyValues 和 ref
            List<String> refs = new ArrayList<>();
            List<Element> propertyElements = element.elements("property");
            PropertyValues propertyValues = new PropertyValues();
            for (Element e : propertyElements) {
                String type = e.attributeValue("type");
                String name = e.attributeValue("name");
                String value = e.attributeValue("value");

                String ref = e.attributeValue("ref");
                String refValue = "";
                boolean isRef = false;
                if (value != null && !value.equals("")) {
                    isRef = false;
                    refValue = value;
                } else if (ref != null && !ref.equals("")) {
                    isRef = true;
                    refValue = ref;
                    refs.add(ref);
                }
                propertyValues.addPropertyValue(new PropertyValue(type, name, refValue, isRef));
            }

            beanDefinition.setPropertyValues(propertyValues);

            String[] refArray = refs.toArray(new String[0]);
            beanDefinition.setDependsOn(refArray);

            this.autowireCapableBeanFactory.registerBeanDefinition(beanId, beanDefinition);

        }
    }
}
