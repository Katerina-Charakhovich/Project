package by.epam.project.command.impl.admin;

import by.epam.project.command.Command;
import by.epam.project.command.exception.CommandException;
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

public class ViewProfileCommand implements Command {
    public static final Logger LOGGER = LogManager.getLogger();
    private UserServiceImpl userServiceImpl = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = PathToPage.PROFILE_CARD_PAGE;
        User user;
        String email = request.getParameter(RequestAttribute.EMAIL) == null
                ? (String) request.getSession().getAttribute("chosenUserEmail")
                : request.getParameter(RequestAttribute.EMAIL);
        try {
            user = userServiceImpl.findUserWithTheAllInfoByLogin(email);
            request.setAttribute(RequestAttribute.GENDER, user.getUserGender());
            request.setAttribute(RequestAttribute.COUNTRY, user.getCountry());
            request.setAttribute(RequestAttribute.ABOUT_ME, user.getAboutMe());
            request.setAttribute(RequestAttribute.NAME_USER, user.getName());
            request.setAttribute(RequestAttribute.AVATAR, user.getAvatar());

            request.getSession().setAttribute("chosenUserEmail", email);
            request.setAttribute(RequestAttribute.LANG_CHANGE_PROCESS_COMMAND, "view_profile");
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "User not found", e);
            throw new CommandException("User not found", e);
        }
        return new Router(page);
    }
}
