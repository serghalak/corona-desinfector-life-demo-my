package com.epam;

import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectFactory {

    private static ObjectFactory ourInstance = new ObjectFactory();
    private List<ObjectConfigurator> configurators = new java.util.ArrayList<>();
    public static ObjectFactory getInstance() {
        return ourInstance;
    }
    private Config config;

    @SneakyThrows
    private ObjectFactory() {
        config = new JavaConfig("com.epam",
                new HashMap<>(Map.of(Policement.class, AngryPolicement.class)));
        for (Class<? extends ObjectConfigurator> cl : config.getScanner().getSubTypesOf(ObjectConfigurator.class)) {
            configurators.add(cl.getDeclaredConstructor().newInstance());
        }
    }

    @SneakyThrows
    public <T> T createObject(Class<T> type) {
        Class<? extends T> implClass = type;

        if (type.isInterface()) {
            implClass = config.getImplClass(type);
        }
        T t = implClass.getDeclaredConstructor().newInstance();
        configurators.forEach(configurator -> configurator.configure(t));
        return t;
    }
}
