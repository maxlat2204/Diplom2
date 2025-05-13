package praktikum.user;

import praktikum.EnvConfig;

public class UserModel {

    private String email;
    private String password;
    private String name;

    public UserModel(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static UserModel random(){
        return new UserModel(EnvConfig.USER_EMAIL, EnvConfig.USER_PASSWORD, EnvConfig.USER_NAME);
    }

    public UserModel() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
