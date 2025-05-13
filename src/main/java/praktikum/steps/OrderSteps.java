package praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import praktikum.EndPoints;
import praktikum.order.OrderModel;

import static io.restassured.RestAssured.given;

public class OrderSteps {

    @Step("Создать заказ без авторизации")
    public static ValidatableResponse createOrderNoAuthUserStep(OrderModel order) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post(EndPoints.ORDER_PATH)
                .then().log().all();
    }

    @Step("Создать заказ через авторизированного пользователя")
    public static ValidatableResponse createOrderAuthUserSteps(OrderModel order, String accessToken) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(EndPoints.ORDER_PATH)
                .then().log().all();
    }

    @Step("Получить заказ авторизированного пользователя")
    public static ValidatableResponse getOrderAuthUserStep(String accessToken) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", accessToken)
                .when()
                .get(EndPoints.ORDER_PATH)
                .then().log().all();
    }

    @Step("Получить заказ без авторизации")
    public static ValidatableResponse getOrderNoAuthUserStep() {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .get(EndPoints.ORDER_PATH)
                .then().log().all();
    }

}
