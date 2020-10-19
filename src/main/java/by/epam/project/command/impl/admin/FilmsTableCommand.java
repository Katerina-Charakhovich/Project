package by.epam.project.command.impl.admin;

import by.epam.project.command.Command;
import by.epam.project.command.exception.CommandException;
import by.epam.project.command.manager.ConfigurationManager;
import by.epam.project.entity.Router;
import by.epam.project.entity.impl.Film;
import by.epam.project.entity.impl.FilmInfo;
import by.epam.project.service.MediaService;
import by.epam.project.service.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class FilmsTableCommand implements Command {
    private static final String LIST = "films";
    private static final String CURRENT_PAGE= "currentPage";
    private static final String FILMS_ON_PAGE = "filmsOnPage";
    private static final String NUMBER_OF_PAGES = "noOfPages";
    private static final String LIST_INFO = "filmsInfo";
    public static final Logger LOGGER = LogManager.getLogger();
    private final MediaService mediaService = MediaService.getInstance();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = ConfigurationManager.getProperty("path.page.filmsTable");
        String current = request.getParameter(CURRENT_PAGE) == null ? "1" : request.getParameter(CURRENT_PAGE);
        String countOfFilms = request.getParameter(FILMS_ON_PAGE)== null ? "5" : request.getParameter(FILMS_ON_PAGE);
        int currentPage = Integer.parseInt(current);
        int filmsOnPage = Integer.parseInt(countOfFilms);
        List<Film>films;
        List<FilmInfo>filmsInfo = new ArrayList<>();
        try {
            films = mediaService.findAllUndeletedFilms(currentPage,filmsOnPage);
            for (Film film : films) {
                FilmInfo filmInfo = mediaService.findInfoById(film.getId());
                filmsInfo.add(filmInfo);
            }

            int rows = mediaService.getNumberOfRows();
            int nOfPages = rows / filmsOnPage;
            if (nOfPages % filmsOnPage > 0) {
                nOfPages++;
            }
            request.getSession().setAttribute(NUMBER_OF_PAGES, nOfPages);
            request.setAttribute(CURRENT_PAGE, currentPage);
            request.getSession().setAttribute(FILMS_ON_PAGE, filmsOnPage);
            request.getSession().setAttribute(LIST, films);
            request.getSession().setAttribute(LIST_INFO, filmsInfo);

        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR,"Command  initStartPage invalid",e);
            throw new CommandException("Command  initStartPage invalid",e);
        }
        return new Router(page, Router.Type.FORWARD);
    }
}
