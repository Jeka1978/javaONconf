package com.epam.javaonconf;

public interface ProxyConfigurator {
    <T> T configureProxy(T t, Class<? extends T> type);
}
