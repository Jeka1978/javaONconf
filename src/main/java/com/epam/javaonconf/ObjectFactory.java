package com.epam.javaonconf;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ObjectFactory {

    private static final ObjectFactory INSTANCE = new ObjectFactory();
    private Config config;
    private List<ObjectConfigurator> objectConfigurators;
    private List<ProxyConfigurator> proxyConfigurators;
    private Map<Class<?>, Object> singletonInstances = new ConcurrentHashMap<>();

    private ObjectFactory() {
        String basePackageName = System.getProperty("base.package.name", "com.epam");
        config = new Config(basePackageName); // Replace with appropriate configuration setup

        // Initialize the list of ObjectConfigurators
        objectConfigurators = config.getImplementations(ObjectConfigurator.class).stream()
                .map(this::createInstance)
                .collect(Collectors.toList());

        // Initialize the list of ProxyConfigurators
        proxyConfigurators = config.getImplementations(ProxyConfigurator.class).stream()
                .map(this::createInstance)
                .collect(Collectors.toList());
    }

    public static ObjectFactory getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    public <T> T createObject(Class<T> type) {
        if (singletonInstances.containsKey(type)) {
            return (T) singletonInstances.get(type);
        }

        Class<? extends T> implClass = type;
        if (type.isInterface()) {
            implClass = config.getImplementation(type);
        }

        T t = createInstance(implClass);
        if (isSingleton(implClass)) {
            singletonInstances.put(implClass, t);
        }

        configureObject(t);
        initializePostConstruct(t);
        t = wrapWithProxyConfigurators(t, implClass);

        return t;
    }

    private boolean isSingleton(Class<?> implClass) {
        return implClass.isAnnotationPresent(Singleton.class);
    }

    @SneakyThrows
    private <T> T createInstance(Class<? extends T> type) {
        return type.getDeclaredConstructor().newInstance();
    }

    private <T> void configureObject(T t) {
        objectConfigurators.forEach(configurator -> configurator.configure(t));
    }

    private <T> T wrapWithProxyConfigurators(T t, Class<? extends T> type) {
        for (ProxyConfigurator proxyConfigurator : proxyConfigurators) {
            t = proxyConfigurator.configureProxy(t, type);
        }
        return t;
    }

    @SneakyThrows
    private void initializePostConstruct(Object obj) {
        for (Method method : obj.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                method.setAccessible(true);
                method.invoke(obj);
            }
        }
    }
}




