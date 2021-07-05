# Backend Framework

[![Build](https://github.com/laminalfalah/backend-framework/actions/workflows/github-actions.yml/badge.svg)](https://github.com/laminalfalah/backend-framework/actions/workflows/github-actions.yml)

Sebagai alat bantu untuk create new Spring Boot Project untuk kasus tersendiri.

## List of Packages
* [Backend Framework Common](backend-framework-common/README.md) : Standard Filter and Response
* [Backend Framework MVC](backend-framework-mvc/README.md) : Configuration for Spring Boot MVC and Response Error
* [Backend Framework OAuth2](backend-framework-oauth/README.md) : OAuth Helper with Provider Google, Facebook
* [Backend Framework Reactive](backend-framework-reactive/README.md) : Configuration for Spring Boot WebFlux and Response Error
* [Backend Framework Security](backend-framework-security/README.md) : Configuration for Spring Boot Security MVC and Webflux and Filter Header
* [Backend Framework Swagger](backend-framework-swagger/README.md) : Configuration for Spring Fox Swagger
* [Backend Framework Validation](backend-framework-validation/README.md) : Open Api Swagger
* [Backend Framework Version](backend-framework-version/README.md) : Version Information

Tidak semua package harus di implementation. Pilih beberapa packages saja. karena beberapa package sudah include dengan package lainnya.

## Setup
```xml
<parent>
    <groupId>io.github.laminalfalah</groupId>
    <artifactId>backend-framework</artifactId>
    <version>...</version>
    <relativePath/>
</parent>
```

## Example Project

* [Example Backend Framework MVC + JPA](https://github.com/laminalfalah/example-backend-framework-mvc-jpa)
* [Example Backend Framework WebFlux + R2DBC](https://github.com/laminalfalah/example-backend-framework-webflux-r2dbc)