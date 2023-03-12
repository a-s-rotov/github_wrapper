package com.github.githubwrapper.cache;

import com.github.githubwrapper.cache.model.CacheEntity;
import com.github.githubwrapper.dto.FullResponseDto;
import com.github.githubwrapper.dto.RequestDto;
import com.github.githubwrapper.exception.MongoCacheException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MongoCacheTest {

    public static final String FULL_RESPONSE = "fullResponse";
    @Mock
    MongoTemplate mongoTemplate;

    @Test
    void setTtl() {
        var indexOperationMock = mock(IndexOperations.class);
        when(mongoTemplate.indexOps(FULL_RESPONSE)).thenReturn(indexOperationMock);

        new MongoCache<>(FULL_RESPONSE, mongoTemplate, 10L, FullResponseDto.class);

        var indexCaptor = ArgumentCaptor.forClass(Index.class);
        verify(indexOperationMock).ensureIndex(indexCaptor.capture());

        var index = indexCaptor.getValue();
        assertEquals(10L, index.getIndexOptions().get("expireAfterSeconds"));
    }

    @Test
    void putEntity() {
        var cache = new MongoCache<>(FULL_RESPONSE, mongoTemplate, null, FullResponseDto.class);

        cache.put(new RequestDto(LocalDate.now()), new FullResponseDto());

        verify(mongoTemplate).save(any(), eq(FULL_RESPONSE));
    }

    @Test
    void putEntityInvalidKey() {
        var cache = new MongoCache<>(FULL_RESPONSE, mongoTemplate, null, FullResponseDto.class);

        assertThrows(MongoCacheException.class, () -> cache.put("str", new FullResponseDto()));
    }

    @Test
    void putEntityInvalidValue() {
        var cache = new MongoCache<>(FULL_RESPONSE, mongoTemplate, null, FullResponseDto.class);

        assertThrows(MongoCacheException.class, () -> cache.put(new RequestDto(LocalDate.now()), "str"));
    }

    @Test
    void lookupEntity() {
        var cache = new MongoCache<>(FULL_RESPONSE, mongoTemplate, null, FullResponseDto.class);
        when(mongoTemplate.findOne(any(),eq(CacheEntity.class),eq(FULL_RESPONSE)))
                .thenReturn(new CacheEntity(FULL_RESPONSE, Instant.now(), new FullResponseDto()));

        var result = cache.lookup(new RequestDto(LocalDate.now()));
        assertNotNull(result);
    }

    @Test
    void getEntity() {
        var cache = new MongoCache<>(FULL_RESPONSE, mongoTemplate, null, FullResponseDto.class);
        when(mongoTemplate.findOne(any(),eq(CacheEntity.class),eq(FULL_RESPONSE)))
                .thenReturn(new CacheEntity(FULL_RESPONSE, Instant.now(), new FullResponseDto()));

        var callable = mock(Callable.class);
        var result = cache.get(new RequestDto(LocalDate.now()),callable);
        assertNotNull(result);
    }

    @Test
    void getMissEntity() throws Exception {
        var cache = new MongoCache<>(FULL_RESPONSE, mongoTemplate, null, FullResponseDto.class);
        when(mongoTemplate.findOne(any(),eq(CacheEntity.class),eq(FULL_RESPONSE)))
                .thenReturn(null);

        var valueLoader = mock(Callable.class);
        when(valueLoader.call()).thenReturn(new FullResponseDto());

        var result = cache.get(new RequestDto(LocalDate.now()),valueLoader);
        assertNotNull(result);
    }

    @Test
    void lookupEntityMiss() {
        var cache = new MongoCache<>(FULL_RESPONSE, mongoTemplate, null, FullResponseDto.class);
        when(mongoTemplate.findOne(any(),eq(CacheEntity.class),eq(FULL_RESPONSE)))
                .thenReturn(null);

        var result = cache.lookup(new RequestDto(LocalDate.now()));
        assertNull(result);
    }

    @Test
    void evictByKey() {
        var cache = new MongoCache<>(FULL_RESPONSE, mongoTemplate, null, FullResponseDto.class);

        cache.evict(new RequestDto(LocalDate.now()));
        verify(mongoTemplate).remove(any(),eq(FULL_RESPONSE));
    }

    @Test
    void clear() {
        var cache = new MongoCache<>(FULL_RESPONSE, mongoTemplate, null, FullResponseDto.class);

        cache.clear();
        verify(mongoTemplate).dropCollection(FULL_RESPONSE);
    }
}
