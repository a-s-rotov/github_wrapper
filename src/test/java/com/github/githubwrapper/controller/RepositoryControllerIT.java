package com.github.githubwrapper.controller;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;

import static com.github.githubwrapper.constant.UrlConstants.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
The test checks the relevance of the GitHub API.
If the test fails, then the GitHub has changed the parameters.
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
class RepositoryControllerIT {
    @Value(value = "${local.server.port}")
    int port;

    @Autowired
    TestRestTemplate restTemplate;

    final String urlTemplate = "http://localhost:%d/repository%s/search?%s";

    @ParameterizedTest
    @ValueSource(strings = {"", "/short"})
    void callRepositoryEndpoint(String requestType) {
        var result = restTemplate.exchange(
                String.format(urlTemplate, port, requestType, DATE_FROM),
                HttpMethod.GET, null, Object.class);
        assertTrue(result.getStatusCode().is2xxSuccessful());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "/short"})
    void callRepositoryEndpointSize(String requestType) {
        var result = restTemplate.exchange(
                String.format(urlTemplate, port, requestType, DATE_FROM_SIZE_PAGE),
                HttpMethod.GET, null, Object.class);
        assertTrue(result.getStatusCode().is2xxSuccessful());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "/short"})
    void callRepositoryEndpointLanguage(String requestType) {
        var result = restTemplate.exchange(
                String.format(urlTemplate, port, requestType, DATE_FROM_LANGUAGE),
                HttpMethod.GET, null, Object.class);
        assertTrue(result.getStatusCode().is2xxSuccessful());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "/short"})
    void callRepositoryEndpointStars(String requestType) {
        var result = restTemplate.exchange(
                String.format(urlTemplate, port, requestType, DATE_FROM_STARS),
                HttpMethod.GET, null, Object.class);
        assertTrue(result.getStatusCode().is2xxSuccessful());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "/short"})
    void callRepositoryEndpointSort(String requestType) {
        var result = restTemplate.exchange(
                String.format(urlTemplate, port, requestType, DATE_FROM_SORT),
                HttpMethod.GET, null, Object.class);
        assertTrue(result.getStatusCode().is2xxSuccessful());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "/short"})
    void callRepositoryEndpointInvalid(String requestType) {
        var result = restTemplate.exchange(
                String.format(urlTemplate, port, requestType, INVALID_REQUEST),
                HttpMethod.GET, null, Object.class);
        assertTrue(result.getStatusCode().is4xxClientError());
    }

}
