package by.epam.project.command.impl.admin;

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
 * Use admin for view user profile
 */
public class ViewProfileCommand implements Command {

    public static final Logger LOGGER = LogManager.getLogger();
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = PathToPage.PROFILE_CARD_PAGE;
        String email = request.getParameter(RequestAttribute.EMAIL) == null
                ? (String) request.getSession().getAttribute(RequestAttribute.CHOSEN_USER_EMAIL)
                : request.getParameter(RequestAttribute.EMAIL);
        try {
            User user = userService.findUserWithTheAllInfoByLogin(email);
            if (user == null) {
                throw new CommandException("User not found. Please contact your system administrator");
            }
            request.setAttribute(RequestAttribute.GENDER, user.getUserGender());
            request.setAttribute(RequestAttribute.COUNTRY, user.getCountry());
            request.setAttribute(RequestAttribute.ABOUT_ME, user.getAboutMe());
            request.setAttribute(RequestAttribute.NAME_USER, user.getName());
            request.setAttribute(RequestAttribute.AVATAR, user.getAvatar());
            request.getSession().setAttribute(RequestAttribute.CHOSEN_USER_EMAIL, email);
            request.setAttribute(RequestAttribute.LANG_CHANGE_PROCESS_COMMAND, RequestAttribute.COMMAND_VIEW_PROFILE);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "User not found", e);
            throw new CommandException("User not found. Please contact your system administrator", e);
        }
        return new Router(page);
    }
}
