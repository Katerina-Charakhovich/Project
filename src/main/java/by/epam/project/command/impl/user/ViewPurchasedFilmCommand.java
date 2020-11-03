package by.epam.project.command.impl.user;

import by.epam.project.command.Command;
import by.epam.project.command.PathToPage;
import by.epam.project.command.RequestAttribute;
import by.epam.project.command.Router;
import by.epam.project.command.exception.CommandException;
import by.epam.project.entity.impl.Film;
import by.epam.project.entity.impl.User;
import by.epam.project.service.PurchasedFilmService;
import by.epam.project.service.exception.ServiceException;
import by.epam.project.service.impl.PurchasedFilmServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ViewPurchasedFilmCommand implements Command {
    public static final Logger LOGGER = LogManager.getLogger();
    private PurchasedFilmService purchasedFilmService = PurchasedFilmServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = PathToPage.PURCHASED_FILMS;
        List<Film> films;
        String email;
        String role = (String) request.getSession().getAttribute(RequestAttribute.ROLE);
        String language = (String) request.getSession().getAttribute(RequestAttribute.LANGUAGE);
        try {
            if (role.equals(User.UserRole.USER.name())){
                email = (String) request.getSession().getAttribute(RequestAttribute.EMAIL);
            }else {
                email = (String) request.getSession().getAttribute(RequestAttribute.CHOSEN_USER_EMAIL);
            }
            films = purchasedFilmService.findAllPurchasedFilmsByUser(email,language.toLowerCase());
            request.setAttribute(RequestAttribute.LIST_FILMS, films);
            request.setAttribute(RequestAttribute.LANG_CHANGE_PROCESS_COMMAND,
                    RequestAttribute.COMMAND_VIEW_PURCHASED_FILM);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Command  ViewPurchaseFilm invalid", e);
            throw new CommandException("Command  ViewPurchaseFilm invalid", e);
        }
        return new Router(page);
    }
}
