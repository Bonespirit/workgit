package com.pang.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class MyRedisConfig {


//	@Bean
//	public KeyGenerator simpleKeyGenerator() {
//		return (o, method, objects) -> {
//			StringBuilder stringBuilder = new StringBuilder();
//			stringBuilder.append(o.getClass().getSimpleName());
//			stringBuilder.append(".");
//			stringBuilder.append(method.getName());
//			stringBuilder.append("[");
//			for (Object obj : objects) {
//				stringBuilder.append(obj.toString());
//			}
//			stringBuilder.append("]");
//
//			return stringBuilder.toString();
//		};
//	}

	@Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		return new RedisCacheManager(
				RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
				this.getRedisCacheConfigurationWithTtl(600), // 默认策略600分钟，未配置的 key 会使用这个
				this.getRedisCacheConfigurationMap() // 指定 key 策略
				);
	}

	private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
		Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
		redisCacheConfigurationMap.put("ShortCache", this.getRedisCacheConfigurationWithTtl(1));//10分钟的短缓存
		redisCacheConfigurationMap.put("LongCache", this.getRedisCacheConfigurationWithTtl(2));//一个钟的长缓存

		return redisCacheConfigurationMap;
	}

	private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer minutes) {
		//默认1
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(minutes))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()))
                .disableCachingNullValues();

		return config;
	}


	@Bean
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
		StringRedisTemplate template = new StringRedisTemplate(factory);
		template.setValueSerializer(new StringRedisSerializer());
		template.setKeySerializer(new StringRedisSerializer());
		template.afterPropertiesSet();
		return template;

	}
	
	private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }
    private RedisSerializer<Object> valueSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }
}
