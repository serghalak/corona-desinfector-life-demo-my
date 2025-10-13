package com.epam;

import org.reflections.Reflections;

import java.util.Set;

public class JavaConfig implements Config {

    private Reflections scanner;

    public JavaConfig(String packageToScan) {
        this.scanner = new Reflections(packageToScan);
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> type) {
        Set<Class<? extends T>> classes = scanner.getSubTypesOf(type);
        if (classes.size() != 1) {
            throw new RuntimeException(type + " has 0 or more than one impl");
        }
        return classes.iterator().next();
    }
}
