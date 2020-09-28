package com.douglei.orm.spring.boot.starter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Import;

import com.douglei.orm.context.SessionFactoryContainer;

/**
 * 事物组件扫描的注解
 * @author DougLei
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ConditionalOnClass(SessionFactoryContainer.class) // @ConditionalOnXXX, 满足指定条件时, 该配置类生效, 该配置表示当前classpath中存在SessionFactoryContainer类时, 该配置类生效
@Import(TransactionComponentRegistry.class)
public @interface TransactionComponentScan {
	
	/**
	 * 是否扫描所有包, 默认为true
	 * @return
	 */
	boolean searchAll() default true;
	
	/**
	 * 扫描事物的包路径数组
	 * @return
	 */
	String[] transactionComponentPackages();
}
