package com.douglei.orm.spring.boot.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.douglei.orm.configuration.Configuration;
import com.douglei.tools.utils.StringUtil;

/**
 * jdb.orm框架在spring中的配置属性对象
 * @author DougLei
 */
@ConfigurationProperties(prefix = "jdb.orm")
public class JdbOrmConfigurationProperties {
	// 这些属性必须提供get/set方法, 或者只有get/set方法都行, 才可以在spring的application.properties文件中通过快捷方式直接带出来
	
	private String defaultSessionFactoryConfigurationFile;// 默认数据源配置
	public void setDefaultSessionFactoryConfigurationFile(String defaultSessionFactoryConfigurationFile) {
		this.defaultSessionFactoryConfigurationFile = defaultSessionFactoryConfigurationFile;
	}
	public String getDefaultSessionFactoryConfigurationFile() {
		if(StringUtil.isEmpty(defaultSessionFactoryConfigurationFile)) {
			defaultSessionFactoryConfigurationFile = Configuration.DEFAULT_CONF_FILE;
		}
		return defaultSessionFactoryConfigurationFile;
	}
	
	private String sessionFactoryConfigurationFiles;// 多数据源配置
	public void setSessionFactoryConfigurationFiles(String sessionFactoryConfigurationFiles) {
		this.sessionFactoryConfigurationFiles = sessionFactoryConfigurationFiles;
	}
	public String getSessionFactoryConfigurationFiles() {
		return sessionFactoryConfigurationFiles;
	}
	public String[] getSessionFactoryConfigurationFileArray() {
		if(StringUtil.notEmpty(this.sessionFactoryConfigurationFiles)) {
			return this.sessionFactoryConfigurationFiles.split(",");
		}
		return null;
	}
}
