package by.epam.project.command.impl.user;

import by.epam.project.command.Command;
import by.epam.project.command.CommandException;
import by.epam.project.command.PathToPage;
import by.epam.project.command.RequestAttribute;
import by.epam.project.command.Router;
import by.epam.project.entity.impl.User;
import by.epam.project.util.ValidationUser;
import by.epam.project.service.exception.ServiceException;
import by.epam.project.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;


public class RegistrationCommand implements Command {

    public static final Logger LOGGER = LogManager.getLogger();
    private ValidationUser validationUser = ValidationUser.getInstance();
    private UserServiceImpl userServiceImpl = UserServiceImpl.getInstance();
    private Router router;

    /**
     * The type Registration command.
     */

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page;
        String email = request.getParameter(RequestAttribute.EMAIL);
        String password = request.getParameter(RequestAttribute.PASSWORD);
        String repeatPassword = request.getParameter(RequestAttribute.REPEAT_PASSWORD);
        if (!password.equals(repeatPassword)) {
            boolean registrationErrorPasswords = true;
            request.setAttribute(RequestAttribute.PASSWORDS_DO_NOT_MATCH,
                    registrationErrorPasswords);
            page = PathToPage.REGISTRATION_PAGE;
            router = new Router(page);
        }
        if (!validationUser.isRightPassword(password) && !validationUser.isRightLogin(email)) {
            boolean registrationErrorSymbols = true;
            request.setAttribute(RequestAttribute.INCORRECT_ERROR_SYMBOLS, registrationErrorSymbols);
            page = PathToPage.REGISTRATION_PAGE;
            router = new Router(page);
        }
        try {
            if (userServiceImpl.create(email, password)) {
                User user = userServiceImpl.findUserWithTheAllInfoByLogin(email);
                request.getSession().setAttribute(RequestAttribute.ROLE, user.getUserRole());
                request.getSession().setAttribute(RequestAttribute.EMAIL, email);
                page = PathToPage.INDEX_PAGE;
                router = new Router(page);
                router.useRedirect();
            } else {
                boolean registrationError = true;
                request.setAttribute(RequestAttribute.REGISTRATION_ERROR, registrationError);
                page = PathToPage.REGISTRATION_PAGE;
                router = new Router(page);
            }
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Registration failed", e);
            throw new CommandException("Registration failed", e);
        }
        return router;
    }
}


