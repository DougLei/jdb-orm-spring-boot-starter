package com.douglei.orm.spring.boot.starter;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.douglei.orm.configuration.ExternalDataSource;
import com.douglei.orm.configuration.environment.mapping.store.MappingStore;
import com.douglei.orm.context.SessionFactoryRegister;
import com.douglei.orm.spring.ConfigurationWrapper;
import com.douglei.orm.spring.DestroyProxyBeanContextListener;

/**
 * 
 * @author DougLei
 */
@Configuration
@ConditionalOnMissingClass("org.springframework.data.redis.connection.RedisConnectionFactory")
@EnableConfigurationProperties(value = JdbOrmConfigurationProperties.class)
@AutoConfigureAfter({DataSourceAutoConfiguration.class, RedisAutoConfiguration.class})
public class SessionFactoryRegisterHolderAutoConfiguration {
	
	@Autowired
	protected JdbOrmConfigurationProperties jdbOrmConfigurationProperties;
	
	@Autowired(required = false)
	protected DataSource dataSource;
	
	@Bean
	@ConditionalOnMissingBean(SessionFactoryRegister.class)
	public SessionFactoryRegister sessionFactoryRegister() {
		SessionFactoryRegister sessionFactoryRegister = new SessionFactoryRegister();
		registerDefaultSessionFactory(sessionFactoryRegister);
		return sessionFactoryRegister;
	}
	
	// 注册默认的数据源
	private void registerDefaultSessionFactory(SessionFactoryRegister sessionFactoryRegister) {
		ConfigurationWrapper defaultConfiguration = getDefaultConfiguration(dataSource);
		sessionFactoryRegister.registerDefaultSessionFactory(defaultConfiguration.getConfigurationFile(), defaultConfiguration.getDataSource(), defaultConfiguration.getMappingStore(), false);
	}
	private ConfigurationWrapper getDefaultConfiguration(DataSource dataSource) {
		ConfigurationWrapper defaultConfiguration = new ConfigurationWrapper();
		defaultConfiguration.setConfigurationFile(jdbOrmConfigurationProperties.getDefaultJdbOrmConf());
		defaultConfiguration.setDataSource(getDataSource());
		defaultConfiguration.setMappingStore(getMappingStore());
		return defaultConfiguration;
	}
	private ExternalDataSource getDataSource() {
		if(dataSource != null) {
			return new ExternalDataSource(dataSource, jdbOrmConfigurationProperties.getDataSourceCloseMethodName());
		}
		return null;
	}
	protected MappingStore getMappingStore() {
		return null;
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
