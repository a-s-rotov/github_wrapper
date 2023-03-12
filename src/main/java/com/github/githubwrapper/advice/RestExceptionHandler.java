package com.github.githubwrapper.advice;

import com.github.githubwrapper.dto.ApiErrorDto;
import com.github.githubwrapper.exception.GitHubConnectionException;
import com.github.githubwrapper.exception.MongoCacheException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler {

    @ExceptionHandler(GitHubConnectionException.class)
    protected ResponseEntity<Object> handleGitHubConnectionException(GitHubConnectionException ex) {
        return new ResponseEntity(ApiErrorDto.builder()
                .message("GitHub connection problem")
                .timestamp(LocalDateTime.now())
                .debugMessage(ex.getLocalizedMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<Object> handleValidateFileException(BindException ex) {
        return new ResponseEntity(ApiErrorDto.builder()
                .message("Validation error")
                .timestamp(LocalDateTime.now())
                .debugMessage(ex.getLocalizedMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MongoCacheException.class)
    protected ResponseEntity<Object> handleMongoCacheException(MongoCacheException ex) {
        return new ResponseEntity(ApiErrorDto.builder()
                .message("Mongo cache exception")
                .timestamp(LocalDateTime.now())
                .debugMessage(ex.getLocalizedMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
