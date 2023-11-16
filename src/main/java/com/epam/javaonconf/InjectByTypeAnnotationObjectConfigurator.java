package com.epam.javaonconf;

import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class InjectByTypeAnnotationObjectConfigurator implements ObjectConfigurator {

    @Override
    @SneakyThrows
    public void configure(Object obj) {
        Class<?> implClass = obj.getClass();
        for (Field field : implClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectByType.class)) {
                field.setAccessible(true);
                Object value = ObjectFactory.getInstance().createObject(field.getType());
                field.set(obj, value);
            }
        }
    }
}

