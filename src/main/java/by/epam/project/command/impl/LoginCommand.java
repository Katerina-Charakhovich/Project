package by.epam.project.command.impl;

import by.epam.project.command.Command;
import by.epam.project.dao.exception.DaoException;
import by.epam.project.command.manager.ConfigurationManager;
import by.epam.project.command.manager.MessageManager;
import by.epam.project.service.UserService;
import by.epam.project.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";

    @Override
    public String execute(HttpServletRequest request) throws DaoException {
        String page;
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        UserService userService = new UserServiceImpl();
        if (userService.checkLoginAndPass(login, password)) {
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
