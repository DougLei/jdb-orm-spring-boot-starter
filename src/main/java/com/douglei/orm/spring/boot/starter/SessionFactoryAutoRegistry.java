package com.douglei.orm.spring.boot.starter;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.douglei.orm.configuration.ExternalDataSource;
import com.douglei.orm.context.IdRepeatedException;
import com.douglei.orm.context.SessionFactoryContainer;
import com.douglei.orm.spring.ConfigurationEntity;
import com.douglei.orm.spring.DestroyProxyBeanContextListener;

/**
 * 
 * @author DougLei
 */
@Configuration
@EnableConfigurationProperties(value = JdbOrmConfigurationProperties.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class SessionFactoryAutoRegistry {
	
	@Autowired
	protected JdbOrmConfigurationProperties config;
	
	@Autowired(required = false)
	protected DataSource dataSource;
	
	@Bean
	@ConditionalOnMissingBean(SessionFactoryContainer.class)
	public SessionFactoryContainer sessionFactoryContainer() throws IdRepeatedException  {
		// 初始化JDB-ORM的Configuration
		ConfigurationEntity entity = new ConfigurationEntity();
		entity.setFilepath(config.getFilepath());
		entity.setDataSource(dataSource==null?null:new ExternalDataSource(dataSource, config.getDataSourceCloseMethodName()));
		entity.setMappingContainer(null);
		
		SessionFactoryContainer.getSingleton().registerByFile(entity.getFilepath(), entity.getDataSource(), entity.getMappingContainer());
		return SessionFactoryContainer.getSingleton();
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
