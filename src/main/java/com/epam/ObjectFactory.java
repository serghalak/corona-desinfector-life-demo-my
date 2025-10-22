package com.epam;

import lombok.SneakyThrows;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class ObjectFactory {

    //private static ObjectFactory ourInstance; // = new ObjectFactory();
    private List<ObjectConfigurator> configurators = new ArrayList<>();
    private List<ProxyConfigurator> proxyConfigurators = new ArrayList<>();
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

        for (Class<? extends ProxyConfigurator> cl : context.getConfig().getScanner().getSubTypesOf(ProxyConfigurator.class)) {
           proxyConfigurators.add(cl.getDeclaredConstructor().newInstance());
        }
    }

    @SneakyThrows
    public <T> T createObject(Class<T> implClass) {

        T t = create(implClass);
        configure(t);

        invokeInit(implClass, t);

        t = wrapWithProxyIfNeeded(implClass, t);

        return t;
    }

    private <T> T wrapWithProxyIfNeeded(Class<T> implClass, T t) {
        for (ProxyConfigurator proxyConfigurator : proxyConfigurators) {
            t = (T) proxyConfigurator.replaceWithProxyIfNeeded(t, implClass);
        }
        return t;
    }

    private static <T> void invokeInit(Class<T> implClass, T t) throws IllegalAccessException, InvocationTargetException {
        for (Method method : implClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                method.invoke(t);
            }
        }
    }

    private <T> void configure(T t) {
        configurators.forEach(configurator -> configurator.configure(t, context));
    }

    private static <T> T create(Class<T> implClass) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return implClass.getDeclaredConstructor().newInstance();
    }
}
