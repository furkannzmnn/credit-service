package org.colendi.domain.installment.port;

public interface CachePort {
    void lock(String key);
    void unlock(String key);
}
