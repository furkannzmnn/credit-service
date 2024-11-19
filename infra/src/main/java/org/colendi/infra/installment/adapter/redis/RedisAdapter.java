package org.colendi.infra.installment.adapter.redis;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.colendi.domain.config.exception.DomainException;
import org.colendi.domain.config.exception.ErrorCode;
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
        boolean success = Boolean.TRUE.equals(
                redisTemplate.opsForValue().setIfAbsent(generateKey(key), lockValue, EXPIRATION_TIME, TimeUnit.SECONDS)
        );

        if (!success) {
            throw DomainException.builder()
                    .messageKey(ErrorCode.SYSTEM_BUSY.getMessageKey())
                    .build();
        }
    }

    @Override
    public void unlock(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public boolean isLocked(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(generateKey(key)));
    }

    private String generateKey(String key) {
        return "LOCK:" + key;
    }
}
