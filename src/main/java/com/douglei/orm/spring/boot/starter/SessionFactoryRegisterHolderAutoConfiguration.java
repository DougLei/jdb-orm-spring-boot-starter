package com.douglei.orm.spring.boot.starter;

import org.springframework.beans.factory.annotation.Autowired;
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
@EnableConfigurationProperties(value = JdbOrmConfigurationProperties.class)
public class SessionFactoryRegisterHolderAutoConfiguration {
	private SessionFactoryRegister sessionFactoryRegister;
	
	@Autowired
	private JdbOrmConfigurationProperties jdbOrmConfigurationProperties;
	
	
	@Bean // 将该方法产生的bean存储到spring容器中
	@ConditionalOnMissingBean(SessionFactoryRegister.class) // 如果容器中不存在该类实例, 则创建该类的实例, 并加入到容器中
	public SessionFactoryRegister sessionFactoryRegister() { // 方法名要和返回值的类型名一致, 首字母小写
		sessionFactoryRegister = new SessionFactoryRegister();
		registerDefaultSessionFactory(jdbOrmConfigurationProperties.defaultConfiguration());
		return sessionFactoryRegister;
	}
	
	// 注册默认的数据源
	private void registerDefaultSessionFactory(ConfigurationWrapper defaultConfiguration) {
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
