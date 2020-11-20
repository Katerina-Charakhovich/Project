package by.epam.project.command.impl.film;

import by.epam.project.command.*;
import by.epam.project.command.exception.CommandException;
import by.epam.project.entity.impl.Film;
import by.epam.project.entity.impl.PurchasedFilm;
import by.epam.project.service.MediaService;
import by.epam.project.service.PurchasedFilmService;
import by.epam.project.service.impl.MediaServiceImpl;
import by.epam.project.service.exception.ServiceException;
import by.epam.project.service.impl.PurchasedFilmServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * This command initializes the movie page
 */
public class InitFilmCommand implements Command {

    public static final Logger LOGGER = LogManager.getLogger();
    private MediaService mediaService = MediaServiceImpl.getInstance();
    private PurchasedFilmService purchasedFilmService = PurchasedFilmServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = PathToPage.INIT_FILM_PAGE;
        String filmId = (String) request.getSession().getAttribute(RequestAttribute.CURRENT_FILM_ID);
        String currentFilmId = request.getParameter(RequestAttribute.CURRENT_FILM_ID) == null ? filmId.toString() : request.getParameter(RequestAttribute.CURRENT_FILM_ID);
        String language = (String) request.getSession().getAttribute(RequestAttribute.LANGUAGE);
        String currentPage = (String) request.getSession().getAttribute(RequestAttribute.CURRENT_PAGE);
        if (currentFilmId == null) {
            throw new CommandException("Film is broken. Please contact your system administrator");
        }
        long id = Long.parseLong(currentFilmId);
        try {
            Film film = mediaService.findFilmById(id, language.toLowerCase());
            request.getSession().setAttribute(RequestAttribute.CURRENT_FILM_ID, currentFilmId);
            String email = (String) request.getSession().getAttribute(RequestAttribute.EMAIL);
            request.setAttribute(RequestAttribute.CURRENT_DESCRIPTION, film.getFilmInfo().getDescription());
            request.setAttribute(RequestAttribute.CURRENT_YEAR_OF_CREATION, film.getFilmInfo().getYearOfCreation());
            request.setAttribute(RequestAttribute.CURRENT_GENRE, film.getFilmInfo().getGenre());
            request.setAttribute(RequestAttribute.CURRENT_LINK, film.getFilmInfo().getLink());
            request.setAttribute(RequestAttribute.CURRENT_FILM_NAME, film.getFilmName());
            request.setAttribute(RequestAttribute.CURRENT_FILM_AVATAR, film.getFilmAvatar());
            request.setAttribute(RequestAttribute.LANG_CHANGE_PROCESS_COMMAND,
                    RequestAttribute.COMMAND_FILM);
            PurchasedFilm purchasedFilm = purchasedFilmService.findPurchasedFilmByUserName(email, id);
            if (purchasedFilm != null) {
                request.getSession().setAttribute(RequestAttribute.BUY_FILM, RequestAttribute.FLAG_FOR_BUTTON_TRUE);
            } else {
                request.getSession().setAttribute(RequestAttribute.BUY_FILM, RequestAttribute.FLAG_FOR_BUTTON_FALSE);
            }
            String backButtonPageAddress = CommandType.INIT_START_PAGE.name();
            if (PathToPage.FILM_TABLE_PAGE.equals(currentPage)) {
                backButtonPageAddress = CommandType.ADMIN_PAGE_FILMS.name();
            } else if (PathToPage.PURCHASED_FILMS.equals(currentPage)){
                backButtonPageAddress = CommandType.VIEW_PURCHASED_FILM.name();
            }
            request.getSession().setAttribute(RequestAttribute.BACK_BUTTON_PAGE_ADDRESS,
                    backButtonPageAddress);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Film not found", e);
            throw new CommandException("Film not found", e);
        }
        return new Router(page);
    }
}
