package by.epam.project.command.impl.admin;

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
import java.util.Map;

public class InitPurchasedFilmTableCommand implements Command {
    public static final Logger LOGGER = LogManager.getLogger();
    private PurchasedFilmService purchasedFilmService = PurchasedFilmServiceImpl.getInstance();
    private static final String DEFAULT_VALUE_OF_PURCHASED_FILM_PAGE = "1";
    private static final String DEFAULT_VALUE_OF_PURCHASED_FILMS_ON_PAGES = "8";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = PathToPage.PURCHASED_FILMS_TABLE;
        String current = request.getParameter(RequestAttribute.CURRENT_PURCHASED_FILM_PAGE) == null
                ? DEFAULT_VALUE_OF_PURCHASED_FILM_PAGE
                : request.getParameter(RequestAttribute.CURRENT_PURCHASED_FILM_PAGE);
        String countOfPurchasedFilms = request.getParameter(RequestAttribute.PURCHASED_FILMS_ON_PAGE) == null
                ? DEFAULT_VALUE_OF_PURCHASED_FILMS_ON_PAGES
                : request.getParameter(RequestAttribute.PURCHASED_FILMS_ON_PAGE);
        int currentPage = Integer.parseInt(current);
        String language = (String)request.getSession().getAttribute(RequestAttribute.LANGUAGE);
        int purchasedFilmsOnPage = Integer.parseInt(countOfPurchasedFilms);
        Map<User, Film> purchasedFilms;
        try {
            purchasedFilms = purchasedFilmService.findAllInfoAboutPurchasedFilms
                    (currentPage,purchasedFilmsOnPage,language.toLowerCase());
            request.setAttribute(RequestAttribute.NUMBER_OF_PAGES, calcNumberOfPages(purchasedFilmsOnPage));
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Command  initPurchasedFimTable invalid", e);
            throw new CommandException("Command  initPurchasedFimTable invalid", e);
        }
        request.setAttribute(RequestAttribute.CURRENT_PURCHASED_FILM_PAGE, currentPage);
        request.setAttribute(RequestAttribute.PURCHASED_FILMS_ON_PAGE, purchasedFilmsOnPage);
        request.setAttribute(RequestAttribute.MAP_PURCHASED_FILMS, purchasedFilms);
        request.setAttribute(RequestAttribute.LANG_CHANGE_PROCESS_COMMAND,
                RequestAttribute.COMMAND_PURCHASED_FILM_TABLE);
        return new Router(page);
    }

    private int calcNumberOfPages(int purchasedFilmsOnPage) throws ServiceException {
        int rows = purchasedFilmService.calculateNumberOfRowsByPurchasedFilms();
        double numberOfPage = (double)rows / purchasedFilmsOnPage;
        int nOfPages = (int)Math.ceil(numberOfPage);
        return nOfPages;
    }
}
