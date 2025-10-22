package com.epam;

import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class InjectByTypeAnnotationObjectConfigurator implements ObjectConfigurator {

    @Override
    @SneakyThrows
    public void configure(Object object, ApplicationContext context) {
        Field[] declaredFields = object.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(InjectByType.class)) {
                Class<?> fieldType = field.getType();
                Object toInject =context.getObject(fieldType) ;//ObjectFactory.getInstance().createObject(fieldType);
                field.setAccessible(true);
                field.set(object, toInject);
            }
        }
    }
}
