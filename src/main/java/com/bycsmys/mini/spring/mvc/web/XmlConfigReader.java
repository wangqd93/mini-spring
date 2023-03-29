package com.bycsmys.mini.spring.mvc.web;

import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

public class XmlConfigReader {


    public XmlConfigReader() {
    }

    public Map<String, MappingValue> loadConfig(Resource resource) {
        Map<String, MappingValue> mappings = new HashMap<>();
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanId = element.attributeValue("id");
            String clz = element.attributeValue("class");
            String value = element.attributeValue("value");

            mappings.put(beanId, new MappingValue(beanId, clz, value));
        }
        return mappings;
    }
}
