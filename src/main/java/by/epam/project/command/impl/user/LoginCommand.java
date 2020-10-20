package by.epam.project.command.impl.user;

import by.epam.project.command.Command;
import by.epam.project.command.exception.CommandException;
import by.epam.project.command.manager.ConfigurationManager;
import by.epam.project.command.manager.MessageManager;
import by.epam.project.entity.Router;
import by.epam.project.entity.impl.User;
import by.epam.project.service.exception.ServiceException;
import by.epam.project.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String ROLE = "role";
    private static final String GENDER = "gender";
    private static final String COUNTRY = "country";
    private static final String ABOUT_ME = "about_me";
    private static final String NAME = "name";
    private static final String AVATAR = "avatar";
    private static final UserServiceImpl USER_SERVICE_IMPL = UserServiceImpl.getInstance();
    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page;
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        User user;
        try {
            if (USER_SERVICE_IMPL.isLoginAndPasswordValid(email, password)) {
                user = USER_SERVICE_IMPL.findUserWithTheAllInfoByLogin(email);
                request.getSession().setAttribute(ROLE, user.getRole());
                request.getSession().setAttribute(EMAIL, user.getEmail());
                request.setAttribute(GENDER, user.getGender());
                request.setAttribute(COUNTRY, user.getCountry());
                request.setAttribute(NAME, user.getName());
                request.setAttribute(ABOUT_ME, user.getAboutMe());
                request.setAttribute(AVATAR, user.getAvatar());
                page = ConfigurationManager.getProperty("path.page.profile");
            } else if (USER_SERVICE_IMPL.isUserLocked(email)) {
                request.setAttribute("UserIsLocked",
                        MessageManager.getProperty("UserIsLocked"));
                page = ConfigurationManager.getProperty("path.page.login");
            } else {
                request.setAttribute("errorLoginPassMessage",
                        MessageManager.getProperty("message.loginerror"));
                page = ConfigurationManager.getProperty("path.page.index");
            }
            request.setAttribute("langChangeProcessCommand", "profile");
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "User is not exist", e);
            throw new CommandException("User is not exist", e);
        }
        return new Router(page, Router.Type.FORWARD);
    }
}
