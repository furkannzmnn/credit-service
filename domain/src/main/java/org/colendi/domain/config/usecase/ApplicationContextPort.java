package org.colendi.domain.config.usecase;

import java.util.Map;

public interface ApplicationContextPort {

    <T> Map<String, Object> getBeansWithAnnotation(Class<T> annotation);
}
