package com.douglei.orm.spring.boot.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.douglei.orm.configuration.Configuration;
import com.douglei.orm.spring.ConfigurationWrapper;

/**
 * jdb.orm框架在spring中的配置属性对象
 * @author DougLei
 */
@ConfigurationProperties(prefix = "jdb.orm")
public class JdbOrmConfigurationProperties {
	
	private ConfigurationWrapper defaultConfiguration;// 默认数据源配置
	public void setDefaultConfiguration(ConfigurationWrapper defaultConfiguration) {
		this.defaultConfiguration = defaultConfiguration;
	}
	public ConfigurationWrapper getDefaultConfiguration() {
		if(defaultConfiguration == null) {
			defaultConfiguration = new ConfigurationWrapper();
			defaultConfiguration.setConfigurationFile(Configuration.DEFAULT_CONF_FILE);
		}
		return defaultConfiguration;
	}
	
	
	private ConfigurationWrapper[] configurations;// 多数据源配置
	public void setConfigurations(ConfigurationWrapper[] configurations) {
		this.configurations = configurations;
	}
	public ConfigurationWrapper[] getConfigurations() {
		return configurations;
	}
}
