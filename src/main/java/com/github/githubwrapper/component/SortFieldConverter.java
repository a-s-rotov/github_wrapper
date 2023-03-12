package com.github.githubwrapper.component;

import com.github.githubwrapper.dto.SortField;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SortFieldConverter implements Converter<String, SortField> {

    @Override
    public SortField convert(String value) {
        return SortField.valueOf(value.toUpperCase());
    }
}