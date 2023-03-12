package com.github.githubwrapper.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class QueryBuilderTest {

    @Test
    void convertTest() {
        var localDate = LocalDate.now();
        var result = QueryBuilder.builder().date(localDate).build();
        assertEquals("created:>" + localDate, result);
    }

    @Test
    void convertInvalidParamTest() {
        assertThrows(IllegalArgumentException.class, () -> QueryBuilder.builder().build());
    }

    @Test
    void convertWithLanguageTest() {
        var localDate = LocalDate.now();
        var result = QueryBuilder.builder()
                .date(localDate)
                .language("java")
                .build();
        assertEquals("created:>" + localDate+"+language:java", result);
    }

    @Test
    void convertWithStarTest() {
        var localDate = LocalDate.now();
        var firstResult = QueryBuilder.builder()
                .date(localDate)
                .stars(10, 10)
                .build();
        assertEquals("created:>" + localDate+"+star:10..10", firstResult);

        var secondResult = QueryBuilder.builder()
                .date(localDate)
                .stars(10, null)
                .build();
        assertEquals("created:>" + localDate+"+star:>=10", secondResult);

        var thirdResult = QueryBuilder.builder()
                .date(localDate)
                .stars(null, 10)
                .build();
        assertEquals("created:>" + localDate+"+star:<=10", thirdResult);
    }
}
