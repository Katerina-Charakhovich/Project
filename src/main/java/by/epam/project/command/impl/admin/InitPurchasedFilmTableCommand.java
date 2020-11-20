package by.epam.project.command.impl.admin;

import by.epam.project.command.*;
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

/**
 * Command to initialize the purchased film table
 */
public class InitPurchasedFilmTableCommand implements Command {

    public static final Logger LOGGER = LogManager.getLogger();
    private PurchasedFilmService purchasedFilmService = PurchasedFilmServiceImpl.getInstance();
    private static final String DEFAULT_VALUE_OF_PURCHASED_FILM_PAGE = "1";
    private static final String DEFAULT_VALUE_OF_PURCHASED_FILMS_ON_PAGES = "8";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = PathToPage.PURCHASED_FILMS_TABLE;
        String current = CommandUtil.calculateTableParameter(request, RequestAttribute.CURRENT_PURCHASED_FILM_PAGE, DEFAULT_VALUE_OF_PURCHASED_FILM_PAGE);
        String countOfPurchasedFilms = CommandUtil.calculateTableParameter(request, RequestAttribute.PURCHASED_FILMS_ON_PAGE, DEFAULT_VALUE_OF_PURCHASED_FILMS_ON_PAGES);
        int currentPage = Integer.parseInt(current);
        String language = (String) request.getSession().getAttribute(RequestAttribute.LANGUAGE);
        int purchasedFilmsOnPage = Integer.parseInt(countOfPurchasedFilms);
        Map<User, Film> purchasedFilms;
        try {
            int nOfPages = calcNumberOfPages(purchasedFilmsOnPage);
            if (currentPage > nOfPages) {
                currentPage = nOfPages;
            }
            purchasedFilms = purchasedFilmService.findAllInfoAboutPurchasedFilms
                    (currentPage, purchasedFilmsOnPage, language.toLowerCase());
            request.getSession().setAttribute(RequestAttribute.NUMBER_OF_PAGES_PURCHASE_TABLE, nOfPages);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Command  initPurchasedFimTable invalid", e);
            throw new CommandException("Command  initPurchasedFimTable invalid", e);
        }
        request.getSession().setAttribute(RequestAttribute.CURRENT_PURCHASED_FILM_PAGE, currentPage);
        request.getSession().setAttribute(RequestAttribute.PURCHASED_FILMS_ON_PAGE, purchasedFilmsOnPage);
        request.setAttribute(RequestAttribute.MAP_PURCHASED_FILMS, purchasedFilms);
        request.setAttribute(RequestAttribute.LANG_CHANGE_PROCESS_COMMAND,
                RequestAttribute.COMMAND_PURCHASED_FILM_TABLE);
        return new Router(page);
    }

    /**
     * Counting the number of rows
     */
    private int calcNumberOfPages(int purchasedFilmsOnPage) throws ServiceException {
        int rows = purchasedFilmService.calculateNumberOfRowsByPurchasedFilms();
        double numberOfPage = (double) rows / purchasedFilmsOnPage;
        return (int) Math.ceil(numberOfPage);
    }
}
