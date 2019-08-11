package com.douglei.orm.spring.boot.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.douglei.orm.context.SessionFactoryRegister;
import com.douglei.orm.spring.ConfigurationWrapper;
import com.douglei.orm.spring.DestroyProxyBeanContextListener;

/**
 * 
 * @author DougLei
 */
@Configuration // 标明这是一个配置类
@ConditionalOnClass(SessionFactoryRegister.class) // @ConditionalOnXXX, 满足指定条件时, 该配置类生效, 该配置表示当前classpath中存在SessionFactoryRegister类时, 该配置类生效
@EnableConfigurationProperties(value = JdbOrmConfigurationProperties.class)
public class SessionFactoryRegisterHolderAutoConfiguration {
	
	@Autowired
	private JdbOrmConfigurationProperties jdbOrmConfigurationProperties;
	
	private SessionFactoryRegister sessionFactoryRegister;
	
	@Bean // 将该方法产生的bean存储到spring容器中
	@ConditionalOnMissingBean(SessionFactoryRegister.class) // 如果容器中不存在该类实例, 则创建该类的实例, 并加入到容器中
	public SessionFactoryRegister sessionFactoryRegister() { // 方法名要和返回值的类型名一致, 首字母小写
		sessionFactoryRegister = new SessionFactoryRegister();
		registerDefaultSessionFactory(jdbOrmConfigurationProperties.getDefaultConfiguration());
		registerSessionFactorys(jdbOrmConfigurationProperties.getConfigurations());
		return sessionFactoryRegister;
	}
	
	// 注册默认的数据源
	private void registerDefaultSessionFactory(ConfigurationWrapper defaultConfiguration) {
		sessionFactoryRegister.registerDefaultSessionFactory(defaultConfiguration.getConfigurationFile(), defaultConfiguration.getDataSource(), defaultConfiguration.getMappingCacheStore(), false);
	}
	
	// 注册多数据源
	private void registerSessionFactorys(ConfigurationWrapper[] configurations) {
		if(configurations != null && configurations.length > 0) {
			for (ConfigurationWrapper configuration : configurations) {
				sessionFactoryRegister.registerSessionFactoryByConfigurationFile(configuration.getConfigurationFile(), configuration.getDataSource(), configuration.getMappingCacheStore());
			}
		}
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
