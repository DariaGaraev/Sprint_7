package login_test;

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
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsEqual.equalTo;

public class LoginCourierTest {
    private CourierClient courierClient;
    private CourierData courierData ;
    private CourierCredentials courierCredentials;
    int courierId;

    @Before
    @Step("Создание тестовых данных курьера")
    public void setUp() {
        courierClient = new CourierClient();
        courierData = CourierGenerator.getRandomCourier();
        courierCredentials = CourierCredentials.from(courierData);
    }
    @After
    @Step("Удаление курьера")
    public void deleteCourier() {
        if (courierId != 0) {
            courierClient.deleteCourier(courierId);
        }
    }
    @Test
    @DisplayName("Логин курьера")
    @Description("Проверка авторизации курьера")
    public void courierLogin(){
        ValidatableResponse response = courierClient.loginCourier(courierCredentials);
        response.assertThat()
                .statusCode(SC_OK)
                .body("id", greaterThan(0))
                .extract()
                .path("id");
        courierId = response.extract().path("id");
    }

    @Test
    @DisplayName("Логин курьера при пустом поле логина")
    @Description("Невозможность войти в систему без логина")
    public void courierLoginWithOutLogin(){
        courierCredentials.setLogin(null);
        ValidatableResponse response = courierClient.loginCourier(courierCredentials);
        response.assertThat().body("message", equalTo("Недостаточно данных для входа")).and().statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Логин курьера при пустом поле пароля")
    @Description("Невозможность войти в систему без пароля")
    public void courierLoginWithOutPassword(){
        courierCredentials.setPassword("");
        ValidatableResponse response = courierClient.loginCourier(courierCredentials);
        response.assertThat().body("message", equalTo("Недостаточно данных для входа")).and().statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Логин курьера при пустых полях логина и пароля")
    @Description("Невозможность курьера войти в систему без пароля и логина")
    public void courierLoginWithOutDate(){
        courierCredentials.setLogin(null);
        courierCredentials.setPassword("");
        ValidatableResponse response = courierClient.loginCourier(courierCredentials);
        response.assertThat().body("message", equalTo("Недостаточно данных для входа")).and().statusCode(SC_BAD_REQUEST);
    }
    @Test
    @DisplayName("Логин курьера с несуществующими логином и паролем")
    @Description("Невозможность войти в систему с несуществующими логином и паролем")
    public void courierLoginNonExistData(){
        courierCredentials.setLogin("BlaBlaLogin123123");
        courierCredentials.setPassword("BlaBlaLogin123123");
        ValidatableResponse response = courierClient.loginCourier(courierCredentials);
        response.assertThat().body("message", equalTo("Учетная запись не найдена")).and().statusCode(SC_NOT_FOUND);
    }
}
