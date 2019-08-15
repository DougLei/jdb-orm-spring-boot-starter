package com.douglei.orm.spring.boot.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.douglei.orm.configuration.environment.mapping.store.MappingStore;
import com.douglei.orm.spring.redis.mapping.store.RedisMappingStore;
import com.douglei.orm.spring.redis.mapping.store.SpringRedisMappingStore;
import com.douglei.tools.utils.reflect.ConstructorUtil;

/**
 * 
 * @author DougLei
 */
@Configuration
@ConditionalOnClass(RedisConnectionFactory.class)
public class SessionFactoryRegisterHolderAutoConfigurationWithRedis extends SessionFactoryRegisterHolderAutoConfiguration{
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Bean
	public RedisTemplate<String, Object> soRedisTempalte(RedisConnectionFactory connectionFactory){
		RedisTemplate<String, Object> soRedisTempalte = new RedisTemplate<String, Object>();
		soRedisTempalte.setKeySerializer(RedisSerializer.string());
		soRedisTempalte.setValueSerializer(RedisSerializer.java());
		soRedisTempalte.setConnectionFactory(connectionFactory);
		return soRedisTempalte;
	}
	
	@Override
	protected MappingStore getMappingStore() {
		if(jdbOrmConfigurationProperties.isEnableRedisStoreMapping() && redisTemplate != null) {
			String mappingStore2RedisImplClass = jdbOrmConfigurationProperties.getMappingStore2RedisImplClass();
			if(mappingStore2RedisImplClass == null) {
				mappingStore2RedisImplClass = RedisMappingStore.class.getName();
			}
			SpringRedisMappingStore srms = (SpringRedisMappingStore) ConstructorUtil.newInstance(mappingStore2RedisImplClass);
			srms.setTemplate(redisTemplate);
			srms.setStoreMultiDataSource(jdbOrmConfigurationProperties.isStoreMultiDataSource());
			return srms;
		}
		return null;
	}
}
