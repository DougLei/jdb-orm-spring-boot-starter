package com.douglei.orm.spring.boot.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.douglei.orm.mapping.container.MappingContainer;
import com.douglei.orm.spring.redis.mapping.store.SpringRedisMappingContainer;
import com.douglei.orm.spring.redis.mapping.store.SpringRedisMappingContainerImpl;
import com.douglei.tools.reflect.ClassUtil;

/**
 * 
 * @author DougLei
 */
@Configuration
@ConditionalOnClass(RedisConnectionFactory.class)
public class SessionFactoryContainerHolderAutoConfigurationWithRedis extends SessionFactoryContainerHolderAutoConfiguration{
	
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
	protected MappingContainer getMappingContainer() {
		if(jdbOrmConfigurationProperties.isEnableRedisStoreMapping()) {
			String mappingContainer2RedisImplClass = jdbOrmConfigurationProperties.getMappingContainer2RedisImplClass();
			if(mappingContainer2RedisImplClass == null) {
				mappingContainer2RedisImplClass = SpringRedisMappingContainerImpl.class.getName();
			}
			SpringRedisMappingContainer srms = (SpringRedisMappingContainer) ClassUtil.newInstance(mappingContainer2RedisImplClass);
			srms.setTemplate(redisTemplate);
			srms.setStoreMultiDataSource(jdbOrmConfigurationProperties.isStoreMultiDataSource());
			return srms;
		}
		return null;
	}
}
