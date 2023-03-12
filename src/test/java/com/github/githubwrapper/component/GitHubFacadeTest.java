package com.github.githubwrapper.component;

import com.github.githubwrapper.dto.FullResponseDto;
import com.github.githubwrapper.dto.RequestDto;
import com.github.githubwrapper.dto.github.RepositoryResponseDto;
import com.github.githubwrapper.exception.GitHubConnectionException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GitHubFacadeTest {

    public static final String DEFAULT_URL = "localhost:80/repository?per_page=10&page=1&sort=stars&order=asc&q=created:>";
    @InjectMocks
    GitHubFacade gitHubFacade;

    @Mock
    RestTemplate restTemplate;

    @Test
    void callTest() {
        ReflectionTestUtils.setField(gitHubFacade, "gitHubUrl", "localhost:80/");
        ReflectionTestUtils.setField(gitHubFacade, "searchResource", "repository");

        var localDate = LocalDate.now();

        var repositoryResponse = new RepositoryResponseDto();
        repositoryResponse.setName("name");
        var fullResponseDto = new FullResponseDto();
        fullResponseDto.setItems(List.of(repositoryResponse));

        when(restTemplate.getForObject(DEFAULT_URL + localDate, FullResponseDto.class))
                .thenReturn(fullResponseDto);

        var result = gitHubFacade.search(new RequestDto(localDate));

        Assertions.assertThat(result.getItems())
                .isNotNull()
                .extracting(RepositoryResponseDto::getName)
                .containsExactlyInAnyOrder("name");
    }

    @Test
    void returnNull() {
        ReflectionTestUtils.setField(gitHubFacade, "gitHubUrl", "localhost:80/");
        ReflectionTestUtils.setField(gitHubFacade, "searchResource", "repository");

        var localDate = LocalDate.now();

        when(restTemplate.getForObject(DEFAULT_URL + localDate, FullResponseDto.class))
                .thenReturn(null);

        assertThrows(GitHubConnectionException.class, ()-> gitHubFacade.search(new RequestDto(localDate)));
    }
}
