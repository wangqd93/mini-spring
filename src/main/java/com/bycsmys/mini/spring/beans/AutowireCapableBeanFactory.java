package com.bycsmys.mini.spring.beans;

import com.bycsmys.mini.spring.beans.factory.AbstractBeanFactory;

import java.util.ArrayList;
import java.util.List;

public class AutowireCapableBeanFactory  extends AbstractBeanFactory {

    protected final List<AutowiredAnnotationBeanPostProcessor> beanPostProcessors = new ArrayList<>();


    public void addBeanPostProcessor(AutowiredAnnotationBeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public int getBeanPostProcessorCount() {
        return beanPostProcessors.size();
    }

    public List<AutowiredAnnotationBeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }

    @Override
    public Object applyBeanPostProcessorBeforeInitialization(Object existingBean, String beanName) {
        Object result = existingBean;
        for (AutowiredAnnotationBeanPostProcessor beanPostProcessor : beanPostProcessors) {
            beanPostProcessor.setBeanFactory(this);
            try {
                result = beanPostProcessor.postProcessBeforeInitialization(result, beanName);
                if (result == null) {
                    return result;
                }
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorAfterInitialization(Object existingBean, String beanName) {
        Object result = existingBean;
        for (AutowiredAnnotationBeanPostProcessor beanPostProcessor : beanPostProcessors) {
            try {
                result = beanPostProcessor.postProcessAfterInitialization(result, beanName);
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
            if (result == null) {
                return result;
            }
        }

        return result;
    }
}
