package com.epam;

import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;

public class ObjectFactory {

    private static ObjectFactory ourInstance = new ObjectFactory();
    public static ObjectFactory getInstance() {
        return ourInstance;
    }
    private Config config;

    private ObjectFactory() {
        config = new JavaConfig("com.epam",
                new HashMap<>(Map.of(Policement.class, AngryPolicement.class)));
    }

    @SneakyThrows
    public <T> T createObject(Class<T> type) {
        Class<? extends T> implClass = type;

        if (type.isInterface()) {
            implClass = config.getImplClass(type);
        }

        return implClass.getDeclaredConstructor().newInstance();
    }
}
