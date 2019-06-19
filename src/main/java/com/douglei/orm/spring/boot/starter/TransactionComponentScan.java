package com.douglei.orm.spring.boot.starter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * 事务组件扫描器
 * @author DougLei
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(TransactionComponentRegistry.class)
public @interface TransactionComponentScan {
	
	String[] packages();
}