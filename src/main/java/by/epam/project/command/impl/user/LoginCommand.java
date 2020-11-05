package by.epam.project.command.impl.user;

import by.epam.project.command.Command;
import by.epam.project.command.CommandException;
import by.epam.project.command.PathToPage;
import by.epam.project.command.RequestAttribute;
import by.epam.project.command.Router;
import by.epam.project.entity.impl.User;
import by.epam.project.service.exception.ServiceException;
import by.epam.project.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;


public class LoginCommand implements Command {

    public static final Logger LOGGER = LogManager.getLogger();
    private UserServiceImpl userService = UserServiceImpl.getInstance();

    /**
     * The type Login command.
     */

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page;
        String email = request.getParameter(RequestAttribute.EMAIL);
        String password = request.getParameter(RequestAttribute.PASSWORD);
        boolean errorLoginPassMessage;
        User user;
        Router router;
        try {
            if (userService.isLoginAndPasswordValid(email, password)) {
                if (userService.isUserLocked(email)) {
                    errorLoginPassMessage = true;
                    request.setAttribute(RequestAttribute.USER_IS_LOCKED, errorLoginPassMessage);
                    page = PathToPage.LOGIN_PAGE;
                    return new Router(page);
                }
                user = userService.findUserWithTheAllInfoByLogin(email);
                request.getSession().setAttribute(RequestAttribute.ROLE, user.getUserRole().name());
                request.getSession().setAttribute(RequestAttribute.EMAIL, user.getEmail());
                request.setAttribute(RequestAttribute.GENDER, user.getUserGender());
                request.setAttribute(RequestAttribute.COUNTRY, user.getCountry());
                request.setAttribute(RequestAttribute.NAME_USER, user.getName());
                request.setAttribute(RequestAttribute.ABOUT_ME, user.getAboutMe());
                request.setAttribute(RequestAttribute.AVATAR, user.getAvatar());
                request.setAttribute(RequestAttribute.LANG_CHANGE_PROCESS_COMMAND,
                        RequestAttribute.COMMAND_PROFILE);
                page = PathToPage.PROFILE_PAGE;
            } else {
                errorLoginPassMessage = true;
                request.setAttribute(RequestAttribute.INCORRECT_LOGIN_AND_PASSWORD,
                        errorLoginPassMessage);
                page = PathToPage.LOGIN_PAGE;
            }
            router = new Router(page);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "User is not exist", e);
            throw new CommandException("User is not exist", e);
        }
        return router;
    }
}
