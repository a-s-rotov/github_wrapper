package com.github.githubwrapper.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Data
public class RequestDto {

    @NonNull
    private Integer page = 1;
    @NonNull
    @Max(100)
    @Min(1)
    private Integer size = 10;
    @NonNull
    private SortField sortField = SortField.STARS;
    @NonNull
    private SortOrder sortOrder = SortOrder.ASC;
    @NonNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFrom;
    @Nullable
    private Integer starsFrom;
    @Nullable
    private Integer starsUntil;
    @Nullable
    private String language;
}
