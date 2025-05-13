package praktikum;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import praktikum.user.UserModel;

import static java.net.HttpURLConnection.HTTP_ACCEPTED;
import static praktikum.steps.UserSteps.deletedUserStep;

public class BaseApiTest {
    protected String accessToken;
    protected UserModel user = UserModel.random();

    @Before
    public void setUp(){
        RestAssured.baseURI = EnvConfig.BASE_URI;
    }

    @After
    public void delUser(){
        if (accessToken != null){
            deletedUserStep(accessToken)
                    .statusCode(HTTP_ACCEPTED);
        }
    }

}
