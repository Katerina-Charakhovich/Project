package by.epam.project.service;

public class ValidationService {
    private static ValidationService instance;
    private static final String LOGIN_EXPRESSION = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";
    private static final String PASSWORD_EXPRESSION = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,20}$";
    private ValidationService() {
    }
    public static ValidationService getInstance(){
        if (instance == null){
            instance = new ValidationService();
        }
        return instance;
    }
    public Boolean isRightLogin (String enteredLogin){
        return enteredLogin.matches(LOGIN_EXPRESSION);
    }
    public Boolean isRightPassword(String enteredPassword){
        return enteredPassword.matches(PASSWORD_EXPRESSION);
    }
}
