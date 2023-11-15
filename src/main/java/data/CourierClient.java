package client;

import data.CourierCredentials;
import data.CourierData;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class CourierClient extends RestClient{
    private static final String COURIER_PATH = "/api/v1/courier";
    private static final String LOGIN_PATH = "/api/v1/courier/login";

    public ValidatableResponse createCourier(CourierData courier){
        return given()
                .spec(requestSpecification())
                .and()
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    public ValidatableResponse loginCourier(CourierCredentials courierCredentials){
        return given()
                .spec(requestSpecification())
                .and()
                .body(courierCredentials)
                .when()
                .post(LOGIN_PATH)
                .then();
    }
    public void deleteCourier(int id){
        //удаление, лучше переписать с validatableresponse
    }
}
