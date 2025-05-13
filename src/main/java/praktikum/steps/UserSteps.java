package praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import praktikum.EndPoints;
import praktikum.user.UserModel;

import static io.restassured.RestAssured.given;
import static praktikum.EndPoints.USER_OPTIONS_DEL_PATH;

public class UserSteps {

    @Step("Создать пользователя")
    public static ValidatableResponse createUserStep(UserModel user) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(EndPoints.USER_REGISTER_PATH)
                .then().log().all();
    }

    @Step("Удалить пользователя")
    public static ValidatableResponse deletedUserStep(String accessToken) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", accessToken)
                .when()
                .delete(EndPoints.USER_OPTIONS_DEL_PATH)
                .then().log().all();
    }

    @Step("Залогинить пользователя")
    public static ValidatableResponse loginUserStep(UserModel user) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(EndPoints.USER_LOGIN_PATH)
                .then().log().all();
    }

    @Step("Изменить данные пользователя")
    public static ValidatableResponse changeUserStep(UserModel user, String accessToken) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch(USER_OPTIONS_DEL_PATH)
                .then().log().all();
    }

}
