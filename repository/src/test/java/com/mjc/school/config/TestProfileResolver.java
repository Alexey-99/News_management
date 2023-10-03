package com.mjc.school.config;

import org.springframework.test.context.ActiveProfilesResolver;

import static org.springframework.core.env.AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME;

public class TestProfileResolver /*implements ActiveProfilesResolver*/ {
    private static final String TEST_PROFILE = "test";

//    @Override
//    public String[] resolve(Class<?> testClass) {
//        System.setProperty(ACTIVE_PROFILES_PROPERTY_NAME, TEST_PROFILE);
//        return new String[]{TEST_PROFILE};
//    }
}