package com.github.githubwrapper.configuration;

import com.github.githubwrapper.cache.MongoCache;
import com.github.githubwrapper.cache.MongoCacheManager;
import com.github.githubwrapper.dto.FullResponseDto;
import com.github.githubwrapper.dto.ShortResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

@Configuration
@EnableCaching
@ConditionalOnProperty(
        value = "app.cache.type",
        havingValue = "mongo")
public class MongoConfiguration {

    @Value("${app.cache.ttl-in-seconds:10}")
    private Long ttl;

    @Bean
    public CacheManager cacheManager(MongoTemplate mongoTemplate) {
        var fullResponseCache = new MongoCache<>("fullResponse", mongoTemplate, ttl, FullResponseDto.class);
        var shortResponseCache = new MongoCache<>("shortResponse", mongoTemplate, ttl, ShortResponseDto.class);
        return new MongoCacheManager(List.of(fullResponseCache, shortResponseCache));
    }

}
