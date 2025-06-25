package com.rtaitai.springbootmall.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RedisCache {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofMinutes(1);
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public RedisCache(final RedisTemplate<String, String> redisTemplate, final ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public boolean putIfAbsent(final String cacheName, final String key, final String value, final Duration duration) {
        final String cacheKey = bindCacheKey(cacheName, key);
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(cacheKey, value, duration.toMillis(), TimeUnit.MILLISECONDS));
    }

    public void occupy(final String cacheName, final String key, final Duration duration) {
        final String cacheKey = bindCacheKey(cacheName, key);
        redisTemplate.opsForValue().set(cacheKey, "occupied", duration);
    }

    public Long increment(final String cacheName, final String key, final Duration duration) {
        final String cacheKey = bindCacheKey(cacheName, key);

        final Long increment = redisTemplate.opsForValue().increment(cacheKey, 1L);

        // increment 時不會設定時限，因為補上時限
        final Long ttl = Optional.ofNullable(redisTemplate.getExpire(cacheKey)).orElse(0L);
        if (ttl == -1L) {
            redisTemplate.expire(cacheKey, duration);
        }

        return increment;
    }

    public <T> T put(final String cacheName, final String key, final T value, final Duration duration) {
        final String cacheKey = bindCacheKey(cacheName, key);
        try {
            redisTemplate.opsForValue().set(cacheKey, new String(objectMapper.writeValueAsBytes(value)), duration.toMillis(), TimeUnit.MILLISECONDS);
        } catch (final JsonProcessingException e) {
            log.error("write value error:{}", value);
        }
        return value;
    }

    public void evict(final String cacheName, final String key) {
        redisTemplate.expire(bindCacheKey(cacheName, key), 0, TimeUnit.NANOSECONDS);
        // 等於 redisTemplate.delete() ?
    }




    private String bindCacheKey(final String cacheName, final String key) {
        return cacheName + ":" + key;
    }

    private List<String> bindCacheKeys(final String cacheName, final List<String> keys) {
        return keys.stream().map(key -> cacheName + ":" + key).collect(Collectors.toList());
    }
}
