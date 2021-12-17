# Backend Framework

[![Sonarcloud Analyze][github-actions-sonarcloud]][github-actions-url] 
[![Build][github-actions-build]][github-actions-url]
[![Maven Central][maven-image]][maven-url]

Sebagai alat bantu untuk create new Spring Boot Project untuk kasus tersendiri.
Tidak semua package harus di implementation. Pilih beberapa packages saja. karena beberapa package sudah include dengan package lainnya.

### List of Packages
* [Backend Framework Common](backend-framework-common/README.md) : Standard Filter and Response
* [Backend Framework MVC](backend-framework-mvc/README.md) : Configuration for Spring Boot MVC and Response Error
* [Backend Framework Reactive](backend-framework-reactive/README.md) : Configuration for Spring Boot WebFlux and Response Error
* [Backend Framework Swagger](backend-framework-swagger/README.md) : Configuration for Spring Fox Swagger
* [Backend Framework Validation](backend-framework-validation/README.md) : Configuration Validation for Spring Boot
* [Backend Framework Version](backend-framework-version/README.md) : Configuration Version Information

### Analysis of the Backend Framework
[![quality-gate][sonar-quality-gate]][sonar-url]
[![security-rating][sonar-security-rating]][sonar-url]
[![reliability-rating][sonar-reliability-rating]][sonar-url]
[![maintainability-rating][sonar-maintainability-rating]][sonar-url]
[![coverage][sonar-coverage]][sonar-url]
[![bugs][sonar-bugs]][sonar-url] 
[![vulnerabilities][sonar-vulnerabilities]][sonar-url]

### Wakatime
[![wakatime][wakatime-badge]][wakatime-url]

### Setup
```xml
<parent>
    <groupId>io.github.laminalfalah</groupId>
    <artifactId>backend-framework</artifactId>
    <version>...</version>
    <relativePath/>
</parent>
```
or
```xml
<dependecies>
    <dependency>
        <groupId>io.github.laminalfalah</groupId>
        <artifactId>backend-framework-mvc</artifactId>
        <version>${framework.version}</version>
    </dependency>
</dependecies>
```
or
```xml
<dependecies>
    <dependency>
        <groupId>io.github.laminalfalah</groupId>
        <artifactId>backend-framework-reactive</artifactId>
        <version>${framework.version}</version>
    </dependency>
</dependecies>
```

## Example Project

* [Example Backend Framework MVC + JPA][example-mvc-jpa]
* [Example Backend Framework WebFlux + R2DBC][example-webflux-r2dbc]

[maven-image]: https://maven-badges.herokuapp.com/maven-central/io.github.laminalfalah/backend-framework/badge.svg
[maven-url]: https://maven-badges.herokuapp.com/maven-central/io.github.laminalfalah/backend-framework

[github-actions-build]: https://github.com/laminalfalah/backend-framework/actions/workflows/github-actions.yml/badge.svg
[github-actions-sonarcloud]: https://github.com/laminalfalah/backend-framework/actions/workflows/sonarcloud.yml/badge.svg
[github-actions-url]: https://github.com/laminalfalah/backend-framework/actions

[sonar-url]: https://sonarcloud.io/dashboard?id=laminalfalah_backend-framework
[sonar-security-rating]: https://sonarcloud.io/api/project_badges/measure?project=laminalfalah_backend-framework&metric=security_rating
[sonar-reliability-rating]: https://sonarcloud.io/api/project_badges/measure?project=laminalfalah_backend-framework&metric=reliability_rating
[sonar-maintainability-rating]: https://sonarcloud.io/api/project_badges/measure?project=laminalfalah_backend-framework&metric=sqale_rating
[sonar-quality-gate]: https://sonarcloud.io/api/project_badges/measure?project=laminalfalah_backend-framework&metric=alert_status
[sonar-coverage]: https://sonarcloud.io/api/project_badges/measure?project=laminalfalah_backend-framework&metric=coverage
[sonar-bugs]: https://sonarcloud.io/api/project_badges/measure?project=laminalfalah_backend-framework&metric=bugs
[sonar-vulnerabilities]: https://sonarcloud.io/api/project_badges/measure?project=laminalfalah_backend-framework&metric=vulnerabilities

[wakatime-url]: https://wakatime.com/badge/github/laminalfalah/backend-framework
[wakatime-badge]: https://wakatime.com/badge/github/laminalfalah/backend-framework.svg

[example-mvc-jpa]: https://github.com/laminalfalah/example-backend-framework-mvc-jpa
[example-webflux-r2dbc]: https://github.com/laminalfalah/example-backend-framework-webflux-r2dbc
