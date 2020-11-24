package com.gasparbarancelli.cupons.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
class RedisConfiguration {

  @Value("${spring.redis.host}")
  private String host;

  @Value("${spring.redis.port}")
  private Integer port;

  @Bean
  public JedisConnectionFactory redisConnectionFactory() {
    RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
    return new JedisConnectionFactory(config);
  }
}
