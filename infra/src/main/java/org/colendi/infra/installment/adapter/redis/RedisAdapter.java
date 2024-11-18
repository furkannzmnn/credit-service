package org.colendi.infra.installment.adapter.redis;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.colendi.domain.installment.port.CachePort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class RedisAdapter implements CachePort {

    RedisTemplate<String, String> redisTemplate;


    private static final long EXPIRATION_TIME = 10;

    @Override
    public void lock(String key) {
        String lockValue = "LOCKED";
        boolean success = Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, lockValue, EXPIRATION_TIME, TimeUnit.SECONDS));

        if (!success) {
            throw new RuntimeException("Lock could not be acquired");
        }
    }

    @Override
    public void unlock(String key) {
        redisTemplate.delete(key);
    }
}
