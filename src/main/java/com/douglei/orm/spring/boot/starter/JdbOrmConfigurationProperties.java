package com.douglei.orm.spring.boot.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.douglei.orm.Configuration;

/**
 * jdb.orm框架在spring中的配置属性对象
 * @author DougLei
 */
@ConfigurationProperties(prefix = "jdb.orm")
public class JdbOrmConfigurationProperties {
	
	private String defaultJdbOrmConf = Configuration.DEFAULT_CONFIGURATION_FILE_PATH;// 默认jdb-orm.conf
	private String dataSourceCloseMethodName;// 数据源关闭的方法名
	
	private boolean enableRedisStoreMapping;// 是否启用redis存储mapping
	private String mappingContainer2RedisImplClass;// 映射存储实现类(redis)的全路径, 继承 {@link SpringRedisMappingContainer}类
	private boolean storeMultiDataSource;// 是否存储多个数据源的映射
	
	public String getDefaultJdbOrmConf() {
		return defaultJdbOrmConf;
	}
	public void setDefaultJdbOrmConf(String defaultJdbOrmConf) {
		this.defaultJdbOrmConf = defaultJdbOrmConf;
	}
	public String getDataSourceCloseMethodName() {
		return dataSourceCloseMethodName;
	}
	public void setDataSourceCloseMethodName(String dataSourceCloseMethodName) {
		this.dataSourceCloseMethodName = dataSourceCloseMethodName;
	}
	public boolean isEnableRedisStoreMapping() {
		return enableRedisStoreMapping;
	}
	public void setEnableRedisStoreMapping(boolean enableRedisStoreMapping) {
		this.enableRedisStoreMapping = enableRedisStoreMapping;
	}
	public String getMappingContainer2RedisImplClass() {
		return mappingContainer2RedisImplClass;
	}
	public void setMappingContainer2RedisImplClass(String mappingContainer2RedisImplClass) {
		this.mappingContainer2RedisImplClass = mappingContainer2RedisImplClass;
	}
	public boolean isStoreMultiDataSource() {
		return storeMultiDataSource;
	}
	public void setStoreMultiDataSource(boolean storeMultiDataSource) {
		this.storeMultiDataSource = storeMultiDataSource;
	}
}
