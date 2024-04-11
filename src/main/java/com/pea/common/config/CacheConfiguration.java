package com.pea.common.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;


@Configuration
public class CacheConfiguration {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        // 自定义缓存配置
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
                .maximumSize(100) // 设置缓存的最大容量
                .expireAfterWrite(5, TimeUnit.MINUTES) // 设置缓存过期策略（写入后5分钟过期）
                .recordStats(); // 记录缓存统计信息

        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }
}