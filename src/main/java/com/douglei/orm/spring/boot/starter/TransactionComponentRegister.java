package com.douglei.orm.spring.boot.starter;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import com.douglei.orm.context.TransactionAnnotationMemoryUsage;
import com.douglei.orm.context.TransactionComponentProxyEntity;

/**
 * 
 * @author DougLei
 */
public class TransactionComponentRegister implements ImportBeanDefinitionRegistrar{
	
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		Map<String, Object> transactionComponentScannerMap = importingClassMetadata.getAnnotationAttributes(TransactionComponentScan.class.getName());
		String[] transactionComponentPackages = (String[]) transactionComponentScannerMap.get("transactionComponentPackages");
		registerBeanDefinitions(registry, transactionComponentPackages);
	}
	
	private void registerBeanDefinitions(BeanDefinitionRegistry registry, String[] transactionComponentPackages) {
		List<TransactionComponentProxyEntity> transactionComponentProxyEntities = TransactionAnnotationMemoryUsage.scanTransactionComponent(transactionComponentPackages);
		
		Class<?> transactionClass = null;
		GenericBeanDefinition definition = null;
		for (TransactionComponentProxyEntity transactionComponentProxyEntity : transactionComponentProxyEntities) {
			transactionClass = transactionComponentProxyEntity.getTransactionComponentProxyBeanClass();
			definition = new GenericBeanDefinition();
			
			// 设置该bean的class为TransactionBeanFactory类
			definition.setBeanClass(TransactionComponentProxyBeanFactory.class);
			
			// 将参数传递给TransactionBeanFactory类的构造函数
			definition.getConstructorArgumentValues().addGenericArgumentValue(transactionClass);
			definition.getConstructorArgumentValues().addGenericArgumentValue(transactionComponentProxyEntity.getTransactionMethods());
			
			// 设置根据类型注入
			definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
			
			// 将bean注入到spring容器中
			registry.registerBeanDefinition(transactionClass.getSimpleName(), definition);
		}
	}
}
