package ru.yandex.practicum.scooter.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BaseApiClient {

    public static RequestSpecification getRequestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri("http://qa-scooter.praktikum-services.ru")
                .log(LogDetail.ALL)
                .setContentType(ContentType.JSON).build();
    }
}
