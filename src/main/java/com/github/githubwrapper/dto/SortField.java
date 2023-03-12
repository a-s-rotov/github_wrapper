package com.github.githubwrapper.dto;

import org.springframework.lang.NonNull;

public enum SortField {
    STARS, FORKS, HELP_WANTED_ISSUES, UPDATED;

    public @NonNull String getValue() {
        return toString().toLowerCase();
    }
}
