package com.github.githubwrapper.dto;

import com.github.githubwrapper.dto.github.RepositoryResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullResponseDto {
    private @NonNull List<RepositoryResponseDto> items = new ArrayList<>();
}
