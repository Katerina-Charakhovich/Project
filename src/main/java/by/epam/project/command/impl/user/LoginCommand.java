package by.epam.project.command.impl.user;

import by.epam.project.command.Command;
import by.epam.project.command.exception.CommandException;
import by.epam.project.command.PathToPage;
import by.epam.project.command.RequestAttribute;
import by.epam.project.command.Router;
import by.epam.project.entity.impl.User;
import by.epam.project.service.UserService;
import by.epam.project.service.exception.ServiceException;
import by.epam.project.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Command for user or administrator authorization
 */
public class LoginCommand implements Command {

    public static final Logger LOGGER = LogManager.getLogger();
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page;
        String email = request.getParameter(RequestAttribute.EMAIL);
        String password = request.getParameter(RequestAttribute.PASSWORD);
        try {
            if (userService.isLoginAndPasswordValid(email, password)) {
                if (userService.isUserLocked(email)) {
                    request.setAttribute(RequestAttribute.USER_IS_LOCKED, true);
                    page = PathToPage.LOGIN_PAGE;
                    return new Router(page);
                }
                User user = userService.findUserWithTheAllInfoByLogin(email);
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
                request.setAttribute(RequestAttribute.INCORRECT_LOGIN_AND_PASSWORD,true);
                page = PathToPage.LOGIN_PAGE;
            }
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "User is not exist", e);
            throw new CommandException("User is not exist", e);
        }
        return new Router(page);
    }
}
