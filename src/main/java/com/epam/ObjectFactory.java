package com.epam;

public class ObjectFactory {

    private static ObjectFactory ourInstance = new ObjectFactory();
    public static ObjectFactory getInstance() {
        return ourInstance;
    }
    private Config config = new JavaConfig("com.epam");

    private ObjectFactory() {
    }

    public <T> T createObject(Class<T> type) {
        Class<? extends T> implClass = type;

        if (type.isInterface()) {
            implClass = config.getImplClass(type);
        }

        try {
            return implClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
