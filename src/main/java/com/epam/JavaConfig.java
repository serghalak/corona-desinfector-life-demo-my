package com.epam;

import lombok.Getter;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;

public class JavaConfig implements Config {

    @Getter
    private Reflections scanner;
    private Map<Class, Class> if2ImplClass;

    public JavaConfig(String packageToScan, Map<Class, Class> if2ImplClass) {
        this.scanner = new Reflections(packageToScan);
        this.if2ImplClass = if2ImplClass;
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> type) {
        return if2ImplClass.computeIfAbsent(type, aClass -> {
            Set<Class<? extends T>> classes = scanner.getSubTypesOf(type);
            if (classes.size() != 1) {
                throw new RuntimeException(type + " has 0 or more than one impl please update your config");
            }
            return classes.iterator().next();
        });
    }
}
