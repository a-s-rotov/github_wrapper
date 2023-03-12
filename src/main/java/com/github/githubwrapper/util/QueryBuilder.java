package com.github.githubwrapper.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QueryBuilder {

    private List<String> params = new ArrayList<>();

    public static QueryBuilder builder() {
        return new QueryBuilder();
    }

    public QueryBuilder date(@NonNull LocalDate date) {
        params.add("created:>" + date);
        return this;
    }

    public QueryBuilder language(@Nullable String language) {
        if (StringUtils.hasText(language)) {
            params.add("language:" + language);
        }
        return this;
    }

    public QueryBuilder stars(@Nullable Integer starsFrom, @Nullable Integer starsUntil) {
        var starsQuery = buildStarQuery(starsFrom, starsUntil);

        if (StringUtils.hasText(starsQuery)) {
            params.add("star:" + starsQuery);
        }
        return this;
    }

    public String build() {
        if (params.stream().noneMatch(item -> item.startsWith("created:>"))) {
            throw new IllegalArgumentException("The Date From parameter is mandatory.");
        }
        return String.join("+", params);
    }

    private static String buildStarQuery(Integer starsFrom, Integer starsUntil) {
        if (starsFrom != null && starsUntil != null) {
            return starsFrom + ".." + starsUntil;
        } else if (starsUntil != null) {
            return "<=" + starsUntil;
        } else if (starsFrom != null) {
            return ">=" + starsFrom;
        } else {
            return null;
        }
    }


}
