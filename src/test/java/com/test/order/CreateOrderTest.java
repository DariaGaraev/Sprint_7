package com.test.order;

import com.example.data.OrderData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import com.example.order.OrderClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.apache.http.HttpStatus.SC_CREATED;

import java.util.List;

import static org.hamcrest.Matchers.greaterThan;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private OrderClient orderClient;
    private OrderData orderData;
    private final List<String> color;
    public CreateOrderTest(List<String> color) {
        this.color = color;
    }
    @Parameterized.Parameters (name = "Color Scooter - {0}")
    public static Object[][] dataGen() {
        return new Object[][] {
                {List.of("BLACK", "GREY")},
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of()}
        };
    }
    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }
    @Test
    @DisplayName("Заказ самокатов разных цветов")
    @Description("Цвет самоката")
    public void orderCreateColorWithParam() {
        orderData = new OrderData(color);
        ValidatableResponse response = orderClient.createOrder(orderData);
        response.assertThat()
                .statusCode(SC_CREATED)
                .body("track", greaterThan(0))
                .extract()
                .path("track");
    }
}
