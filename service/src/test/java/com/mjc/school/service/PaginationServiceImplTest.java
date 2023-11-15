package com.mjc.school.service;

import com.mjc.school.service.pagination.impl.PaginationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PaginationServiceImplTest {
    @InjectMocks
    private PaginationServiceImpl paginationService;

    @Test
    void calcNumberFirstElement() {
        int numberFirstElementActual = paginationService.calcNumberFirstElement(1, 1);
        int numberFirstElementExpected = 0;
        assertEquals(numberFirstElementExpected, numberFirstElementActual);
    }

    @ParameterizedTest
    @MethodSource(value = "providerMaxNumberPageParams")
    void calcMaxNumberPage(long allElementsSize, int size, int maxNumberPageExpected) {
        int maxNumberPageActual = paginationService.calcMaxNumberPage(allElementsSize, size);
        assertEquals(maxNumberPageExpected, maxNumberPageActual);
    }

    static List<Arguments> providerMaxNumberPageParams() {
        return List.of(
                Arguments.of(1, 1, 1),
                Arguments.of(10, 5, 2),
                Arguments.of(1, 5, 1)
        );
    }
}