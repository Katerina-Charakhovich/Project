package by.epam.project.command.impl.user;

import by.epam.project.command.Command;
import by.epam.project.command.PathToPage;
import by.epam.project.command.RequestAttribute;
import by.epam.project.command.Router;
import by.epam.project.command.CommandException;
import by.epam.project.service.PurchasedFilmService;
import by.epam.project.service.exception.ServiceException;
import by.epam.project.service.impl.PurchasedFilmServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;


public class PurchaseFilmCommand implements Command {

    public static final Logger LOGGER = LogManager.getLogger();
    private PurchasedFilmService purchasedFilmService = PurchasedFilmServiceImpl.getInstance();

    /**
     * The type Purchase film command.
     */

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = null;
        String email = (String) request.getSession().getAttribute(RequestAttribute.EMAIL);
        String currentFilmId = (String) request.getSession().getAttribute(RequestAttribute.CURRENT_FILM_ID);
        long filmId = Long.parseLong(currentFilmId);
        String buyFilm = request.getParameter(RequestAttribute.BUY_FILM) == null
                ? (String) request.getSession().getAttribute(RequestAttribute.BUY_FILM)
                : request.getParameter(RequestAttribute.BUY_FILM);
        String description = request.getParameter(RequestAttribute.CURRENT_DESCRIPTION);
        String yearOfCreation = request.getParameter(RequestAttribute.CURRENT_YEAR_OF_CREATION);
        String genre = request.getParameter(RequestAttribute.CURRENT_GENRE);
        String link = request.getParameter(RequestAttribute.CURRENT_LINK);
        String filmName = request.getParameter(RequestAttribute.CURRENT_FILM_NAME);
        String filmAvatar = request.getParameter(RequestAttribute.CURRENT_FILM_AVATAR);
        try {
            if (buyFilm != null) {
                if (purchasedFilmService.purchaseFilm(email, filmId)) {
                    request.getSession().setAttribute(RequestAttribute.CURRENT_DESCRIPTION, description);
                    request.getSession().setAttribute(RequestAttribute.BUY_FILM, buyFilm);
                    request.getSession().setAttribute(RequestAttribute.CURRENT_YEAR_OF_CREATION, yearOfCreation);
                    request.getSession().setAttribute(RequestAttribute.CURRENT_GENRE, genre);
                    request.getSession().setAttribute(RequestAttribute.CURRENT_LINK, link);
                    request.getSession().setAttribute(RequestAttribute.CURRENT_FILM_NAME, filmName);
                    request.getSession().setAttribute(RequestAttribute.CURRENT_FILM_AVATAR, filmAvatar);
                    router = new Router(PathToPage.INIT_FILM_PAGE);
                    router.useRedirect();
                } else {
                    router = new Router(PathToPage.ERROR_PAGE);
                    request.setAttribute(RequestAttribute.ERROR, "Unsuccessful attempt to buy a movie");
                }
            }
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Command  PurchaseFilm invalid", e);
            throw new CommandException("Command  PurchaseFilm invalid", e);
        }

        return router;
    }
}
