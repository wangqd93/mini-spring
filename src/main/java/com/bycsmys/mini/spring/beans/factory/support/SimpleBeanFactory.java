package com.bycsmys.mini.spring.beans.factory.support;

import com.bycsmys.mini.spring.beans.factory.config.BeanDefinition;
import com.bycsmys.mini.spring.beans.BeansException;
import com.bycsmys.mini.spring.beans.factory.BeanFactory;
import com.bycsmys.mini.spring.beans.factory.config.ConstructorArgumentValue;
import com.bycsmys.mini.spring.beans.factory.config.ConstructorArgumentValues;
import com.bycsmys.mini.spring.beans.factory.config.PropertyValue;
import com.bycsmys.mini.spring.beans.factory.config.PropertyValues;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    private Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>();


    private List<String> beanDefinitionNames = new ArrayList<>();

    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(name, beanDefinition);
        this.beanDefinitionNames.add(name);

        if (!beanDefinition.isLazyInit()) {
            try {
                getBean(name);
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void removeBeanDefinition(String name) {
        this.beanDefinitionMap.remove(name);
        this.beanDefinitionNames.remove(name);
        this.removeSingleton(name);
    }

    public SimpleBeanFactory() {
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        Object singleton = this.getSingleton(beanName);
        if (singleton == null) {

            // 从半初始化对象池获取
            singleton = this.earlySingletonObjects.get(beanName);

            if (singleton == null) {
                BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
                singleton = createBean(beanDefinition);
                this.registerSingleton(beanName, singleton);

                // 预留beanpostprocessor位置
                // step 1: postProcessBeforeInitialization
                // step 2: afterPropertiesSet
                // step 3: init-method
                // step 4: postProcessAfterInitialization
            }
        }
        return singleton;
    }


    private Object createBean(BeanDefinition beanDefinition) {
        Class<?> clz = null;
        try {
            clz = Class.forName(beanDefinition.getClassName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // 创建对象
        Object obj = doCreateBean(beanDefinition);
        this.earlySingletonObjects.put(beanDefinition.getId(), obj);

        // 处理property
        handleProperties(beanDefinition, clz, obj);

        return obj;
    }

    private Object doCreateBean(BeanDefinition beanDefinition) {
        Class clz = null;
        Object obj = null;
        Constructor<?> con = null;
        try {
            clz = Class.forName(beanDefinition.getClassName());

            ConstructorArgumentValues constructConstructorArgumentValues = beanDefinition.getConstructArgumentValues();
            if (!constructConstructorArgumentValues.isEmpty()) {
                Class<?>[] paramTypes = new Class<?>[constructConstructorArgumentValues.getArgumentCount()];
                Object[] paramValue = new Object[constructConstructorArgumentValues.getArgumentCount()];

                for (int i = 0; i < constructConstructorArgumentValues.getArgumentCount(); i++) {
                    ConstructorArgumentValue constructorArgumentValue = constructConstructorArgumentValues.getIndexedArgumentValue(i);

                    if ("String".equals(constructorArgumentValue.getType()) || "java.lang.String".equals(constructorArgumentValue.getType())) {
                        paramTypes[i] = String.class;
                        paramValue[i] = constructorArgumentValue.getValue();
                    } else if ("Integer".equals(constructorArgumentValue.getType()) || "java.lang.Integer".equals(constructorArgumentValue.getType())) {
                        paramTypes[i] = Integer.class;
                        paramValue[i] = Integer.valueOf((String) constructorArgumentValue.getValue());
                    } else if ("int".equals(constructorArgumentValue.getType())) {
                        paramTypes[i] = int.class;
                        paramValue[i] = Integer.valueOf((String) constructorArgumentValue.getValue());
                    } else {
                        paramTypes[i] = String.class;
                        paramValue[i] = constructorArgumentValue.getValue();
                    }
                }
                con = clz.getConstructor(paramTypes);
                obj = con.newInstance(paramValue);
            } else {
                obj = clz.newInstance();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println(beanDefinition.getId() + " bean created. " + beanDefinition.getClassName() + " : " + obj.toString());
        return obj;
    }


    private void handleProperties(BeanDefinition beanDefinition, Class<?> clz, Object obj) {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();

        for (int i = 0; i < propertyValues.size(); i++) {
            PropertyValue propertyValue = propertyValues.getPropertyValueList().get(i);

            Class<?>[] paramType = new Class[1];
            Object[] paramValue = new Object[]{propertyValue.getValue()};

            if (!propertyValue.isRef()) {
                if ("String".equals(propertyValue.getType()) || "java.lang.String".equals(propertyValue.getType())) {
                    paramType[0] = String.class;
                } else if ("Integer".equals(propertyValue.getType()) || "java.lang.Integer".equals(propertyValue.getType())) {
                    paramType[0] = Integer.class;
                } else if ("int".equals(propertyValue.getType())) {
                    paramType[0] = int.class;
                } else {
                    paramType[0] = String.class;
                }

            } else {
                // 处理 ref 引用
                try {
                    paramType[0] = Class.forName(propertyValue.getType());
                    paramValue[0] = getBean((String) propertyValue.getValue());


                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            String name = propertyValue.getName();
            String methodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);

            Method method = null;
            try {
                method = clz.getMethod(methodName, paramType);
                method.invoke(obj, paramValue);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        }
    }

    @Override
    public Boolean containsBean(String name) {
        return beanDefinitionMap.containsKey(name);

    }

    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitionMap.get(name);
    }

    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitionMap.containsKey(name);
    }

    public boolean isSingleton(String name) {
        return this.beanDefinitionMap.get(name).isSingleton();
    }

    public boolean isPrototype(String name) {
        return this.beanDefinitionMap.get(name).isPrototype();
    }

    public Class<?> getType(String name) {
        return this.beanDefinitionMap.get(name).getClass();
    }

    public void refresh() {
        for (String beanDefinitionName : beanDefinitionNames) {
            try {
                getBean(beanDefinitionName);
            } catch (Exception e) {

            }
        }
    }
}
