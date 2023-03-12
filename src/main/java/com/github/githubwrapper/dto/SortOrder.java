package com.github.githubwrapper.dto;

import org.springframework.lang.NonNull;

public enum SortOrder {
    ASC, DESC;

    public @NonNull String getValue() {
        return toString().toLowerCase();
    }
}
