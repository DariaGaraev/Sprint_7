package order_test;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.OrderClient;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.apache.http.HttpStatus.SC_OK;
public class ListOrderTest {
    private OrderClient orderClient;
    @Test
    @DisplayName("Получение списка заказов")
    @Description("Успешное получение списка заказов")
    public void orderList() {
        orderClient = new OrderClient();
        ValidatableResponse response = orderClient.orderList();
        response.assertThat()
                .statusCode(SC_OK)
                .body("orders", notNullValue());
    }
}
