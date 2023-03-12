package com.github.githubwrapper.cache;

import lombok.AllArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class MongoCacheManager implements CacheManager {

    private @NonNull List<MongoCache<?>> mongoCacheList;

    @Override
    public Cache getCache(@NonNull String name) {
        return mongoCacheList.stream()
                .filter(mongoCache -> name.equals(mongoCache.getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Wrong cache name."));
    }

    @Override
    public Collection<String> getCacheNames() {
        return mongoCacheList.stream()
                .map(MongoCache::getName)
                .collect(Collectors.toList());
    }
}
