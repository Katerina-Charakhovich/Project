package by.epam.login.command.impl;

import by.epam.login.command.Command;
import by.epam.login.manager.ConfigurationManager;
import by.epam.login.manager.MessageManager;
import by.epam.login.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        String login = request.getParameter(LOGIN);
        String pass = request.getParameter(PASSWORD);
        if (UserService.checkLogin(login, pass)) {
            request.setAttribute("user", login);
            page = ConfigurationManager.getProperty("path.page.welcome");
        } else {
            request.setAttribute("errorLoginPassMessage",
                    MessageManager.getProperty("message.loginerror"));
            page = ConfigurationManager.getProperty("path.page.index");
        }
        return page;
    }
}
