package com.douglei.orm.spring.boot.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.douglei.orm.context.SessionFactoryRegister;

/**
 * 事物组件扫描的自动配置
 * @author DougLei
 */
@Configuration // 标明这是一个配置类
@ConditionalOnClass(SessionFactoryRegister.class) // @ConditionalOnXXX, 满足指定条件时, 该配置类生效, 该配置表示当前classpath中存在SessionFactoryRegister类时, 该配置类生效
@Import(TransactionComponentAutoRegistry.class)
public class TransactionComponentScanAutoConfiguration {

}
