package com.douglei.orm.spring.boot.starter;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

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
	private SessionFactoryRegister sessionFactoryRegister;
	
	@Autowired
	private JdbOrmConfigurationProperties jdbOrmConfigurationProperties;
	
	@Bean // 将该方法产生的bean存储到spring容器中
	@ConditionalOnMissingBean(SessionFactoryRegister.class)
	public SessionFactoryRegister sessionFactoryRegister(@Nullable DataSource dataSource) {
		sessionFactoryRegister = new SessionFactoryRegister();
		registerDefaultSessionFactory(jdbOrmConfigurationProperties.defaultConfiguration());
		return sessionFactoryRegister;
	}
	
	// 注册默认的数据源
	private void registerDefaultSessionFactory(ConfigurationWrapper defaultConfiguration) {
		sessionFactoryRegister.registerDefaultSessionFactory(
				defaultConfiguration.getConfigurationFile(), 
				defaultConfiguration.getDataSource(), 
				jdbOrmConfigurationProperties.isEnableRedisStoreMapping()?defaultConfiguration.getMappingStore():null, 
				false);
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
