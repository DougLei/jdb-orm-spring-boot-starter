package com.douglei.orm.spring.boot.starter;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;

import com.douglei.orm.configuration.Configuration;
import com.douglei.orm.configuration.ExternalDataSource;
import com.douglei.orm.configuration.environment.mapping.store.MappingStore;
import com.douglei.orm.spring.ConfigurationWrapper;
import com.douglei.orm.spring.redis.mapping.store.RedisMappingStore;
import com.douglei.orm.spring.redis.mapping.store.SpringRedisMappingStore;
import com.douglei.tools.utils.reflect.ConstructorUtil;

/**
 * jdb.orm框架在spring中的配置属性对象
 * @author DougLei
 */
@ConfigurationProperties(prefix = "jdb.orm")
public class JdbOrmConfigurationProperties {
	
	private String defaultJdbOrmConf = Configuration.DEFAULT_CONF_FILE;// 默认jdb-orm.conf
	private String dataSourceCloseMethodName;// 数据源关闭的方法名
	
	private boolean enableRedisStoreMapping;// 是否启用redis存储mapping
	private String mappingStore2RedisImplClass;// 映射存储实现类(redis)的全路径, 继承 {@link SpringRedisMappingStore}类
	private boolean multiDataSource;// 是否是多个数据源, 如果包含多个数据源, 则code需要前缀区分是哪个数据源
	
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
	public String getMappingStore2RedisImplClass() {
		return mappingStore2RedisImplClass;
	}
	public void setMappingStore2RedisImplClass(String mappingStore2RedisImplClass) {
		this.mappingStore2RedisImplClass = mappingStore2RedisImplClass;
	}
	public boolean isMultiDataSource() {
		return multiDataSource;
	}
	public void setMultiDataSource(boolean multiDataSource) {
		this.multiDataSource = multiDataSource;
	}
	
	
	public ConfigurationWrapper defaultConfiguration(DataSource dataSource, RedisTemplate<String, Object> redisTemplate) {
		ConfigurationWrapper defaultConfiguration = new ConfigurationWrapper();
		defaultConfiguration.setConfigurationFile(defaultJdbOrmConf);
		defaultConfiguration.setDataSource(getDataSource(dataSource));
		defaultConfiguration.setMappingStore(getMappingStore(redisTemplate));
		return defaultConfiguration;
	}
	private ExternalDataSource getDataSource(DataSource dataSource) {
		if(dataSource != null) {
			return new ExternalDataSource(dataSource, dataSourceCloseMethodName);
		}
		return null;
	}
	private MappingStore getMappingStore(RedisTemplate<String, Object> redisTemplate) {
		if(enableRedisStoreMapping && redisTemplate != null) {
			if(mappingStore2RedisImplClass == null) {
				mappingStore2RedisImplClass = RedisMappingStore.class.getName();
			}
			SpringRedisMappingStore srms = (SpringRedisMappingStore) ConstructorUtil.newInstance(mappingStore2RedisImplClass);
			srms.setTemplate(redisTemplate);
			srms.setMultiDataSource(multiDataSource);
			return srms;
		}
		return null;
	}
}
