package com.mjc.school.converter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class UserConverterTest {
    @Autowired
    private UserConverter userConverter;

    @Test
    void toUserDetails() {
        assert 2 + 3 != 4;
    }

    @Test
    void fromRegistrationUserDTO() {
    }
}