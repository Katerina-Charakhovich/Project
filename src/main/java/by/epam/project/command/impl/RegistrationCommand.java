package by.epam.project.command.impl;

import by.epam.project.command.Command;
import by.epam.project.command.manager.ConfigurationManager;
import by.epam.project.command.manager.MessageManager;
import by.epam.project.service.ValidationService;
import by.epam.project.service.exception.ServiceException;
import by.epam.project.service.UserService;

import javax.servlet.http.HttpServletRequest;


public class RegistrationCommand implements Command {
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String REPEAT_PASSWORD = "repeat password";


    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        String page;
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        String repeatPassword = request.getParameter(REPEAT_PASSWORD);
        if (!password.equals(repeatPassword)){
            request.setAttribute("registrationError",
                    MessageManager.getProperty("message.passwordsDoNotMatch"));
           return ConfigurationManager.getProperty("path.page.registration");
        }
        if (!ValidationService.getInstance().isRightLogin(email)){
            request.setAttribute("registrationErrorLogin",
                    MessageManager.getProperty("message.incorrectLoginAndPassword"));
            return ConfigurationManager.getProperty("path.page.registration");
        }
        if (!ValidationService.getInstance().isRightPassword(password)){
            request.setAttribute("registrationErrorPassword",
                    MessageManager.getProperty("message.incorrectLoginAndPassword"));
            return ConfigurationManager.getProperty("path.page.registration");
        }

        if (UserService.getInstance().create(email, password)) {
            request.setAttribute("user", email);
            page = ConfigurationManager.getProperty("path.page.welcome");
        } else {
            request.setAttribute("registrationErrorLogin",
                    MessageManager.getProperty("message.thisUserExists"));
            page = ConfigurationManager.getProperty("path.page.registration");
        }
        return page;
    }
}


