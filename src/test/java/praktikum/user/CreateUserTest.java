package praktikum.user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import praktikum.BaseApiTest;
import praktikum.EnvBody;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static praktikum.EnvConfig.*;
import static praktikum.steps.UserSteps.createUserStep;

public class CreateUserTest extends BaseApiTest {

    @Test
    @DisplayName("Позитивный тест на создание пользователя")
    @Description("Создаем пользователя с валидными данными")
    public void createUserTest(){
        accessToken = createUserStep(user)
                .statusCode(HTTP_OK)
                .body(EnvBody.BODY_SUCCESS, equalTo(true))
                .extract()
                .path(EnvBody.BODY_ACCESS_TOKEN);
    }

    @Test
    @DisplayName("Негативный тест на создание одинаковых пользователей")
    @Description("Создаем пользователя с валидными данными, далее создаем идентичного пользователя")
    public void identicalCreateUserNegativeTest(){
        accessToken = createUserStep(user)
                .statusCode(HTTP_OK)
                .body(EnvBody.BODY_SUCCESS, equalTo(true))
                .extract()
                .path(EnvBody.BODY_ACCESS_TOKEN);

        createUserStep(user)
                .statusCode(HTTP_FORBIDDEN)
                .body(EnvBody.BODY_SUCCESS, equalTo(false),
                        EnvBody.BODY_MESSAGE, equalTo(EnvBody.ERROR_USER_ALREADY_EXISTS));
    }

    @Test
    @DisplayName("Негативный тест на создание пользователя без Почты")
    @Description("Создаем пользователя без Почты, пользователь не должен создаться")
    public void noEmailCreateUserNegativeTest(){
        UserModel userNoValid = new UserModel(null, USER_PASSWORD, USER_NAME);
        createUserStep(userNoValid)
                .statusCode(HTTP_FORBIDDEN)
                .body(EnvBody.BODY_SUCCESS, equalTo(false),
                        EnvBody.BODY_MESSAGE, equalTo(EnvBody.ERROR_REQUIRED_FIELDS));
    }

    @Test
    @DisplayName("Негативный тест на создание пользователя без Пароля")
    @Description("Создаем пользователя без Пароля, пользователь не должен создаться")
    public void noPasswordCreateUserNegativeTest(){
        UserModel userNoValid = new UserModel(USER_EMAIL, null, USER_NAME);
        createUserStep(userNoValid)
                .statusCode(HTTP_FORBIDDEN)
                .body(EnvBody.BODY_SUCCESS, equalTo(false),
                        EnvBody.BODY_MESSAGE, equalTo(EnvBody.ERROR_REQUIRED_FIELDS));
    }

    @Test
    @DisplayName("Негативный тест на создание пользователя без Имени пользователя")
    @Description("Создаем пользователя без Имени пользователя, пользователь не должен создаться")
    public void noNameCreateUserNegativeTest(){
        UserModel userNoValid = new UserModel(USER_EMAIL, USER_PASSWORD, null);
        createUserStep(userNoValid)
                .statusCode(HTTP_FORBIDDEN)
                .body(EnvBody.BODY_SUCCESS, equalTo(false),
                        EnvBody.BODY_MESSAGE, equalTo(EnvBody.ERROR_REQUIRED_FIELDS));
    }

}
