package by.epam.project.command.impl.admin;

import by.epam.project.command.Command;
import by.epam.project.command.exception.CommandException;
import by.epam.project.command.manager.ConfigurationManager;
import by.epam.project.entity.Router;
import by.epam.project.entity.impl.User;
import by.epam.project.service.UserService;
import by.epam.project.service.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ViewProfileCommand implements Command {
    private static final String EMAIL = "email";
    private static final String GENDER = "gender";
    private static final String COUNTRY = "country";
    private static final String ABOUT_ME = "about_me";
    private static final String NAME = "name";
    private static final String AVATAR = "avatar";
    private final UserService userService = UserService.getInstance();
    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = ConfigurationManager.getProperty("path.page.profileCard");
        User user;
        String email = request.getParameter(EMAIL) == null
                ? (String) request.getSession().getAttribute("chosenUserEmail")
                : request.getParameter(EMAIL);
        try {
            user = userService.findUserWithTheAllInfoByLogin(email);
            request.setAttribute(GENDER, user.getGender());
            request.setAttribute(COUNTRY, user.getCountry());
            request.setAttribute(ABOUT_ME, user.getAboutMe());
            request.setAttribute(NAME, user.getName());
            request.setAttribute(AVATAR, user.getAvatar());

            request.getSession().setAttribute("chosenUserEmail", email);
            request.setAttribute("langChangeProcessCommand", "view_profile");
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "User not found", e);
            throw new CommandException("User not found", e);
        }
        return new Router(page, Router.Type.FORWARD);
    }
}
