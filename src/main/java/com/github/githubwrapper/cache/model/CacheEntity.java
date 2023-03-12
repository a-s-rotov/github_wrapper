package com.github.githubwrapper.cache.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
public class CacheEntity {

    private @NonNull String name;
    private @NonNull Instant creationDate;
    private @Nullable Object object;
}
