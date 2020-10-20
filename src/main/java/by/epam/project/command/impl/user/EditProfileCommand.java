package by.epam.project.command.impl.user;

import by.epam.project.command.Command;
import by.epam.project.command.exception.CommandException;
import by.epam.project.command.manager.ConfigurationManager;
import by.epam.project.command.manager.MessageManager;
import by.epam.project.entity.Router;
import by.epam.project.entity.impl.User;
import by.epam.project.service.impl.UserServiceImpl;
import by.epam.project.service.ValidationUser;
import by.epam.project.service.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class EditProfileCommand implements Command {
    private static final String GENDER = "gender";
    private static final String COUNTRY = "country";
    private static final String ABOUT_ME = "about_me";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String XSS_REGEX = "<\\/?[A-Za-z]+[^>]*>";
    private final UserServiceImpl userServiceImpl = UserServiceImpl.getInstance();
    public static final Logger LOGGER = LogManager.getLogger();
    private final ValidationUser validationUser = ValidationUser.getInstance();
    private Router router;

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page;
        String gender = request.getParameter(GENDER);
        String country = request.getParameter(COUNTRY);
        String aboutMe = request.getParameter(ABOUT_ME).replaceAll(XSS_REGEX, "");
        String name = request.getParameter(NAME);
        String email = (String) request.getSession().getAttribute(EMAIL);
        try {
            User user = userServiceImpl.updateInfo(email, name, aboutMe, country, gender);
            request.setAttribute(GENDER, user.getGender());
            request.setAttribute(COUNTRY, user.getCountry());
            request.setAttribute(ABOUT_ME, user.getAboutMe());
            request.setAttribute(NAME, user.getName());
            if (validationUser.isRightAboutMe(aboutMe)) {
                page = ConfigurationManager.getProperty("path.page.profile");
            } else {
                request.setAttribute("errorData",
                        MessageManager.getProperty("message.incorrectData"));
                page = ConfigurationManager.getProperty("path.page.edit_profile");
            }
            router = new Router(page, Router.Type.REDIRECT);
            if (validationUser.isRightName(name)) {
                page = ConfigurationManager.getProperty("path.page.profile");
            } else {
                request.setAttribute("errorData",
                        MessageManager.getProperty("message.incorrectData"));
                page = ConfigurationManager.getProperty("path.page.edit_profile");
            }
            router = new Router(page, Router.Type.REDIRECT);

        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "User not found", e);
            throw new CommandException("User not found", e);
        }
        return router;
    }
}
