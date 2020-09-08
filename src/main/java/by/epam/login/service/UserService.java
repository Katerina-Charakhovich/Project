package by.epam.login.service;

public class UserService {
    private final static String ADMIN_LOGIN = "admin";
    private final static String ADMIN_PASS = "Qwerty123";

    public static boolean checkLogin(String enterLogin, String enterPass) {
        return ADMIN_LOGIN.equals(enterLogin) && ADMIN_PASS.equals(enterPass);
    }
}
