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
import java.util.List;

public class UsersTableCommand implements Command {
    private static final String LIST = "users";
    private static final String CURRENT_USERS_PAGE = "currentUsersPage";
    private static final String USERS_ON_PAGE= "usersOnPage";
    private static final String NUMBER_OF_PAGES = "noOfPages";
    public static final Logger LOGGER = LogManager.getLogger();
    private final UserService userService = UserService.getInstance();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = ConfigurationManager.getProperty("path.page.usersTable");
        String current = request.getParameter(CURRENT_USERS_PAGE) == null ? "1" : request.getParameter(CURRENT_USERS_PAGE);
        String countOfUsers = request.getParameter(USERS_ON_PAGE)== null ? "5" : request.getParameter(USERS_ON_PAGE);
        int currentPage = Integer.parseInt(current);
        int usersOnPage = Integer.parseInt(countOfUsers);
        List<User>users;
        try {
            users = userService.findAllUndeletedUsers(currentPage,usersOnPage);
            int rows = userService.getNumberOfRows();
            int nOfPages = rows / usersOnPage;
            if (nOfPages % usersOnPage > 0) {
                nOfPages++;
            }

            request.getSession().setAttribute(NUMBER_OF_PAGES, nOfPages);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR,"Command  adminPage invalid",e);
            throw new CommandException("Command  adminPage invalid",e);
        }
        request.setAttribute(CURRENT_USERS_PAGE, currentPage);
        request.getSession().setAttribute(USERS_ON_PAGE, usersOnPage);
        request.getSession().setAttribute(LIST, users);
        return new Router(page, Router.Type.FORWARD);
    }
}
