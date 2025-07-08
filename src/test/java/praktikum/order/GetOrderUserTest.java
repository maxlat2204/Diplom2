package praktikum.order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import praktikum.BaseApiTest;
import praktikum.EnvBody;

import java.util.List;

import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;
import static praktikum.EnvBody.BODY_MESSAGE;
import static praktikum.EnvBody.ERROR_YOU_SHOULD_BE_AUTHORISED;
import static praktikum.steps.OrderSteps.*;
import static praktikum.steps.UserSteps.createUserStep;

public class GetOrderUserTest extends BaseApiTest {
    private final List<String> ingredients = List.of("61c0c5a71d1f82001bdaaa6d");
    OrderModel order = new OrderModel(ingredients);

    @Before
    //Создаем пользователя и заказ через него
    public void createUserAndOrder(){
        accessToken = createUserStep(user)
                .statusCode(HTTP_OK)
                .extract().path(EnvBody.BODY_ACCESS_TOKEN);

        createOrderAuthUserSteps(order, accessToken)
                .statusCode(HTTP_OK);
    }

    @Test
    @DisplayName("Позитивный тест на получение заказа авторизированного пользователя")
    @Description("Проверяем что получается получить заказ авторизированного пользователя")
    public void getOrderAuthorizationUser(){
        getOrderAuthUserStep(accessToken)
                .statusCode(HTTP_OK)
                .body(EnvBody.BODY_SUCCESS, equalTo(true));
    }

    @Test
    @DisplayName("Негативный тест на получение заказа без авторизации")
    @Description("Проверяем что не получается получить заказ без авторизации")
    public void getOrderNoAuthorizationUser(){
        getOrderNoAuthUserStep()
                .statusCode(HTTP_UNAUTHORIZED)
                .body(EnvBody.BODY_SUCCESS, equalTo(false),
                        BODY_MESSAGE, equalTo( ERROR_YOU_SHOULD_BE_AUTHORISED));
    }

}
