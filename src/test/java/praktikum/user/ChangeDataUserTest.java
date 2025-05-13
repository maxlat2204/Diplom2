package praktikum.user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import praktikum.BaseApiTest;
import praktikum.EnvBody;

import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;
import static praktikum.EnvConfig.*;
import static praktikum.steps.UserSteps.*;

public class ChangeDataUserTest extends BaseApiTest {
    String emailChange = faker.internet().emailAddress();
    String nameChange = faker.name().username();

    @Before
    public void createUser(){
        accessToken = createUserStep(user)
                .statusCode(HTTP_OK)
                .body(EnvBody.BODY_SUCCESS, equalTo(true))
                .extract()
                .path(EnvBody.BODY_ACCESS_TOKEN);

        user.setPassword(null);//убрали у пользователя параметр password, чтобы менять данные пользователя
        user.setEmail(emailChange);//изменили почту
        user.setName(nameChange);// изменили имя пользователя
    }

    @Test
    @DisplayName("Позитивный тест на изменение данных пользователя")
    @Description("Успешно меняем данные пользователя: почту и имя пользователя")
    public void changeDataUser(){
        changeUserStep(user, accessToken)
                .statusCode(HTTP_OK)
                .body(EnvBody.BODY_SUCCESS, equalTo(true),
                         "user.email", equalTo(emailChange),
                        "user.name", equalTo(nameChange));
    }



    @Test
    @DisplayName("Негативный тест на изменение данных пользователя без авторизации")
    @Description("Пытаемся изменить данные пользователя без авторизации: почту и имя пользователя")
    public void changeDataNoAuthorizationUserNegativeTest(){
        String accessTokenNoValid = "noValidToken";//создали не валидный токен, чтобы не прошла авторизация

        changeUserStep(user, accessTokenNoValid)
                .statusCode(HTTP_UNAUTHORIZED)
                .body(EnvBody.BODY_SUCCESS, equalTo(false),
                        EnvBody.BODY_MESSAGE, equalTo(EnvBody.ERROR_YOU_SHOULD_BE_AUTHORISED));
    }

}
