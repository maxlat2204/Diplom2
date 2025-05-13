package praktikum.order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import praktikum.BaseApiTest;
import praktikum.EnvBody;

import java.util.List;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static praktikum.EnvBody.BODY_MESSAGE;
import static praktikum.EnvConfig.USER_NAME;
import static praktikum.steps.OrderSteps.createOrderAuthUserSteps;
import static praktikum.steps.OrderSteps.createOrderNoAuthUserStep;
import static praktikum.steps.UserSteps.createUserStep;

public class CreateOrderTest extends BaseApiTest {

    private final List<String> ingredients = List.of("61c0c5a71d1f82001bdaaa6d");
    OrderModel order = new OrderModel(ingredients);

    @Test
    @DisplayName("Позитивный тест на создание заказа без авторизации")
    @Description("Проверяем что заказ создается без авторизации")
    public void createOrderNoAuthorization(){
        //Делаем заказ без авторизации
        createOrderNoAuthUserStep(order)
                .statusCode(HTTP_OK)
                .body(EnvBody.BODY_SUCCESS, equalTo(true),
                        "order.number", notNullValue());
    }

    @Test
    @DisplayName("Позитивный тест на создание заказа с авторизацией")
    @Description("Проверяем что заказ создается, через авторизированного пользователя")
    public void createOrderAuthorizationUser(){
        //Создаем пользователя
        accessToken = createUserStep(user)
                .statusCode(HTTP_OK)
                .extract().path(EnvBody.BODY_ACCESS_TOKEN);

        //Делаем заказ через созданного пользователя
        createOrderAuthUserSteps(order, accessToken)
                .statusCode(HTTP_OK)
                .body(EnvBody.BODY_SUCCESS, equalTo(true),
                        "order.number", notNullValue(),
                        "order.owner.name", equalTo(USER_NAME));
    }

    @Test
    @DisplayName("Негативный тест на создание заказа с пустым запросом")
    @Description("Проверяем что заказ с пустым запросом не создается")
    public void createOrderNoIngredients(){
        List<String> noIngredients = List.of();//Создали пустой список
        order.setIngredients(noIngredients);//Передали этот список в заказ

        //Пытаемся создать заказ с пустым запросом
        createOrderNoAuthUserStep(order)
                .statusCode(HTTP_BAD_REQUEST)
                .body(EnvBody.BODY_SUCCESS, equalTo(false),
                        BODY_MESSAGE, equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Негативный тест на создание заказа с неверным хешем ингредиентов")
    @Description("Проверяем что появится ошибка 500, при заказе с неверным хешем ингредиентов")
    public void createOrderNoValidIngredients(){
        List<String> noValidIngredients = List.of("abv");
        order.setIngredients(noValidIngredients);

        createOrderNoAuthUserStep(order)
                .statusCode(HTTP_INTERNAL_ERROR);
    }

}
