package com.example.client;

import com.example.data.CourierCredentials;
import com.example.data.CourierData;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class CourierClient extends RestClient{
    private static final String COURIER_PATH = "/api/v1/courier";
    private static final String LOGIN_PATH = "/api/v1/courier/login";
    public static String COURIER_DELETE = "/api/v1/courier/:id";
    @Step("Создание нового курьера")
    public ValidatableResponse createCourier(CourierData courier){
        return given()
                .spec(requestSpecification())
                .and()
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }
    @Step("Вход курьера в систему")
    public ValidatableResponse loginCourier(CourierCredentials courierCredentials){
        return given()
                .spec(requestSpecification())
                .and()
                .body(courierCredentials)
                .when()
                .post(LOGIN_PATH)
                .then();
    }
    @Step("Удаление курьера")
    public ValidatableResponse deleteCourier(int id){
        return given()
                .spec(requestSpecification())
                .delete(COURIER_DELETE + id)
                .then();
    }
}
