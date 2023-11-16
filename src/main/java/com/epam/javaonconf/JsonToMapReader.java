package com.epam.javaonconf;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class JsonToMapReader {

    private final Map<Class<?>, Class<?>> interfaceToImplementationMap;
    private final ObjectMapper objectMapper;

    public JsonToMapReader() {
        this.interfaceToImplementationMap = new HashMap<>();
        this.objectMapper = new ObjectMapper();
    }

    @SneakyThrows
    public Map<Class<?>, Class<?>> readJsonToMap(String fileName) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: " + fileName);
            }

            Map<String, String> stringMap = objectMapper.readValue(inputStream, new TypeReference<>() {});
            for (Map.Entry<String, String> entry : stringMap.entrySet()) {
                Class<?> interfaceType = Class.forName(entry.getKey());
                Class<?> implementationType = Class.forName(entry.getValue());

                interfaceToImplementationMap.put(interfaceType, implementationType);
            }
        }
        return interfaceToImplementationMap;
    }
}

