package com.github.githubwrapper.service;

import com.github.githubwrapper.component.GitHubFacade;
import com.github.githubwrapper.dto.FullResponseDto;
import com.github.githubwrapper.dto.RequestDto;
import com.github.githubwrapper.dto.github.RepositoryResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GitHubRepositoryServiceTest {

    @InjectMocks
    GitHubRepositoryService gitHubRepositoryService;

    @Mock
    GitHubFacade gitHubFacade;

    @Test
    void getFullRequest(){
        var repositoryResponse1 = new RepositoryResponseDto();
        repositoryResponse1.setName("name1");

        var repositoryResponse2 = new RepositoryResponseDto();
        repositoryResponse2.setName("name2");

        var fullResponseDto = new FullResponseDto();
        fullResponseDto.setItems(List.of(repositoryResponse1,repositoryResponse2));

        when(gitHubFacade.search(any())).thenReturn(fullResponseDto);
        var response = gitHubRepositoryService.search(new RequestDto(LocalDate.now()));

        assertEquals(fullResponseDto,response);
    }

    @Test
    void getShortRequest(){
        var repositoryResponse1 = new RepositoryResponseDto();
        repositoryResponse1.setName("name1");

        var repositoryResponse2 = new RepositoryResponseDto();
        repositoryResponse2.setName("name2");

        var fullResponseDto = new FullResponseDto();
        fullResponseDto.setItems(List.of(repositoryResponse1,repositoryResponse2));

        when(gitHubFacade.search(any())).thenReturn(fullResponseDto);
        var response = gitHubRepositoryService.searchShort(new RequestDto(LocalDate.now()));

        Assertions.assertThat(response.getNames())
                .isNotEmpty()
                .contains("name1","name2");
    }
}
