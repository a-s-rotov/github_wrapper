package com.github.githubwrapper.cache;

import com.github.githubwrapper.dto.FullResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MongoCacheManagerTest {
    @Mock
    MongoCache mongoCache;

    @Test
    void createCacheManagerTest() {
        when(mongoCache.getName()).thenReturn("name");
        var cacheManager = new MongoCacheManager(List.of(mongoCache));

        assertEquals(mongoCache, cacheManager.getCache("name"));
        assertEquals(List.of("name"), cacheManager.getCacheNames());
    }
}
