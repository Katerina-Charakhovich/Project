package by.epam.project.command.impl.user;

import by.epam.project.command.Command;
import by.epam.project.command.exception.CommandException;
import by.epam.project.command.PathToPage;
import by.epam.project.command.RequestAttribute;
import by.epam.project.command.Router;
import by.epam.project.entity.impl.User;
import by.epam.project.service.UserService;
import by.epam.project.service.impl.UserServiceImpl;
import by.epam.project.util.ValidationUser;
import by.epam.project.service.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Command to edit user profile
 */
public class EditProfileCommand implements Command {

    public static final Logger LOGGER = LogManager.getLogger();
    private UserService userService = UserServiceImpl.getInstance();
    private ValidationUser validationUser = ValidationUser.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page;
        String gender = request.getParameter(RequestAttribute.GENDER);
        String country = request.getParameter(RequestAttribute.COUNTRY);
        String aboutMe = request.getParameter(RequestAttribute.ABOUT_ME);
        String name = request.getParameter(RequestAttribute.NAME_USER);
        String avatar = request.getParameter(RequestAttribute.AVATAR);
        String email = (String) request.getSession().getAttribute(RequestAttribute.EMAIL);
        try {
            if (validationUser.isRightName(name) && validationUser.isRightAboutMe(aboutMe)) {
                User user = userService.updateInfo(email, name, aboutMe, country, gender);
                page = PathToPage.PROFILE_PAGE;

                resolveUser(request, user);

            } else {
                request.setAttribute(RequestAttribute.ERROR_DATA, true);
                page = PathToPage.EDIT_PROFILE_PAGE;
                request.setAttribute(RequestAttribute.GENDER, gender);
                request.setAttribute(RequestAttribute.COUNTRY, country);
                request.setAttribute(RequestAttribute.ABOUT_ME, aboutMe);
                request.setAttribute(RequestAttribute.NAME_USER, name);
                request.setAttribute(RequestAttribute.AVATAR, avatar);
                request.setAttribute(RequestAttribute.LANG_CHANGE_PROCESS_COMMAND,
                        RequestAttribute.COMMAND_PROFILE);
            }
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "User not found", e);
            throw new CommandException("User not found", e);
        }
        return new Router(page);
    }

    private void resolveUser(HttpServletRequest request, User user) throws CommandException {
        if (user != null) {
            request.setAttribute(RequestAttribute.GENDER, user.getUserGender());
            request.setAttribute(RequestAttribute.COUNTRY, user.getCountry());
            request.setAttribute(RequestAttribute.ABOUT_ME, user.getAboutMe());
            request.setAttribute(RequestAttribute.NAME_USER, user.getName());
            request.setAttribute(RequestAttribute.AVATAR, user.getAvatar());
            request.setAttribute(RequestAttribute.LANG_CHANGE_PROCESS_COMMAND,
                    RequestAttribute.COMMAND_PROFILE);
        } else {
            throw new CommandException("Fail User update");
        }
    }
}
