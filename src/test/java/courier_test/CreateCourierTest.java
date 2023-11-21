package courier_test;

import client.CourierClient;

import data.CourierCredentials;
import data.CourierData;
import data.CourierGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.*;


public class CreateCourierTest {
    CourierClient courierClient;
    CourierData courier;
     int courierId;


    @Before
    @Step("Создание тестовых данных курьера")
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandomCourier();
      }
    @After
    @Step("Удаление тестовых данных курьера")
    public void cleanUp(){
        if(courierId != 0) {
            courierClient.deleteCourier(courierId);
        }
    }
    @Test
    @DisplayName("Создание нового курьера")
    @Description("Возможность создания нового курьера")
    public void courierCanBeCreatedTest() {
        ValidatableResponse response = courierClient.createCourier(courier);
        response.assertThat()
                .statusCode(SC_CREATED)
                .body("ok", is(true));
        courierId = courierClient.loginCourier(CourierCredentials.from(courier)).extract().jsonPath().getInt("id");
    }
    @Test
    @DisplayName("Создание курьера с существующим логином")
    @Description("Невозможность создания курьера с существующим логином")
    public void impossibleCrteationTheSameCourier() {
        courierClient.createCourier(courier);
        ValidatableResponse response = courierClient.createCourier(courier);
        courierId = courierClient.loginCourier(CourierCredentials.from(courier)).extract().jsonPath().getInt("id");
        response.assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой.")).and().statusCode(SC_CONFLICT);
    }
    @Test
    @DisplayName("Создание курьера при пустом поле логина")
    @Description("Невозможность создания курьера без логина")
    public void courierCrteationWithoutLogin(){
        courier.setLogin(null);
        ValidatableResponse response = courierClient.createCourier(courier);
        response.assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи")).and().statusCode(SC_BAD_REQUEST);
    }
    @Test
    @DisplayName("Создание курьера при пустом поле пароля")
    @Description("Невозможность создания курьера без пароля")
    public void courierCrteationWithoutPassword(){
        courier.setPassword(null);
        ValidatableResponse response = courierClient.createCourier(courier);
        response.assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи")).and().statusCode(SC_BAD_REQUEST);
    }
    @Test
    @DisplayName("Создание курьера при пустых полях пароля и логина")
    @Description("Невозможность создания курьера без логина и пароля")
    public void courierCreateWithoutDate() {
        courier.setLogin(null);
        courier.setPassword(null);
        ValidatableResponse response = courierClient.createCourier(courier);
        response.assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи")).and().statusCode(SC_BAD_REQUEST);
    }

}