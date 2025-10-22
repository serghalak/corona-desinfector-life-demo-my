package com.epam;

import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DeprecatedHandlerProxyConfigurator implements ProxyConfigurator {
    @Override
    public Object replaceWithProxyIfNeeded(Object t, Class implClass) {
        //use proxy if needed
        if (implClass.isAnnotationPresent(Deprecated.class)) {

            if (implClass.getInterfaces().length == 0) {
                return Enhancer.create(implClass, (net.sf.cglib.proxy.InvocationHandler) (proxy, method, args) -> {
                    return getInvocationHandlerLogic(t, method, args);
                });
            }


            return Proxy.newProxyInstance(implClass.getClassLoader(), implClass.getInterfaces(), (proxy, method, args) -> {
                return getInvocationHandlerLogic(t, method, args);
            });
        }  else {
            return t;
        }
    }

    private static Object getInvocationHandlerLogic(Object t, Method method, Object[] args) throws IllegalAccessException, InvocationTargetException {
        System.out.println("********** что ж ты делаешь урод!! ");
        return method.invoke(t, args);
    }
}
