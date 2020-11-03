package by.epam.project.util;

public class ValidationUser {
    private static ValidationUser instance = new ValidationUser();
    private static final String LOGIN_EXPRESSION = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";
    private static final String PASSWORD_EXPRESSION = "^(?=.*[0-9])(?=.*[a-zа-я])(?=.*[A-ZА-Я]).{6,20}$";
    private static final String NAME_EXPRESSION = "[A-Za-zА-Яа-яЁё]+(\\s+[A-Za-zА-Яа-яЁё]+)?";
    private static final int MAX_LENGTH_ABOUT_ME = 300;


    private ValidationUser() {
    }

    public static ValidationUser getInstance() {
        return instance;
    }

    public boolean isRightLogin(String enteredLogin) {
        return enteredLogin.matches(LOGIN_EXPRESSION);
    }

    public boolean isRightPassword(String enteredPassword) {
        return enteredPassword.matches(PASSWORD_EXPRESSION);
    }

    public boolean isRightName(String name) {
        return name.matches(NAME_EXPRESSION);
    }

    public boolean isRightAboutMe(String aboutMe) {
        return aboutMe.length() < MAX_LENGTH_ABOUT_ME;
    }
}
