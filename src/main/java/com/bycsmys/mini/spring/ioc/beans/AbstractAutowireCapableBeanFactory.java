package com.bycsmys.mini.spring.beans;

import com.bycsmys.mini.spring.beans.factory.AbstractBeanFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    protected final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();

    public void addBeanPostProcessor(BeanFactoryPostProcessor beanFactoryPostProcessor) {
        this.beanFactoryPostProcessors.remove(beanFactoryPostProcessor);
        this.beanFactoryPostProcessors.add(beanFactoryPostProcessor);
    }

    public int getBeanPostProcessorCount() {
        return beanFactoryPostProcessors.size();
    }

    public List<BeanFactoryPostProcessor> getBeanPostProcessors() {
        return beanFactoryPostProcessors;
    }

    @Override
    public Object applyBeanPostProcessorBeforeInitialization(Object existingBean, String beanName) {
        Object result = existingBean;
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessors) {
            beanFactoryPostProcessor.setBeanFactory(this);
            try {
                result = beanFactoryPostProcessor.postProcessBeforeInitialization(result, beanName);
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
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessors) {
            try {
                result = beanFactoryPostProcessor.postProcessAfterInitialization(result, beanName);
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
