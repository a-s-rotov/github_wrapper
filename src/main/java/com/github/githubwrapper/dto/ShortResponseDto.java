package com.github.githubwrapper.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ShortResponseDto {
    private @Nullable List<String> names;
}
