package com.github.githubwrapper.component;

import com.github.githubwrapper.dto.SortOrder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SortOrderConverter implements Converter<String, SortOrder> {

    @Override
    public SortOrder convert(String value) {
        return SortOrder.valueOf(value.toUpperCase());
    }
}