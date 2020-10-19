package by.epam.project.command.impl.film;

import by.epam.project.command.Command;
import by.epam.project.command.exception.CommandException;
import by.epam.project.command.manager.ConfigurationManager;
import by.epam.project.entity.Router;
import by.epam.project.entity.impl.Film;
import by.epam.project.service.MediaService;
import by.epam.project.service.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class InitStartPageCommand implements Command {
    private static final String LIST = "list";
    private static final String CURRENT_FILM_PAGE = "currentFilmPage";
    private static final String FILMS_ON_PAGE= "filmsOnPage";
    private static final String NUMBER_OF_PAGES = "noOfPages";
    public static final Logger LOGGER = LogManager.getLogger();
    private static final MediaService mediaService = MediaService.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = ConfigurationManager.getProperty("path.page.start");
        String current = request.getParameter(CURRENT_FILM_PAGE) == null ? "1" : request.getParameter(CURRENT_FILM_PAGE);
        String countOfFilms = request.getParameter(FILMS_ON_PAGE)== null ? "8" : request.getParameter(FILMS_ON_PAGE);
        int currentPage = Integer.parseInt(current);
        int filmsOnPage = Integer.parseInt(countOfFilms);
        List<Film> films;
        try { films = mediaService.findAllUndeletedFilms(currentPage,filmsOnPage);
            int rows = mediaService.getNumberOfRows();
            int nOfPages = rows / filmsOnPage;
            if (nOfPages % filmsOnPage > 0) {
                nOfPages++;
            }
            request.setAttribute(NUMBER_OF_PAGES, nOfPages);

        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR,"Command  initStartPage invalid",e);
            throw new CommandException("Command  initStartPage invalid",e);
        }
        request.setAttribute(CURRENT_FILM_PAGE, currentPage);
        request.setAttribute(FILMS_ON_PAGE, filmsOnPage);
        request.setAttribute(LIST, films);
        request.setAttribute("langChangeProcessCommand", "init_start_page");
        return new Router(page, Router.Type.FORWARD);
    }
}


