package com.douglei.orm.spring.boot.starter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Import;

import com.douglei.orm.context.SessionFactoryRegister;

/**
 * 事物组件扫描的注解
 * @author DougLei
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ConditionalOnClass(SessionFactoryRegister.class) // @ConditionalOnXXX, 满足指定条件时, 该配置类生效, 该配置表示当前classpath中存在SessionFactoryRegister类时, 该配置类生效
@Import(TransactionComponentRegistry.class)
public @interface TransactionComponentScan {
	
	boolean searchAll() default true;
	
	String[] transactionComponentPackages();
}
