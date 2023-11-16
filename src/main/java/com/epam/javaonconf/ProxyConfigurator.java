package com.epam.javaonconf;

public interface ProxyConfigurator {
    @SuppressWarnings("unchecked")
    <T> T configureProxy(T object, Class<? extends T> type);
}
