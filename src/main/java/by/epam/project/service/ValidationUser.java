package by.epam.project.service;

public class ValidationUser {
    private static ValidationUser instance;
    private static final String LOGIN_EXPRESSION = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";
    private static final String PASSWORD_EXPRESSION = "^(?=.*[0-9])(?=.*[a-zа-я])(?=.*[A-ZА-Я]).{6,20}$";
    private static final int MAX_SYMBOLS = 300;
    private static final String NAME_EXPRESSION = "[a-zA-Zа-яА-Я]{0,50}";

    private ValidationUser() {
    }

    public static ValidationUser getInstance() {
        if (instance == null) {
            instance = new ValidationUser();
        }
        return instance;
    }

    public Boolean isRightLogin(String enteredLogin) {
        return enteredLogin.matches(LOGIN_EXPRESSION);
    }

    public Boolean isRightPassword(String enteredPassword) {
        return enteredPassword.matches(PASSWORD_EXPRESSION);
    }

    public Boolean isRightAboutMe(String aboutMe) {
        return aboutMe.length() <= MAX_SYMBOLS;
    }

    public Boolean isRightName(String name) {
        return name.matches(NAME_EXPRESSION);
    }


}
