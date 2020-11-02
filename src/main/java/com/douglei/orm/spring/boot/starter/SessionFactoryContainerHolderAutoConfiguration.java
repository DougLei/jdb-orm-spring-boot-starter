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
import com.douglei.orm.context.IdRepeatedException;
import com.douglei.orm.context.SessionFactoryContainer;
import com.douglei.orm.mapping.container.MappingContainer;
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
public class SessionFactoryContainerHolderAutoConfiguration {
	
	@Autowired
	protected JdbOrmConfigurationProperties jdbOrmConfigurationProperties;
	
	@Autowired(required = false)
	protected DataSource dataSource;
	
	@Bean
	@ConditionalOnMissingBean(SessionFactoryContainer.class)
	public SessionFactoryContainer sessionFactoryContainer() throws IdRepeatedException  {
		SessionFactoryContainer container = SessionFactoryContainer.getSingleton();
		registerDefaultSessionFactory(container);
		return container;
	}
	
	// 注册默认的数据源
	private void registerDefaultSessionFactory(SessionFactoryContainer container) throws IdRepeatedException {
		ConfigurationWrapper defaultConfiguration = getDefaultConfiguration();
		container.registerByFile(defaultConfiguration.getConfigurationFile(), defaultConfiguration.getDataSource(), defaultConfiguration.getMappingContainer());
	}
	private ConfigurationWrapper getDefaultConfiguration() {
		ConfigurationWrapper defaultConfiguration = new ConfigurationWrapper();
		defaultConfiguration.setConfigurationFile(jdbOrmConfigurationProperties.getDefaultJdbOrmConf());
		defaultConfiguration.setDataSource(getDataSource());
		defaultConfiguration.setMappingContainer(getMappingContainer());
		return defaultConfiguration;
	}
	private ExternalDataSource getDataSource() {
		if(dataSource != null) {
			return new ExternalDataSource(dataSource, jdbOrmConfigurationProperties.getDataSourceCloseMethodName());
		}
		return null;
	}
	protected MappingContainer getMappingContainer() {
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
