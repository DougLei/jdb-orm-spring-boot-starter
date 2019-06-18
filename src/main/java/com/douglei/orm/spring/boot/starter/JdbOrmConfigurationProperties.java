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
	// 这些属性必须提供get/set方法, 才可以在spring的application.properties文件中通过快捷方式直接带出来
	// 而且这些属性还都必须是基础类型, 或者String类型
	
	/**
	 * 【必须配置】设置默认的SessionFactory
	 */
	private String defaultSessionFactoryConfigurationFile;
	
	/**
	 * 【多数据源】设置SessionFactory
	 */
	private String sessionFactoryConfigurationFiles;
	
	/**
	 * 要扫描@Transaction的根包路径
	 */
	private String transactionBasePackage;

	public String getDefaultSessionFactoryConfigurationFile() {
		if(defaultSessionFactoryConfigurationFile == null) {
			defaultSessionFactoryConfigurationFile = Configuration.DEFAULT_CONF_FILE;
		}
		return defaultSessionFactoryConfigurationFile;
	}
	public String getTransactionBasePackage() {
		return transactionBasePackage;
	}
	public String getSessionFactoryConfigurationFiles() {
		return sessionFactoryConfigurationFiles;
	}
	public void setDefaultSessionFactoryConfigurationFile(String defaultSessionFactoryConfigurationFile) {
		this.defaultSessionFactoryConfigurationFile = defaultSessionFactoryConfigurationFile;
	}
	public void setSessionFactoryConfigurationFiles(String sessionFactoryConfigurationFiles) {
		this.sessionFactoryConfigurationFiles = sessionFactoryConfigurationFiles;
	}
	public void setTransactionBasePackage(String transactionBasePackage) {
		this.transactionBasePackage = transactionBasePackage;
	}
	
	public String[] getSessionFactoryConfigurationFileArray() {
		if(StringUtil.notEmpty(this.sessionFactoryConfigurationFiles)) {
			return this.sessionFactoryConfigurationFiles.split(",");
		}
		return null;
	} 
}
