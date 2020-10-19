package by.epam.project.command.impl.user;

import by.epam.project.command.Command;
import by.epam.project.command.exception.CommandException;
import by.epam.project.command.manager.ConfigurationManager;
import by.epam.project.command.manager.MessageManager;
import by.epam.project.entity.Router;
import by.epam.project.service.ValidationUser;
import by.epam.project.service.exception.ServiceException;
import by.epam.project.service.UserService;

import javax.servlet.http.HttpServletRequest;


public class RegistrationCommand implements Command {
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String REPEAT_PASSWORD = "repeat password";
    private static final ValidationUser validationUser = ValidationUser.getInstance();
    private static UserService userService = UserService.getInstance();
    private static final String ROLE = "role";
    private static final String PATH_TO_PAGE = "path.page.registration";
    private Router router;


    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page;
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        String repeatPassword = request.getParameter(REPEAT_PASSWORD);


        if (!password.equals(repeatPassword)) {
            request.setAttribute("registrationError",
                    MessageManager.getProperty("message.passwordsDoNotMatch"));
            page =  ConfigurationManager.getProperty(PATH_TO_PAGE);
           router = new Router(page, Router.Type.REDIRECT);
        }
        if (!validationUser.isRightPassword(password) && !validationUser.isRightLogin(email)) {
            request.setAttribute("registrationError",
                    MessageManager.getProperty("message.incorrectLoginAndPassword"));
            page =  ConfigurationManager.getProperty(PATH_TO_PAGE);
            router = new Router(page, Router.Type.REDIRECT);
        }

        try {
            if (UserService.getInstance().create(email, password)) {
                String userRole = userService.findUserRole(email);
                request.getSession().setAttribute(ROLE, userRole);
                request.getSession().setAttribute(EMAIL, email);
                page = ConfigurationManager.getProperty("path.page.start");
                router = new Router(page, Router.Type.REDIRECT);
            } else {
                request.setAttribute("registrationErrorLogin",
                        MessageManager.getProperty("message.thisUserExists"));
                page = ConfigurationManager.getProperty(PATH_TO_PAGE);
                router = new Router(page, Router.Type.REDIRECT);
            }
        } catch (ServiceException e) {
            throw new CommandException("Command invalide");
        }
        return router;
    }
}


