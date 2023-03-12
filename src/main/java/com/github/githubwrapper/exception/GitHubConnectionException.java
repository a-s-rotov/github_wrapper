package com.github.githubwrapper.exception;

public class GitHubConnectionException extends RuntimeException {

    public GitHubConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public GitHubConnectionException(String message) {
        super(message);
    }
}
