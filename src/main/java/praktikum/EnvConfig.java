package praktikum;

import com.github.javafaker.Faker;

public class EnvConfig {
    public static Faker faker = new Faker();

    public static final String BASE_URI = "https://stellarburgers.nomoreparties.site/"; //базовый URL


    public static final String USER_EMAIL = faker.internet().emailAddress(); //генератор случайной почты
    public static final String USER_PASSWORD = faker.internet().password(); //генератор случайного пароля
    public static final String USER_NAME = faker.name().username(); //генератор случайного имя пользователя

}
