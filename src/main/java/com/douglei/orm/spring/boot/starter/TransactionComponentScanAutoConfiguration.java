package com.douglei.orm.spring.boot.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.douglei.orm.context.SessionFactoryContainer;
import com.douglei.orm.spring.TransactionComponentProxyBeanFactory;

/**
 * 事物组件扫描的自动配置
 * @author DougLei
 */
@Configuration 
@ConditionalOnClass(SessionFactoryContainer.class) 
@ConditionalOnMissingBean(TransactionComponentProxyBeanFactory.class)
@Import(TransactionComponentAutoRegistry.class)
public class TransactionComponentScanAutoConfiguration {
}
