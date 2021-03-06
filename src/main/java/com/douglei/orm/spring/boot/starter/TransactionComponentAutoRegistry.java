package com.douglei.orm.spring.boot.starter;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import com.douglei.orm.spring.TransactionComponentRegister2Spring;

/**
 * 
 * @author DougLei
 */
public class TransactionComponentAutoRegistry extends TransactionComponentRegister2Spring implements BeanFactoryAware, ImportBeanDefinitionRegistrar{
	private static final Logger logger = LoggerFactory.getLogger(TransactionComponentAutoRegistry.class);
	
	private BeanFactory beanFactory;
	
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		if(!AutoConfigurationPackages.has(this.beanFactory)) {
			logger.debug("自动扫描事物组件的packages值为空");
			return;
		}
		
		List<String> packages = AutoConfigurationPackages.get(this.beanFactory);
		if(logger.isDebugEnabled()) 
			packages.forEach(package_ -> logger.debug("AutoConfigurationPackages.get(this.beanFactory), 获取的事物组件packages为: {}", package_));
		
		register2Spring(registry, true, packages.toArray(new String[packages.size()]));
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}
}
