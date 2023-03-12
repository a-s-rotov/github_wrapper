package com.github.githubwrapper.controller;

import com.github.githubwrapper.dto.FullResponseDto;
import com.github.githubwrapper.dto.RequestDto;
import com.github.githubwrapper.dto.ShortResponseDto;
import com.github.githubwrapper.service.GitHubRepositoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/repository")
@AllArgsConstructor
public class RepositoryController {

    private final GitHubRepositoryService gitHubRepositoryService;

    @GetMapping("/search")
    public FullResponseDto search(@Valid RequestDto requestDto) {
        return gitHubRepositoryService.search(requestDto);
    }

    @GetMapping("/short/search")
    public ShortResponseDto searchShort(@Valid RequestDto requestDto) {
        return gitHubRepositoryService.searchShort(requestDto);
    }
}
