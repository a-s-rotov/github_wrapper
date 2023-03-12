package com.github.githubwrapper.service;

import com.github.githubwrapper.component.GitHubFacade;
import com.github.githubwrapper.dto.FullResponseDto;
import com.github.githubwrapper.dto.RequestDto;
import com.github.githubwrapper.dto.ShortResponseDto;
import com.github.githubwrapper.dto.github.RepositoryResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GitHubRepositoryService {

    private final GitHubFacade gitHubFacade;

    @Cacheable(value = "fullResponse")
    public @NonNull FullResponseDto search(@NonNull RequestDto requestDto) {
        return gitHubFacade.search(requestDto);
    }

    @Cacheable(value = "shortResponse")
    public @NonNull ShortResponseDto searchShort(@NonNull RequestDto requestDto) {
        var response = gitHubFacade.search(requestDto);
        var nameList = response.getItems().stream()
                .map(RepositoryResponseDto::getName)
                .collect(Collectors.toList());
        return ShortResponseDto.builder()
                .names(nameList)
                .build();
    }
}
