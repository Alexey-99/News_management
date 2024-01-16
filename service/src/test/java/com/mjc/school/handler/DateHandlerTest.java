package com.mjc.school.handler;

import com.mjc.school.util.DateFormatter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class DateHandlerTest {
    @InjectMocks
    private DateFormatter dateHandler;

    @ParameterizedTest
    @MethodSource(value = "providerDateTimeFormatterParams")
    void getCurrentDate(DateTimeFormatter dateFormatter, boolean expectedResult) {
        String dateTime = dateHandler.getCurrentDate();
        boolean resultActual = isValid(dateTime, dateFormatter);
        assertEquals(expectedResult, resultActual);
    }

    private boolean isValid(String str, DateTimeFormatter dateFormatter) {
        boolean result;
        try {
            dateFormatter.parse(str);
            result = true;
        } catch (DateTimeParseException e) {
            result = false;
        }
        return result;
    }

    static List<Arguments> providerDateTimeFormatterParams() {
        return List.of(
                Arguments.of(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"),
                        true
                ),
                Arguments.of(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"),
                        false
                )
        );
    }
}