package com.douglei.orm.spring.boot.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.douglei.orm.context.SessionFactoryRegister;

/**
 * 自动配置对象
 * @author DougLei
 */
@Configuration
@EnableConfigurationProperties(value = JdbOrmConfigurationProperties.class) // 标识用的是哪个配置对象, 下面才能@Autowired到属性中
@ConditionalOnClass(SessionFactoryRegister.class) // 在classpath中存在 SessionFactoryRegister类时, 才会使用该自动配置对象
public class JdbOrmAutoConfiguration {
	private SessionFactoryRegister sessionFactoryRegister;
	
	@Autowired
	private JdbOrmConfigurationProperties jdbOrmConfigurationProperties;
	
	/**
	 * 获取SessionFactoryRegister实例
	 * @return
	 */
	public SessionFactoryRegister getSessionFactoryRegister() {
		if(sessionFactoryRegister == null) {
			sessionFactoryRegister = new SessionFactoryRegister();
			registerDefaultSessionFactoryConfigurationFile();
			registerSessionFactoryConfigurationFiles();
		}
		return sessionFactoryRegister;
	}
	
	// 注册默认数据源
	private void registerDefaultSessionFactoryConfigurationFile() {
		sessionFactoryRegister.registerDefaultSessionFactoryByConfigurationFile(jdbOrmConfigurationProperties.getDefaultSessionFactoryConfigurationFile());
	}
	
	// 注册多数据源
	private void registerSessionFactoryConfigurationFiles() {
		String[] sessionFactoryConfigurationFiles = jdbOrmConfigurationProperties.getSessionFactoryConfigurationFileArray();
		if(sessionFactoryConfigurationFiles != null && sessionFactoryConfigurationFiles.length > 0) {
			for (String configurationFile : sessionFactoryConfigurationFiles) {
				sessionFactoryRegister.registerSessionFactoryByConfigurationFile(configurationFile);
			}
		}
	}
}
