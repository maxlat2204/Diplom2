package praktikum.user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import praktikum.BaseApiTest;
import praktikum.EnvBody;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static praktikum.steps.UserSteps.createUserStep;
import static praktikum.steps.UserSteps.loginUserStep;

public class LoginUserTest extends BaseApiTest {

    @Before
    public void createUser(){
        accessToken = createUserStep(user)
                .statusCode(HTTP_OK)
                .body(EnvBody.BODY_SUCCESS, equalTo(true))
                .extract()
                .path(EnvBody.BODY_ACCESS_TOKEN);

        user.setName(null);//убрали у пользователя параметр name, чтобы логиниться
    }

    @Test
    @DisplayName("Позитивный тест на логин валидного пользователя")
    @Description("Успешно логиним существующего пользователя")
    public void loginUser(){
        accessToken = loginUserStep(user)
                .statusCode(HTTP_OK)
                .body(EnvBody.BODY_SUCCESS, equalTo(true))
                .extract()
                .path(EnvBody.BODY_ACCESS_TOKEN);
    }

    @Test
    @DisplayName("Негативный тест на логин пользователя с неправильным Паролем")
    @Description("Пытаемся залогинить существующего пользователя, но с неправильным Паролем")
    public void noValidPasswordLoginUserNegativeTest(){
        user.setPassword("12345");//сделали пароль не корректным
        loginUserStep(user)
                .statusCode(HTTP_UNAUTHORIZED)
                .body(EnvBody.BODY_SUCCESS, equalTo(false),
                        EnvBody.BODY_MESSAGE, equalTo(EnvBody.ERROR_EMAIL_OR_PASSWORD_ARE_INCORRECT));
    }

    @Test
    @DisplayName("Негативный тест на логин пользователя с несуществующей Почтой")
    @Description("Пытаемся залогинить пользователя, несуществующей Почтой")
    public void noValidEmailLoginUserNegativeTest(){
        user.setEmail("miss@ya.ru");//сделали почту не корректной
        loginUserStep(user)
                .statusCode(HTTP_UNAUTHORIZED)
                .body(EnvBody.BODY_SUCCESS, equalTo(false),
                        EnvBody.BODY_MESSAGE, equalTo(EnvBody.ERROR_EMAIL_OR_PASSWORD_ARE_INCORRECT));
    }

}
