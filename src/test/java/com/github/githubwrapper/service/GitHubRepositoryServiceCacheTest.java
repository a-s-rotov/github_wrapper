package com.github.githubwrapper.service;

import com.github.githubwrapper.component.GitHubFacade;
import com.github.githubwrapper.dto.FullResponseDto;
import com.github.githubwrapper.dto.RequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("local")
class GitHubRepositoryServiceCacheTest {

    @Autowired
    GitHubRepositoryService gitHubRepositoryService;

    @MockBean
    GitHubFacade gitHubFacade;

    @Test
    void cacheFullRequestTest() {
        gitHubRepositoryService.search(new RequestDto(LocalDate.now()));
        gitHubRepositoryService.search(new RequestDto(LocalDate.now()));
        verify(gitHubFacade, times(1)).search(any());
    }

    @Test
    void cacheShortRequestTest() {
        when(gitHubFacade.search(any())).thenReturn(FullResponseDto.builder()
                .items(new ArrayList<>())
                .build());

        gitHubRepositoryService.searchShort(new RequestDto(LocalDate.now()));
        gitHubRepositoryService.searchShort(new RequestDto(LocalDate.now()));
        verify(gitHubFacade, times(1)).search(any());
    }
}
