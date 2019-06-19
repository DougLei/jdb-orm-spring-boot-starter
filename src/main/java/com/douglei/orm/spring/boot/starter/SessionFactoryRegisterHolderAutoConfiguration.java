package com.douglei.orm.spring.boot.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.douglei.orm.context.SessionFactoryRegister;

/**
 * 
 * @author DougLei
 */
@Configuration // 标明这是一个配置类
@ConditionalOnClass(SessionFactoryRegister.class) // @ConditionalOnXXX, 满足指定条件时, 该配置类生效
@EnableConfigurationProperties(value = JdbOrmConfigurationProperties.class)
public class SessionFactoryRegisterHolderAutoConfiguration {
	private static final SessionFactoryRegister sessionFactoryRegister = new SessionFactoryRegister();
	
	@Autowired
	private JdbOrmConfigurationProperties jdbOrmConfigurationProperties;
	
	@Bean // 将该方法产生的bean存储到spring容器中
	@ConditionalOnMissingBean(SessionFactoryRegister.class) // 如果容器中不存在该类实例, 则创建该类的实例, 并加入到容器中
	public SessionFactoryRegister sessionFactoryRegister() {
		registerDefaultSessionFactoryByConfigurationFile(jdbOrmConfigurationProperties.getDefaultSessionFactoryConfigurationFile());
		registerSessionFactoryByConfigurationFile(jdbOrmConfigurationProperties.getSessionFactoryConfigurationFileArray());
		return sessionFactoryRegister;
	}
	
	// 注册默认的数据源
	private void registerDefaultSessionFactoryByConfigurationFile(String defaultSessionFactoryConfigurationFile) {
		sessionFactoryRegister.registerDefaultSessionFactoryByConfigurationFile(defaultSessionFactoryConfigurationFile);
	}
	
	// 注册多数据源
	private void registerSessionFactoryByConfigurationFile(String[] sessionFactoryConfigurationFiles) {
		if(sessionFactoryConfigurationFiles != null) {
			for (String configurationFile : sessionFactoryConfigurationFiles) {
				sessionFactoryRegister.registerSessionFactoryByConfigurationFile(configurationFile);
			}
		}
	}
	
	/**
	 * 获取SessionFactoryRegister实例
	 * @return
	 */
	public static SessionFactoryRegister getSessionFactoryRegister() {
		return sessionFactoryRegister;
	}
}