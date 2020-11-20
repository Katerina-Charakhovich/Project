package by.epam.project.command.impl.user;

import by.epam.project.command.Command;
import by.epam.project.command.exception.CommandException;
import by.epam.project.command.PathToPage;
import by.epam.project.command.RequestAttribute;
import by.epam.project.command.Router;
import by.epam.project.service.UserService;
import by.epam.project.util.ValidationUser;
import by.epam.project.service.exception.ServiceException;
import by.epam.project.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Command for registering a new user
 */
public class RegistrationCommand implements Command {

    public static final Logger LOGGER = LogManager.getLogger();
    private ValidationUser validationUser = ValidationUser.getInstance();
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page;
        String email = request.getParameter(RequestAttribute.EMAIL);
        String password = request.getParameter(RequestAttribute.PASSWORD);
        String repeatPassword = request.getParameter(RequestAttribute.REPEAT_PASSWORD);
        if (!password.equals(repeatPassword)) {
            request.setAttribute(RequestAttribute.PASSWORDS_DO_NOT_MATCH, true);
            page = PathToPage.REGISTRATION_PAGE;
            return new Router(page);
        }
        if (!validationUser.isRightLogin(email) || !validationUser.isRightPassword(password)) {
            request.setAttribute(RequestAttribute.INCORRECT_ERROR_SYMBOLS, true);
            page = PathToPage.REGISTRATION_PAGE;
            return new Router(page);
        }
        try {
            Router router;
            if (userService.create(email, password)) {
                router = new Router(PathToPage.LOGIN_PAGE);
                router.useRedirect();
            } else {
                request.setAttribute(RequestAttribute.REGISTRATION_ERROR, true);
                router = new Router(PathToPage.REGISTRATION_PAGE);
            }
            return router;
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Registration failed", e);
            throw new CommandException("Registration failed", e);
        }
    }
}


