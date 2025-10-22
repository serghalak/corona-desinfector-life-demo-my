package com.epam;

import lombok.Setter;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectFactory {

    //private static ObjectFactory ourInstance; // = new ObjectFactory();
    private List<ObjectConfigurator> configurators = new ArrayList<>();
    private final ApplicationContext context;
//    public static ObjectFactory getInstance() {
//        return ourInstance;
//    }
    //private Config config;

    @SneakyThrows
    public ObjectFactory(ApplicationContext context) {
//        config = new JavaConfig("com.epam",
//                new HashMap<>(Map.of(Policement.class, AngryPolicement.class)));
        this.context = context;
        for (Class<? extends ObjectConfigurator> cl : context.getConfig().getScanner().getSubTypesOf(ObjectConfigurator.class)) {
            configurators.add(cl.getDeclaredConstructor().newInstance());
        }
    }

    @SneakyThrows
    public <T> T createObject(Class<T> implClass) {

        T t = implClass.getDeclaredConstructor().newInstance();
        configurators.forEach(configurator -> configurator.configure(t, context));
        return t;
    }
}
