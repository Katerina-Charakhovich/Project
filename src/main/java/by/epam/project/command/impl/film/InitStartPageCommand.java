package by.epam.project.command.impl.film;

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
 * Using this command, the start page of the application is initialized
 */
public class InitStartPageCommand implements Command {

    public static final Logger LOGGER = LogManager.getLogger();
    private MediaService mediaService = MediaServiceImpl.getInstance();
    private static final String DEFAULT_VALUE_OF_FILM_PAGE = "1";
    private static final String DEFAULT_VALUE_OF_FILMS_ON_PAGES = "8";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = PathToPage.START_PAGE;
        String current = CommandUtil.calculateTableParameter(request, RequestAttribute.CURRENT_FILM_PAGE, DEFAULT_VALUE_OF_FILM_PAGE);
        String countOfFilms = CommandUtil.calculateTableParameter(request, RequestAttribute.FILMS_ON_PAGE, DEFAULT_VALUE_OF_FILMS_ON_PAGES);
        String language = (String) request.getSession().getAttribute(RequestAttribute.LANGUAGE);
        int currentPage = Integer.parseInt(current);
        int filmsOnPage = Integer.parseInt(countOfFilms);
        List<Film> films;
        try {
            int rows = mediaService.calculateNumberOfActiveRows();
            int nOfPages = rows / filmsOnPage;
            if (nOfPages * filmsOnPage < rows) {
                nOfPages++;
            }
            if (currentPage > nOfPages) {
                currentPage = nOfPages;
            }
            films = mediaService.findAllActiveFilms(currentPage, filmsOnPage, language.toLowerCase(), true);
            request.setAttribute(RequestAttribute.NUMBER_OF_PAGES, nOfPages);

        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Command  initStartPage invalid", e);
            throw new CommandException("Command  initStartPage invalid", e);
        }
        request.getSession().setAttribute(RequestAttribute.CURRENT_FILM_PAGE, currentPage);
        request.getSession().setAttribute(RequestAttribute.FILMS_ON_PAGE, filmsOnPage);
        request.setAttribute(RequestAttribute.LIST_FILMS, films);
        request.setAttribute(RequestAttribute.LANG_CHANGE_PROCESS_COMMAND,
                RequestAttribute.COMMAND_INIT_START_PAGE);
        return new Router(page);
    }
}


