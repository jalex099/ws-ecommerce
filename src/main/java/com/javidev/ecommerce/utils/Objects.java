package com.javidev.ecommerce.utils;

import java.lang.reflect.Field;

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
}
