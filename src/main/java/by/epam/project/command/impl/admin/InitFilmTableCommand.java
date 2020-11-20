package by.epam.project.command.impl.admin;

import by.epam.project.command.*;
import by.epam.project.command.exception.CommandException;
import by.epam.project.entity.impl.Film;
import by.epam.project.service.MediaService;
import by.epam.project.service.impl.MediaServiceImpl;
import by.epam.project.service.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Command to initialize the film table
 */
public class InitFilmTableCommand implements Command {

    public static final Logger LOGGER = LogManager.getLogger();
    private MediaService mediaService = MediaServiceImpl.getInstance();
    private static final String DEFAULT_VALUE_OF_FILM_PAGE = "1";
    private static final String DEFAULT_VALUE_OF_FILMS_ON_PAGES = "8";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = PathToPage.FILM_TABLE_PAGE;
        String current = CommandUtil.calculateTableParameter(request, RequestAttribute.CURRENT_TABLE_FILM_PAGE, DEFAULT_VALUE_OF_FILM_PAGE);
        String countOfFilms = CommandUtil.calculateTableParameter(request, RequestAttribute.FILMS_ON_PAGE_FILM_TABLE, DEFAULT_VALUE_OF_FILMS_ON_PAGES);
        String language = (String) request.getSession().getAttribute(RequestAttribute.LANGUAGE);
        int currentPage = Integer.parseInt(current);
        int filmsOnPage = Integer.parseInt(countOfFilms);
        try {
            int rows = mediaService.calculateNumberOfAllRows();
            int nOfPages = rows / filmsOnPage;
            if (nOfPages * filmsOnPage < rows) {
                nOfPages++;
            }
            if (currentPage > nOfPages) {
                currentPage = nOfPages;
            }
            List<Film> films = mediaService.findAllFilms(currentPage, filmsOnPage, language.toLowerCase());
            request.getSession().setAttribute(RequestAttribute.NUMBER_OF_PAGES_FILM_TABLE, nOfPages);
            request.getSession().setAttribute(RequestAttribute.CURRENT_TABLE_FILM_PAGE, currentPage);
            request.getSession().setAttribute(RequestAttribute.FILMS_ON_PAGE_FILM_TABLE, filmsOnPage);
            request.setAttribute(RequestAttribute.LIST_FILMS, films);
            request.setAttribute(RequestAttribute.LANG_CHANGE_PROCESS_COMMAND,
                    RequestAttribute.COMMAND_ADMIN_PAGE_FILMS);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Command  initFilmTable invalid", e);
            throw new CommandException("Command  initFilmTable invalid", e);
        }
        return new Router(page);
    }
}
