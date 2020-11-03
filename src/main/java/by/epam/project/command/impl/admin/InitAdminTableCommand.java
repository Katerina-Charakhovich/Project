package by.epam.project.command.impl.admin;

import by.epam.project.command.Command;
import by.epam.project.command.Router;
import by.epam.project.command.exception.CommandException;
import by.epam.project.command.PathToPage;
import by.epam.project.command.RequestAttribute;
import by.epam.project.entity.impl.User;
import by.epam.project.service.exception.ServiceException;
import by.epam.project.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class InitAdminTableCommand implements Command {
    public static final Logger LOGGER = LogManager.getLogger();
    private UserServiceImpl userServiceImpl = UserServiceImpl.getInstance();
    private static final String DEFAULT_VALUE_OF_FILM_PAGE = "1";
    private static final String DEFAULT_VALUE_OF_FILMS_ON_PAGES = "8";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = PathToPage.ADMIN_TABLE_PAGE;
        String current = request.getParameter(RequestAttribute.CURRENT_ADMIN_PAGE)
                == null ? DEFAULT_VALUE_OF_FILM_PAGE : request.getParameter(RequestAttribute.CURRENT_ADMIN_PAGE);
        String countOfAdmins = request.getParameter(RequestAttribute.ADMINS_ON_PAGE)
                == null ? DEFAULT_VALUE_OF_FILMS_ON_PAGES : request.getParameter(RequestAttribute.ADMINS_ON_PAGE);
        int currentPage = Integer.parseInt(current);
        int adminsOnPage = Integer.parseInt(countOfAdmins);
        List<User> admins;
        try {
            admins = userServiceImpl.findAllAdmins(currentPage, adminsOnPage);

            resolveAction(request, admins);

            request.setAttribute(RequestAttribute.NUMBER_OF_PAGES, calcNumberOfPages(adminsOnPage));
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Command  initAdminTable invalid", e);
            throw new CommandException("Command  initAdminTable invalid", e);
        }
        request.setAttribute(RequestAttribute.CURRENT_ADMIN_PAGE, currentPage);
        request.setAttribute(RequestAttribute.ADMINS_ON_PAGE, adminsOnPage);
        request.setAttribute(RequestAttribute.LIST_ADMINS, admins);
        request.setAttribute(RequestAttribute.LANG_CHANGE_PROCESS_COMMAND,
                RequestAttribute.COMMAND_ADMIN_TABLE);
        return new Router(page);
    }

    private int calcNumberOfPages(int adminsOnPage) throws ServiceException {
        int rows = userServiceImpl.calculateNumberOfRowsByAdmin();
        double numberOfPage = (double)rows / adminsOnPage;
        int nOfPages = (int)Math.ceil(numberOfPage);
        return nOfPages;
    }

    private void resolveAction(HttpServletRequest request, List<User> admins) throws ServiceException {
        String email = request.getParameter(RequestAttribute.EMAIL);
        if (email != null) {
            User user = admins.stream()
                    .filter(userInList -> email.equals(userInList.getEmail()))
                    .findFirst()
                    .orElse(null);
            if (user == null) {
                LOGGER.log(Level.ERROR, "User not found");
                throw new ServiceException("User not found");
            }
            if (request.getParameter(RequestAttribute.MAKE_USER) != null) {
                if (user.getUserRole() == User.UserRole.ADMIN) {
                    admins.remove(user);
                    user = userServiceImpl.changeRoleToUser(email);
                    request.setAttribute(RequestAttribute.MAKE_USER, user.getUserRole());
                }
            }
        }
    }
}
