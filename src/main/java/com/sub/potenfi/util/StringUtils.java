package com.sub.potenfi.util;

public class StringUtils {
    // 문자열이 null이거나 공백인지를 확인하는 메서드
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }    
}
