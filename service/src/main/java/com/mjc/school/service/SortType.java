package com.mjc.school.service;

public enum SortType {
    ASC, DESC;

    public static String getSortType(String sortType) {
        String currentType = "";
        if (sortType != null && sortType.equalsIgnoreCase(ASC.toString())) {
            return ASC.toString();
        } else {
            currentType = DESC.toString();
        }
        return currentType;
    }
}