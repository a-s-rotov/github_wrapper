package com.github.githubwrapper.util;

import com.github.githubwrapper.dto.SortField;
import com.github.githubwrapper.dto.SortOrder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UrlBuilder {

    private List<String> params = new ArrayList<>();
    private String url;

    public static UrlBuilder builder(@NonNull String url) {
        return new UrlBuilder(url);
    }

    public UrlBuilder(String url) {
        this.url = url;
    }

    public UrlBuilder size(@NonNull Integer size) {
        params.add("per_page=" + size);
        return this;
    }

    public UrlBuilder page(@NonNull Integer page) {
        params.add("page=" + page);
        return this;
    }

    public UrlBuilder sort(@NonNull SortField sort) {
        params.add("sort=" + sort.getValue());

        return this;
    }

    public UrlBuilder order(@NonNull SortOrder order) {
        params.add("order=" + order.getValue());
        return this;
    }

    public UrlBuilder query(@NonNull String query) {
        params.add("q=" + query);
        return this;
    }

    public String build() {
        if (params.stream().noneMatch(item -> item.startsWith("q="))) {
            throw new IllegalArgumentException("The Query parameter is mandatory.");
        }
        return url + "?" + String.join("&", params);
    }

}
