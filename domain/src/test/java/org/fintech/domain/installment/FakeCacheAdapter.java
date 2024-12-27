package org.fintech.domain.installment;

import lombok.Getter;
import org.fintech.domain.installment.port.CachePort;

import java.util.HashMap;
import java.util.Map;

public class FakeCacheAdapter implements CachePort {

    private final Map<String, String> cache = new HashMap<>();

    @Getter
    private boolean lockCalled = false;

    @Override
    public void lock(String key) {
        lockCalled = true;
        cache.put(key, "LOCKED");
    }

    @Override
    public void unlock(String key) {
        cache.remove(key);
    }

    @Override
    public boolean isLocked(String key) {
        return cache.containsKey(key);
    }


    public boolean wasLockCalled() {
        return lockCalled;
    }

    public void reset() {
        lockCalled = false;
    }

}
