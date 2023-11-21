package order;

import data.OrderData;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestOrder {
    private static final String ORDER_POST_CREATE = "/api/v1/orders";
    private static final String ORDER_GET_LIST = "/api/v1/orders";
    @Step("Создание заказа")
        public ValidatableResponse createOrder(OrderData orderData){
        return given()
                .spec(requestSpecification())
                .body(orderData)
                .when()
                .post(ORDER_POST_CREATE)
                .then();
        }
        @Step("Получение списка заказов")
        public ValidatableResponse orderList() {
            return given()
                    .spec(requestSpecification())
                    .when()
                    .get(ORDER_GET_LIST)
                    .then();
        }
}
