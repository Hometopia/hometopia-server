package com.hometopia.commons.utils;

import java.util.function.Supplier;

public class HometopiaUtils {

    public static <T> T tryGet(Supplier<T> supplier, T defaultValue) {
        try {
            return supplier.get();
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
