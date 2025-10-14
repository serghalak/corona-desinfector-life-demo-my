package com.epam;

import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

public class InjectPropertyAnnotationObjectConfigurator implements ObjectConfigurator {

    private Properties properties = new Properties();

    @SneakyThrows
    public InjectPropertyAnnotationObjectConfigurator() {
        try (InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("application.properties")) {
            if (is != null) {
                properties.load(is);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @SneakyThrows
    public void configure(Object object) {
//        Class<?> implClass = object.getClass();
//        for (Field field : implClass.getDeclaredFields()) {
//            InjectProperty injectProperty = field.getAnnotation(InjectProperty.class);
//            if (injectProperty != null) {
//                String value = System.getProperty(injectProperty.value());
//                if (value == null) {
//                    value = injectProperty.value();
//                }
//                field.setAccessible(true);
//                try {
//                    field.set(object, value);
//                } catch (IllegalAccessException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//
//        }

        // Inject properties for fields annotated with @InjectProperty
        Class<?> implClass = object.getClass();
        for (Field field : implClass.getDeclaredFields()) {
            InjectProperty annotation = field.getAnnotation(InjectProperty.class);
            if (annotation != null) {
                String key = annotation.value().isEmpty() ? field.getName() : annotation.value();
                String value = properties.getProperty(key);
                if (value != null) {
                    field.setAccessible(true);
                    // Only basic String injection as per current usage
                    if (field.getType().equals(String.class)) {
                        field.set(object, value);
                    } else {
                        // Try simple conversion for primitives/wrappers if needed in future
                        field.set(object, value);
                    }
                }
            }
        }
    }



    /**
     * Returns the absolute filesystem path to application.properties if available on the filesystem.
     * If the resource is inside a JAR, returns the URI string to the resource.
     * Returns null if the resource is not found.
     */
//    public String getApplicationPropertiesPath() {
//        try {
//            var url = Thread.currentThread().getContextClassLoader().getResource("application.properties");
//            if (url == null) return null;
//            // Try to convert to a file system path when possible
//            if ("file".equalsIgnoreCase(url.getProtocol())) {
//                return new java.io.File(url.toURI()).getAbsolutePath();
//            }
//            // Fallback: return URL/URI as string (e.g., jar:file:...)
//            return url.toURI().toString();
//        } catch (Exception e) {
//            return null;
//        }
//    }
}
