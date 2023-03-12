# GitHub Wrapper

This is a spring boot microservice that uses the GitHub API to get the most popular repositories. 
It uses the GitHub repositories search API.

https://docs.github.com/en/rest/search?apiVersion=2022-11-28#search-repositories

## Spring Boot profiles
* `default` - default profile for local run
* `local` - profile with local in-memory cache (for caching uses Caffeine)
* `mongo` - profile with dedicated cache based MongoDB (for caching uses custom cache manger _com.github.githubwrapper.cache.CacheManager_)

## Commands for launch
`docker-compose up` - by default uses _mongo_ profile

or

`mvn spring-boot:run`

## Endpoints
_GET http://localhost:8080/repository/search?dateFrom=2019-01-10_

This endpoint returns array of full data from GitHub API. The data doesn't change.

_GET http://localhost:8080/repository/short/search?dateFrom=2019-01-10_

This endpoint returns only names of repositories.

## Parameters
* Integer page - page number _(default: 1, optional)_
* Integer size - page size _(default: 10, optional)_
* SortField sortField - field to sort by _(default: STARS, optional)_ 
* SortOrder sortOrder - sort order _(default: ASC, optional)_ 
* LocalDate dateFrom - filter by creation time > _(format: yyyy-MM-dd, required)_
* Integer starsFrom - matches repositories with at least starsFrom stars >= _(optional)_
* Integer starsUntil - matches repositories with more than starsUntil stars <= _(optional)_
* String language - filter by programing language _(optional)_

## Technology stack
* Java 11
* Spring boot 2.7.9
* Caffeine
* MongoDB
* Docker/Docker-compose
* jsonschema2pojo (for generation DTOs by JSON schema)