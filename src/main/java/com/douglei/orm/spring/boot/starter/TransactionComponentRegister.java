package com.douglei.orm.spring.boot.starter;

import java.util.Map;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import com.douglei.orm.spring.TransactionComponentRegister2Spring;

/**
 * 
 * @author DougLei
 */
public class TransactionComponentRegister extends TransactionComponentRegister2Spring implements ImportBeanDefinitionRegistrar{
	
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		Map<String, Object> transactionComponentScannerMap = importingClassMetadata.getAnnotationAttributes(TransactionComponentScan.class.getName());
		String[] transactionComponentPackages = (String[]) transactionComponentScannerMap.get("transactionComponentPackages");
		register2Spring(registry, transactionComponentPackages);
	}
}
