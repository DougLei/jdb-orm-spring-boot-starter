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
	// 而且这些属性都必须是基础类型, 或者String类型
	
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
	
	
	private String transactionComponentScanPackages;// 事物组件扫描的包
	public void setTransactionComponentScanPackages(String transactionComponentScanPackages) {
		this.transactionComponentScanPackages = transactionComponentScanPackages;
	}
	public String getTransactionComponentScanPackages() {
		return transactionComponentScanPackages;
	}
	public String[] getTransactionComponentScanPackageArray() {
		if(StringUtil.notEmpty(this.transactionComponentScanPackages)) {
			return this.transactionComponentScanPackages.split(",");
		}
		return null;
	}
}
