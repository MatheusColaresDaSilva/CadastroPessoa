package com.elotech.cadastropessoa.integration;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

public class CadastroPessoaTestUtil {


    public static ValidatableResponse createRequestGet(String url, RequestSpecification requestSpecification, HttpStatus httpStatus) {
        return RestAssured.given()
                .spec(requestSpecification)
                .when().
                        get(url)
                .then().
                        statusCode(httpStatus.value());
    }

    public static ValidatableResponse createRequestPost(String url, Object body, HttpStatus httpStatus) {
        return RestAssured.given()
        .spec(requestSpecification(body))
        .when().
                post(url)
        .then().
                statusCode(httpStatus.value());
    }

    public static ValidatableResponse createRequestPost(String url, HttpStatus httpStatus) {
        return RestAssured.given()
                .when().
                        post(url)
                .then().
                        statusCode(httpStatus.value());
    }

    public static ValidatableResponse createRequestPut(String url, Object body, HttpStatus httpStatus) {
        return RestAssured.given()
                .spec(requestSpecification(body))
                .when().
                        put(url)
                .then().
                        statusCode(httpStatus.value());
    }

    public static ValidatableResponse createRequestDelete(String url, RequestSpecification requestSpecification, HttpStatus httpStatus) {
        return RestAssured.given()
                .spec(requestSpecification)
                .when().
                        delete(url)
                .then().
                        statusCode(httpStatus.value());
    }

    private static RequestSpecification requestSpecification(Object body) {
        return new RequestSpecBuilder()
                .setContentType("application/json; charset=UTF-8")
                .addHeader("Accept", ContentType.JSON.toString())
                .setBody(body)
                .build();
    }
}
