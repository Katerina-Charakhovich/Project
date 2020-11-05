package by.epam.project.command.impl.user;

import by.epam.project.command.Command;

import by.epam.project.command.CommandException;
import by.epam.project.command.PathToPage;
import by.epam.project.command.RequestAttribute;
import by.epam.project.command.Router;
import by.epam.project.entity.impl.User;
import by.epam.project.service.impl.UserServiceImpl;
import by.epam.project.service.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;


public class ForwardToEditProfileCommand implements Command {

    public static final Logger LOGGER = LogManager.getLogger();
    private UserServiceImpl userServiceImpl = UserServiceImpl.getInstance();

    /**
     * The type Forward to edit profile command.
     */
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = PathToPage.EDIT_PROFILE_PAGE;
        String userEmail = (String) request.getSession().getAttribute(RequestAttribute.EMAIL);

        try {
            User user = userServiceImpl.findUserWithTheAllInfoByLogin(userEmail);
            request.setAttribute(RequestAttribute.GENDER, user.getUserGender());
            request.setAttribute(RequestAttribute.COUNTRY, user.getCountry());
            request.setAttribute(RequestAttribute.ABOUT_ME, user.getAboutMe());
            request.setAttribute(RequestAttribute.NAME_USER, user.getName());
            request.setAttribute(RequestAttribute.LANG_CHANGE_PROCESS_COMMAND,
                    RequestAttribute.COMMAND_FORWARD_TO_EDIT_PROFILE);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "User not found", e);
            throw new CommandException("User not found", e);
        }
        return new Router(page);
    }
}
