package com.javidev.ecommerce.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.lang.reflect.Field;
import java.util.Map;

public class Objects {
    public static boolean isNull(Object object) {
        return object == null;
    }

    public static boolean isNotNull(Object object) {
        return object != null;
    }

    public static <T> T merge(T newObject, T oldObject) {
        if(oldObject == null) return newObject;
        Class<?> classObject = newObject.getClass();
        Field[] fields = classObject.getDeclaredFields();
        Object mergedObject = null;
        try{
            mergedObject = classObject.getDeclaredConstructor().newInstance();
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(newObject);
                if (value == null) {
                    value = field.get(oldObject);
                }
                field.set(mergedObject, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) mergedObject;
    }

    //* Parse any object to Map<String, Object>
    public static Map<String, Object> mapper(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return mapper.convertValue(object, Map.class);
    }
}
