package com.biblio.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EnumUtil {
    public static <T extends Enum<T>> String toDisplayName(T enumConstant) {
        try {
            Field displayNameField = enumConstant.getClass().getDeclaredField("getDescription");
            displayNameField.setAccessible(true);
            return (String) displayNameField.get(enumConstant);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException("Enum " + enumConstant.getClass().getName() + " does not have a description field", e);
        }
    }

    public static <T extends Enum<T>> T fromDisplayName(Class<T> enumClass, String displayName) {
        try {
            Field displayNameField = enumClass.getDeclaredField("getDescription");
            displayNameField.setAccessible(true);

            for (T enumConstant : enumClass.getEnumConstants()) {
                String value = (String) displayNameField.get(enumConstant);
                if (value.equalsIgnoreCase(displayName)) {
                    return enumConstant;
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException("Enum " + enumClass.getName() + " does not have a displayName field", e);
        }

        throw new IllegalArgumentException("No enum constant with displayName " + displayName);
    }

    public static <E extends Enum<E>> List<PairUtil> mapEnumToPairUtil(Class<E> enumClass) {
        List<PairUtil> pairList = new ArrayList<>();

        for (E enumConstant : enumClass.getEnumConstants()) {
            String key = enumConstant.name();
            String value = getEnumDescription(enumConstant);

            pairList.add(new PairUtil(key, value));
        }

        return pairList;
    }

    public static <E extends Enum<E>> String getEnumDescription(E enumConstant) {
        try {
            Method method = enumConstant.getClass().getMethod("getDescription");
            return (String) method.invoke(enumConstant);
        } catch (NoSuchMethodException e) {
            return getEnumFallbackValue(enumConstant);
        } catch (Exception e) {
            return enumConstant.name();
        }
    }

    private static <E extends Enum<E>> String getEnumFallbackValue(E enumConstant) {
        return enumConstant.name();
    }
}
