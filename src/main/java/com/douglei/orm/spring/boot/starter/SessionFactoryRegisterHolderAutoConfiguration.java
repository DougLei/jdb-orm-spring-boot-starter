package com.douglei.orm.spring.boot.starter;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import com.douglei.orm.context.SessionFactoryRegister;
import com.douglei.orm.spring.ConfigurationWrapper;
import com.douglei.orm.spring.DestroyProxyBeanContextListener;

/**
 * 
 * @author DougLei
 */
@Configuration
@EnableConfigurationProperties(value = JdbOrmConfigurationProperties.class)
public class SessionFactoryRegisterHolderAutoConfiguration {
	
	@Autowired
	private JdbOrmConfigurationProperties jdbOrmConfigurationProperties;
	
	@Autowired(required = false)
	private DataSource dataSource;
	
	@Autowired(required = false)
	private RedisTemplate<String, Object> redisTemplate;
	
	@Bean // 将该方法产生的bean存储到spring容器中
	@ConditionalOnMissingBean(SessionFactoryRegister.class)
	public SessionFactoryRegister sessionFactoryRegister() {
		SessionFactoryRegister sessionFactoryRegister = new SessionFactoryRegister();
		registerDefaultSessionFactory(sessionFactoryRegister);
		return sessionFactoryRegister;
	}
	
	// 注册默认的数据源
	private void registerDefaultSessionFactory(SessionFactoryRegister sessionFactoryRegister) {
		ConfigurationWrapper defaultConfiguration = jdbOrmConfigurationProperties.defaultConfiguration(dataSource, redisTemplate);
		sessionFactoryRegister.registerDefaultSessionFactory(defaultConfiguration.getConfigurationFile(), defaultConfiguration.getDataSource(), defaultConfiguration.getMappingStore(), false);
	}
	
	/**
	 * 配置销毁ProxyBeanContext的监听器
	 * @return
	 */
	@Bean
	public DestroyProxyBeanContextListener destroyProxyBeanContextListener() {
		return new DestroyProxyBeanContextListener();
	}
}
