package by.epam.project.command.impl.admin;

import by.epam.project.command.*;
import by.epam.project.command.exception.CommandException;
import by.epam.project.entity.impl.User;
import by.epam.project.service.UserService;
import by.epam.project.service.exception.ServiceException;
import by.epam.project.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Command to initialize the admin table
 */
public class InitAdminTableCommand implements Command {

    public static final Logger LOGGER = LogManager.getLogger();
    private UserService userService = UserServiceImpl.getInstance();
    private static final String DEFAULT_VALUE_OF_ADMIN_PAGE = "1";
    private static final String DEFAULT_VALUE_OF_ADMIN_ON_PAGES = "8";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = PathToPage.ADMIN_TABLE_PAGE;
        String current = CommandUtil.calculateTableParameter(request, RequestAttribute.CURRENT_ADMIN_PAGE, DEFAULT_VALUE_OF_ADMIN_PAGE);
        String countOfAdmins = CommandUtil.calculateTableParameter(request, RequestAttribute.ADMINS_ON_PAGE, DEFAULT_VALUE_OF_ADMIN_ON_PAGES);
        int currentPage = Integer.parseInt(current);
        int adminsOnPage = Integer.parseInt(countOfAdmins);
        List<User> admins;
        try {
            int nOfPages = calcNumberOfPages(adminsOnPage);
            if (currentPage > nOfPages) {
                currentPage = nOfPages;
            }
            admins = userService.findAllAdmins(currentPage, adminsOnPage);
            resolveAction(request, admins);
            request.getSession().setAttribute(RequestAttribute.NUMBER_OF_PAGES_ADMIN_TABLE, nOfPages);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Command  initAdminTable invalid", e);
            throw new CommandException("Command  initAdminTable invalid", e);
        }
        request.getSession().setAttribute(RequestAttribute.CURRENT_ADMIN_PAGE, currentPage);
        request.getSession().setAttribute(RequestAttribute.ADMINS_ON_PAGE, adminsOnPage);
        request.setAttribute(RequestAttribute.LIST_ADMINS, admins);
        request.setAttribute(RequestAttribute.LANG_CHANGE_PROCESS_COMMAND,
                RequestAttribute.COMMAND_ADMIN_TABLE);
        request.getSession().setAttribute(RequestAttribute.CARD_BACK_BUTTON_PAGE_ADDRESS, CommandType.INIT_ADMIN_TABLE.name());
        return new Router(page);
    }

    /**
     * Counting the number of rows
     */
    private int calcNumberOfPages(int adminsOnPage) throws ServiceException {
        int rows = userService.calculateNumberOfRowsByAdmin();
        double numberOfPage = (double) rows / adminsOnPage;
        return (int) Math.ceil(numberOfPage);
    }

    /**
     * Make admin user
     */
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
            if (request.getParameter(RequestAttribute.MAKE_USER) != null && user.getUserRole() == User.UserRole.ADMIN) {
                admins.remove(user);
                user = userService.changeRoleToUser(email);
                if (user != null) {
                    request.setAttribute(RequestAttribute.MAKE_USER, user.getUserRole());
                }
            }
        }
    }
}
