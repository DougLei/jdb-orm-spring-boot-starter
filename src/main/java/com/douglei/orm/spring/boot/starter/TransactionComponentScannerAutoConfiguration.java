package com.douglei.orm.spring.boot.starter;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.douglei.orm.context.SessionFactoryRegister;
import com.douglei.orm.context.TransactionAnnotationMemoryUsage;
import com.douglei.orm.context.TransactionComponentProxyEntity;
import com.douglei.orm.spring.boot.starter.properties.JdbOrmConfigurationProperties;

/**
 * 
 * @author DougLei
 */
@Configuration
@ConditionalOnClass(SessionFactoryRegister.class)
@EnableConfigurationProperties(value = JdbOrmConfigurationProperties.class)
public class TransactionComponentScannerAutoConfiguration implements BeanDefinitionRegistryPostProcessor, InitializingBean{
	
	/**
	 * 要扫描@Transaction的根包路径
	 */
	private String[] transactionComponentPackages;
	
	@Autowired
	private JdbOrmConfigurationProperties jdbOrmConfigurationProperties;
	 
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		// 不用实现
		// 这个beanFactory参数, 可以获得下面register的bean
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println(jdbOrmConfigurationProperties);
		if(transactionComponentPackages == null || transactionComponentPackages.length == 0) {
			throw new NullPointerException(getClass().getName() + " 中的transactionComponentPackages属性值不能为空");
		}
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
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
