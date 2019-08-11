package com.douglei.orm.spring.boot.starter;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.douglei.orm.configuration.Configuration;
import com.douglei.orm.spring.ConfigurationWrapper;

/**
 * jdb.orm框架在spring中的配置属性对象
 * @author DougLei
 */
@ConfigurationProperties(prefix = "jdb.orm")
public class JdbOrmConfigurationProperties {
	
	private String defaultConfiguration;// 默认数据源配置
	public void setDefaultConfiguration(String defaultConfiguration) {
		this.defaultConfiguration = defaultConfiguration;
	}
	public String getDefaultConfiguration() {
		return defaultConfiguration;
	}
	
	private String dataSourceCloseMethodName;// 数据源关闭的方法名
	public String getDataSourceCloseMethodName() {
		return dataSourceCloseMethodName;
	}
	public void setDataSourceCloseMethodName(String dataSourceCloseMethodName) {
		this.dataSourceCloseMethodName = dataSourceCloseMethodName;
	}
	
	
	public ConfigurationWrapper getDefaultConfiguration_() {
		ConfigurationWrapper defaultConfiguration = new ConfigurationWrapper();
		defaultConfiguration.setConfigurationFile(this.defaultConfiguration==null?Configuration.DEFAULT_CONF_FILE:this.defaultConfiguration);
//		defaultConfiguration.setDataSource(dataSource);
//		defaultConfiguration.setMappingCacheStore(mappingCacheStore);
		return defaultConfiguration;
	}
}
