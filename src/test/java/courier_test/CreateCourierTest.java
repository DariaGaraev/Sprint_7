package courier_test;

import client.CourierClient;
import data.CourierData;
import data.CourierGenerator;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;

public class CreateCourierTest {
    CourierClient courierClient;
    CourierData courier;
    private int courierId;

    @Before
    public void setup() {
        courierClient = new CourierClient();
        //  courier = new CourierData("login95", "password95", "Daria");
        courier = CourierGenerator.getRandomCourier();
    }

    @After
    public void cleanUp() {
        //написать проверку- если курьер создан - удаляем
        courierClient.deleteCourier(courierId);
    }
    @Test
    public void courierCanBeCreatedTest() {

        ValidatableResponse response = courierClient.createCourier(courier);
        response.assertThat()
                .statusCode(201)
                .body("ok", is(true));

        //  ValidatableResponse responseLogin = courierClient.loginCourier();
        //  responseLogin.assertThat()
        //          .statusCode(HttpStatus.SC_OK)
        // написать, как здесь правильно .body("ok", is(true));
    }
}
