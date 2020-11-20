package by.epam.project.command.impl.user;

import by.epam.project.command.Command;
import by.epam.project.command.exception.CommandException;
import by.epam.project.command.PathToPage;
import by.epam.project.command.RequestAttribute;
import by.epam.project.command.Router;
import by.epam.project.entity.impl.User;
import by.epam.project.service.UserService;
import by.epam.project.service.impl.UserServiceImpl;
import by.epam.project.service.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Command to initialize user profile
 */
public class InitProfileCommand implements Command {

    public static final Logger LOGGER = LogManager.getLogger();
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = PathToPage.PROFILE_PAGE;
        try {
            String userRole = (String) request.getSession().getAttribute(RequestAttribute.EMAIL);
            if (userRole == null) {
                page = PathToPage.ERROR_PAGE;
                request.setAttribute(RequestAttribute.ERROR, RequestAttribute.ERROR_MESSAGE_404);
                return new Router(page);
            }
            String userEmail = (String) request.getSession().getAttribute(RequestAttribute.EMAIL);
            User user = userService.findUserWithTheAllInfoByLogin(userEmail);
            request.setAttribute(RequestAttribute.GENDER, user.getUserGender());
            request.setAttribute(RequestAttribute.COUNTRY, user.getCountry());
            request.setAttribute(RequestAttribute.ABOUT_ME, user.getAboutMe());
            request.setAttribute(RequestAttribute.NAME_USER, user.getName());
            request.setAttribute(RequestAttribute.AVATAR, user.getAvatar());
            request.setAttribute(RequestAttribute.LANG_CHANGE_PROCESS_COMMAND,
                    RequestAttribute.COMMAND_PROFILE);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "User not found", e);
            throw new CommandException("User not found", e);
        }
        return new Router(page);
    }
}




