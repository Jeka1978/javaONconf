package com.epam.javaonconf;

import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Random;

public class InjectRandomIntAnnotationObjectConfigurator implements ObjectConfigurator {

    private final Random random;

    public InjectRandomIntAnnotationObjectConfigurator() {
        this.random = new Random();
    }

    @SneakyThrows
    @Override
    public void configure(Object obj) {
        Class<?> objClass = obj.getClass();
        Field[] fields = objClass.getDeclaredFields();

        for (Field field : fields) {
            InjectRandomInt annotation = field.getAnnotation(InjectRandomInt.class);
            if (annotation != null) {
                int min = annotation.min();
                int max = annotation.max();
                int randomValue = min + random.nextInt(max - min);
                field.setAccessible(true);
                field.set(obj, randomValue);
            }
        }
    }
}

