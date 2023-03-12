package com.github.githubwrapper.util;

import com.github.githubwrapper.dto.SortField;
import com.github.githubwrapper.dto.SortOrder;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UrlBuilderTest {

    @Test
    void convertTest() {
        var result = UrlBuilder.builder("localhost").query("q").build();
        assertEquals("localhost?q=q", result);
    }

    @Test
    void convertInvalidParamTest() {
        assertThrows(IllegalArgumentException.class, () -> UrlBuilder.builder("localhost").build());
    }

    @Test
    void convertWithPaginationTest() {
        var result = UrlBuilder.builder("localhost")
                .query("q")
                .page(10)
                .size(10)
                .build();
        assertEquals("localhost?q=q&page=10&per_page=10", result);
    }

    @Test
    void convertWithStarTest() {
        var result = UrlBuilder.builder("localhost")
                .query("q")
                .sort(SortField.STARS)
                .order(SortOrder.ASC)
                .build();
        assertEquals("localhost?q=q&sort=stars&order=asc", result);
    }
}
