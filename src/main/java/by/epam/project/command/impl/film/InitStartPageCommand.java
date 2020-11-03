package by.epam.project.command.impl.film;

import by.epam.project.command.Command;
import by.epam.project.command.exception.CommandException;
import by.epam.project.command.PathToPage;
import by.epam.project.command.RequestAttribute;
import by.epam.project.command.Router;
import by.epam.project.entity.impl.Film;
import by.epam.project.service.impl.MediaServiceImpl;
import by.epam.project.service.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class InitStartPageCommand implements Command {
    public static final Logger LOGGER = LogManager.getLogger();
    private MediaServiceImpl mediaService = MediaServiceImpl.getInstance();
    private static final String DEFAULT_VALUE_OF_FILM_PAGE = "1";
    private static final String DEFAULT_VALUE_OF_FILMS_ON_PAGES = "8";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = PathToPage.START_PAGE;
        String current = request.getParameter(RequestAttribute.CURRENT_FILM_PAGE)
                == null ? DEFAULT_VALUE_OF_FILM_PAGE : request.getParameter(RequestAttribute.CURRENT_FILM_PAGE);
        String countOfFilms = request.getParameter(RequestAttribute.FILMS_ON_PAGE)
                == null ? DEFAULT_VALUE_OF_FILMS_ON_PAGES : request.getParameter(RequestAttribute.FILMS_ON_PAGE);
        String language = (String)request.getSession().getAttribute(RequestAttribute.LANGUAGE);
        int currentPage = Integer.parseInt(current);
        int filmsOnPage = Integer.parseInt(countOfFilms);
        List<Film> films;
        try {
            films = mediaService.findAllActiveFilms(currentPage, filmsOnPage,language.toLowerCase(),true);
            int rows = mediaService.calculateNumberOfRows();
            int nOfPages = rows / filmsOnPage;
            if (nOfPages * filmsOnPage < rows) {
                nOfPages++;
            }
            request.setAttribute(RequestAttribute.NUMBER_OF_PAGES, nOfPages);

        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Command  initStartPage invalid", e);
            throw new CommandException("Command  initStartPage invalid", e);
        }
        request.setAttribute(RequestAttribute.CURRENT_FILM_PAGE, currentPage);
        request.setAttribute(RequestAttribute.FILMS_ON_PAGE, filmsOnPage);
        request.setAttribute(RequestAttribute.LIST_FILMS, films);
        request.setAttribute(RequestAttribute.LANG_CHANGE_PROCESS_COMMAND,
                RequestAttribute.COMMAND_INIT_START_PAGE);
        return new Router(page);
    }
}


