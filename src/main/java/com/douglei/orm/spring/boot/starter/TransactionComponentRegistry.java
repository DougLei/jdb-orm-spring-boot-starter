package com.douglei.orm.spring.boot.starter;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import com.douglei.orm.spring.TransactionComponentRegister2Spring;

/**
 * 
 * @author DougLei
 */
public class TransactionComponentRegistry extends TransactionComponentRegister2Spring implements ImportBeanDefinitionRegistrar{

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		AnnotationAttributes transactionComponentScanAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(TransactionComponentScan.class.getName()));
		register2Spring(registry, transactionComponentScanAttrs.getBoolean("scanAll"), transactionComponentScanAttrs.getStringArray("transactionComponentPackages"));
	}
}
