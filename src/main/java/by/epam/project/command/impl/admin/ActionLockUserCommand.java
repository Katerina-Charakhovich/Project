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

public class ActionLockUserCommand implements Command {
    private static final String LOCK = "lock";
    private static final String EMAIL = "email";
    public static final Logger LOGGER = LogManager.getLogger();
    private final UserService userService = UserService.getInstance();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
       String page = ConfigurationManager.getProperty("path.page.usersTable");
       User user;
       String email = request.getParameter(EMAIL);
        try {
            userService.lockUser(email);
            user = userService.findUserWithTheAllInfoByLogin(email);
            request.setAttribute(LOCK, user.getLocked());
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR,"Command  ActionLockUser invalid",e);
            throw new CommandException("Command  ActionLockUser invalid",e);
        }
        return new Router(page, Router.Type.FORWARD);
    }
}
