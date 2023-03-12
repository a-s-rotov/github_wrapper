package com.github.githubwrapper.component;

import com.github.githubwrapper.dto.FullResponseDto;
import com.github.githubwrapper.dto.RequestDto;
import com.github.githubwrapper.exception.GitHubConnectionException;
import com.github.githubwrapper.util.QueryBuilder;
import com.github.githubwrapper.util.UrlBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class GitHubFacade {

    private final RestTemplate restTemplate;
    @Value("${app.github.url:https://api.github.com/}")
    private String gitHubUrl;
    @Value("${app.github.search_resource:search/repositories}")
    private String searchResource;

    public @NonNull FullResponseDto search(@NonNull RequestDto requestDto) {
        var query = QueryBuilder.builder()
                .date(requestDto.getDateFrom())
                .language(requestDto.getLanguage())
                .stars(requestDto.getStarsFrom(), requestDto.getStarsUntil())
                .build();

        var url = UrlBuilder.builder(gitHubUrl + searchResource)
                .size(requestDto.getSize())
                .page(requestDto.getPage())
                .sort(requestDto.getSortField())
                .order(requestDto.getSortOrder())
                .query(query).build();

        try {
            var response = restTemplate.getForObject(url, FullResponseDto.class);
            if (response == null) {
                throw new GitHubConnectionException("GitHub returned null.");
            }
            return response;
        } catch (RestClientException e) {
            throw new GitHubConnectionException("GitHub connection problem.", e);
        }

    }
}
