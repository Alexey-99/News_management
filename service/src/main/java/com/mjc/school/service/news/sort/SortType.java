package com.mjc.school.service.news.sort;

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