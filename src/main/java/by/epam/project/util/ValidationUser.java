package by.epam.project.util;

/**
 * Class for validating user data
 */
public class ValidationUser {
    private static ValidationUser instance = new ValidationUser();
    private static final String LOGIN_EXPRESSION = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w]{2,4}$";
    private static final String PASSWORD_EXPRESSION = "^(?=.*[0-9])(?=.*[a-zа-я])(?=.*[A-ZА-Я]).{6,20}$";
    private static final String NAME_EXPRESSION = "[A-Za-zА-Яа-яЁё]+(\\s+[A-Za-zА-Яа-яЁё]+)?";
    private static final int MAX_LENGTH_ABOUT_ME = 300;

    private ValidationUser() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ValidationUser getInstance() {
        return instance;
    }

    /**
     * Is right login boolean.
     *
     * @param enteredLogin the entered login
     * @return the boolean
     */
    public boolean isRightLogin(String enteredLogin) {
        return enteredLogin.matches(LOGIN_EXPRESSION);
    }

    /**
     * Is right password boolean.
     *
     * @param enteredPassword the entered password
     * @return the boolean
     */
    public boolean isRightPassword(String enteredPassword) {
        return enteredPassword.matches(PASSWORD_EXPRESSION);
    }

    /**
     * Is right name boolean.
     *
     * @param name the name
     * @return the boolean
     */
    public boolean isRightName(String name) {
        return name.matches(NAME_EXPRESSION);
    }

    /**
     * Is right about me boolean.
     *
     * @param aboutMe the about me
     * @return the boolean
     */
    public boolean isRightAboutMe(String aboutMe) {
        return aboutMe.length() < MAX_LENGTH_ABOUT_ME;
    }
}
