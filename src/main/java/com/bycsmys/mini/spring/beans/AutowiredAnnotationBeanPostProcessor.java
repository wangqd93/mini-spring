package com.bycsmys.mini.spring.beans;

import com.bycsmys.mini.spring.beans.factory.BeanFactory;
import com.bycsmys.mini.spring.beans.factory.annptation.Autowired;

import java.lang.reflect.Field;

public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor {

    private AutowireCapableBeanFactory beanFactory;

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


        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }


    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
