package com.bycsmys.mini.spring.ioc.beans;

import com.bycsmys.mini.spring.ioc.beans.factory.BeanFactory;
import com.bycsmys.mini.spring.ioc.beans.factory.annptation.Autowired;

import java.lang.reflect.Field;

public class AutowiredAnnotationBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    private BeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Object result = bean;

        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null) {

            // 处理field
            for (Field field : fields) {
                boolean isAutoWired = field.isAnnotationPresent(Autowired.class);
                if (isAutoWired) {
                    String fieldName = field.getName();

                    // 仅按照属性名称查找 需要确保属性名称和beanId 一样
                    Object autowiredObject = this.getBeanFactory().getBean(fieldName);
                    try {
                        field.setAccessible(true);
                        field.set(bean, autowiredObject);
                        System.out.println("autowired " + fieldName + " for bean " + beanName);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            return result;
        }


        return result;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }



    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
