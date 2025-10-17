package com.epam;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {
    private ObjectFactory factory;
    private Map<Class, Object> context = new ConcurrentHashMap<>();

    public <T> T getObject(Class<T> type) {
        return null;
    }
}
