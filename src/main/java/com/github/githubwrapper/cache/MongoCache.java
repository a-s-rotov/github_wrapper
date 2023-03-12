package com.github.githubwrapper.cache;

import com.github.githubwrapper.cache.model.CacheEntity;
import com.github.githubwrapper.dto.RequestDto;
import com.github.githubwrapper.exception.MongoCacheException;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.util.concurrent.Callable;

public class MongoCache<K> extends AbstractValueAdaptingCache {

    private final String name;
    private final MongoTemplate mongoTemplate;
    private Class<K> type;

    public MongoCache(@NonNull String name, @NonNull MongoTemplate mongoTemplate, @Nullable Long ttl, @NonNull Class<K> type) {
        super(false);
        this.name = name;
        this.mongoTemplate = mongoTemplate;
        this.type = type;

        if (ttl != null) {
            mongoTemplate
                    .indexOps(name)
                    .ensureIndex(new Index().on("creationDate", Sort.Direction.ASC).expire(ttl));
        }
    }

    @Override
    protected @Nullable Object lookup(Object key) {
        var requestDto = checkKeyClass(key);
        var entity = mongoTemplate.findOne(new Query(Criteria.where("name").is(requestDto.toString())),
                CacheEntity.class, name);
        if (entity != null) {
            return entity.getObject();
        }
        return null;
    }

    @Override
    public @NonNull String getName() {
        return name;
    }

    @Override
    public @NonNull Object getNativeCache() {
        return mongoTemplate;
    }

    @Override
    public <T> @Nullable T get(Object key, Callable<T> valueLoader) {

        ValueWrapper result = get(key);

        if (result != null) {
            return (T) result.get();
        }

        return getSynchronized(key, valueLoader);
    }

    @SuppressWarnings("unchecked")
    private synchronized <T> T getSynchronized(Object key, Callable<T> valueLoader) {

        ValueWrapper result = get(key);

        if (result != null) {
            return (T) result.get();
        }

        T value;
        try {
            value = valueLoader.call();
        } catch (Exception e) {
            throw new ValueRetrievalException(key, valueLoader, e);
        }
        put(key, value);
        return value;
    }

    @Override
    public void put(@NonNull Object key, @NonNull Object value) {
        var requestDto = checkKeyClass(key);
        mongoTemplate.save(CacheEntity.builder()
                .name(requestDto.toString())
                .creationDate(Instant.now())
                .object(checkValueClass(value))
                .build(), name);
    }

    @Override
    public void evict(@NonNull Object key) {
        var requestDto = checkKeyClass(key);
        mongoTemplate.remove(new Query(Criteria.where("name").is(requestDto.toString())),
                name);
    }

    @Override
    public void clear() {
        mongoTemplate.dropCollection(name);
    }

    private RequestDto checkKeyClass(Object obj) {
        if (obj instanceof RequestDto) {
            return (RequestDto) obj;
        } else {
            throw new MongoCacheException("Wrong type of key.");
        }
    }

    private K checkValueClass(Object obj) {
        if (type.isInstance(obj)) {
            return type.cast(obj);
        } else {
            throw new MongoCacheException("Wrong type of value.");
        }
    }
}
