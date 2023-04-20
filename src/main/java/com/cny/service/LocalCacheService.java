package com.cny.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @author : chennengyuan
 * 本地缓存Service
 */
@Slf4j
@Service
public class LocalCacheService {

    private Cache<String, Object> localCache = null;

    @PostConstruct
    public void init() {
        localCache = CacheBuilder.newBuilder()
                .initialCapacity(100)
                .maximumSize(100)
                .expireAfterWrite(180, TimeUnit.SECONDS)
                .build();
    }

    public void set(String key, Object value) {
        localCache.put(key, value);
    }

    public Object get(String key) {
        return localCache.getIfPresent(key);
    }
}
