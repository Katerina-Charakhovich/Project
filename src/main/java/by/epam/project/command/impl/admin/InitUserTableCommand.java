package by.epam.project.command.impl.admin;

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
import java.util.List;


public class InitUserTableCommand implements Command {

    public static final Logger LOGGER = LogManager.getLogger();
    private UserServiceImpl userServiceImpl = UserServiceImpl.getInstance();
    private static final String DEFAULT_VALUE_OF_FILM_PAGE = "1";
    private static final String DEFAULT_VALUE_OF_FILMS_ON_PAGES = "8";

    /**
     * The type Init user table command.
     */
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = PathToPage.USER_TABLE_PAGE;
        String current = request.getParameter(RequestAttribute.CURRENT_USERS_PAGE)
                == null ? DEFAULT_VALUE_OF_FILM_PAGE : request.getParameter(RequestAttribute.CURRENT_USERS_PAGE);
        String countOfUsers = request.getParameter(RequestAttribute.USERS_ON_PAGE)
                == null ? DEFAULT_VALUE_OF_FILMS_ON_PAGES : request.getParameter(RequestAttribute.USERS_ON_PAGE);
        int currentPage = Integer.parseInt(current);
        int usersOnPage = Integer.parseInt(countOfUsers);
        List<User> users;
        try {
            users = userServiceImpl.findUsersOnPage(currentPage, usersOnPage);

            resolveAction(request, users);

            request.setAttribute(RequestAttribute.NUMBER_OF_PAGES, calcNumberOfPages(usersOnPage));
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Command  adminPage invalid", e);
            throw new CommandException("Command  adminPage invalid", e);
        }
        request.setAttribute(RequestAttribute.CURRENT_USERS_PAGE, currentPage);
        request.setAttribute(RequestAttribute.USERS_ON_PAGE, usersOnPage);
        request.setAttribute(RequestAttribute.LIST_USERS, users);
        request.setAttribute(RequestAttribute.LANG_CHANGE_PROCESS_COMMAND,
                RequestAttribute.COMMAND_USER_TABLE);
        return new Router(page);
    }

    /**
     * Counting the number of rows
     */
    private int calcNumberOfPages(int usersOnPage) throws ServiceException {
        int rows = userServiceImpl.calculateNumberOfRowsByUser();
        double numberOfPage = (double) rows / usersOnPage;
        return (int) Math.ceil(numberOfPage);
    }

    /**
     * Make user admin
     */
    private void resolveAction(HttpServletRequest request, List<User> users)
            throws ServiceException {
        String email = request.getParameter(RequestAttribute.EMAIL);
        if (email != null) {
            User user = users.stream()
                    .filter(userInList -> email.equals(userInList.getEmail()))
                    .findFirst()
                    .orElse(null);
            if (user == null) {
                LOGGER.log(Level.ERROR, "User not found");
                throw new ServiceException("User not found");
            }
            if (request.getParameter(RequestAttribute.LOCK) != null) {
                user = userServiceImpl.lockUser(user);
                request.setAttribute(RequestAttribute.LOCK, user.isLocked());
            }
            if (request.getParameter(RequestAttribute.MAKE_ADMIN) != null) {
                if (user.getUserRole() == User.UserRole.USER) {
                    users.remove(user);
                    user = userServiceImpl.changeRoleToAdmin(user);
                    request.setAttribute(RequestAttribute.MAKE_ADMIN, user.getUserRole());
                }
            }
        }
    }
}
