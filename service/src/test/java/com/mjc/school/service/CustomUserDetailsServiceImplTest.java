package com.mjc.school.service;

import com.mjc.school.service.user.impl.CustomUserDetailsServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceImplTest {
    @InjectMocks
    private CustomUserDetailsServiceImpl customUserDetailsService;
}