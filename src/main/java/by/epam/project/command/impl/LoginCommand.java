package by.epam.project.command.impl;

import by.epam.project.command.Command;
import by.epam.project.command.manager.ConfigurationManager;
import by.epam.project.command.manager.MessageManager;
import by.epam.project.service.exception.ServiceException;
import by.epam.project.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";

    @Override
    public String execute(HttpServletRequest request){
        String page = null;
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        try {
            if (UserService.getInstance().isLoginAndPasswordValid(email, password)) {
                request.setAttribute("user", email);
                page = ConfigurationManager.getProperty("path.page.welcome");
            } else {
                request.setAttribute("errorLoginPassMessage",
                        MessageManager.getProperty("message.loginerror"));
                page = ConfigurationManager.getProperty("path.page.index");
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return page;
    }
}
