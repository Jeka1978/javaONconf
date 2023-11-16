package com.epam.javaonconf;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Slf4j
public class DeprecatedMethodLoggerCounterProxyConfigurator implements ProxyConfigurator {

    private final Map<Method, Integer> deprecatedMethodUsageCount = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    @Override
    public <T> T configureProxy(T object, Class<? extends T> type) {
        if (containsDeprecatedMethods(type)) {
            return (T) Proxy.newProxyInstance(type.getClassLoader(), type.getInterfaces(), new DeprecatedMethodInvocationHandler(object));
        }
        return object;
    }

    private boolean containsDeprecatedMethods(Class<?> type) {
        return type.isAnnotationPresent(Deprecated.class) ||
                Stream.of(type.getMethods()).anyMatch(method -> method.isAnnotationPresent(Deprecated.class));
    }

    private class DeprecatedMethodInvocationHandler implements InvocationHandler {

        private final Object target;

        public DeprecatedMethodInvocationHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Method actualMethod = findMethodInTargetClass(method, target.getClass());
            if (actualMethod != null && actualMethod.isAnnotationPresent(Deprecated.class)) {
                deprecatedMethodUsageCount.merge(actualMethod, 1, Integer::sum);
                System.out.println("Method " + actualMethod.getName() + " was called " + deprecatedMethodUsageCount.get(actualMethod) + " times");
            }
            return method.invoke(target, args);
        }

        @SneakyThrows
        private Method findMethodInTargetClass(Method proxyMethod, Class<?> targetClass) {
            return targetClass.getMethod(proxyMethod.getName(), proxyMethod.getParameterTypes());
        }

    }
}

