package by.epam.project.command.impl.film;

import by.epam.project.command.Command;
import by.epam.project.command.exception.CommandException;
import by.epam.project.command.manager.ConfigurationManager;
import by.epam.project.entity.Router;
import by.epam.project.entity.impl.FilmInfo;
import by.epam.project.service.MediaService;
import by.epam.project.service.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class FilmCommand implements Command {
    private static final String CURRENT_FILM_NAME = "filmName";
    private static final String CURRENT_FILM_ID = "filmId";
    private static final String CURRENT_REAL_FILM_NAME = "realName";
    private static final String CURRENT_DESCRIPTION = "description";
    private static final String CURRENT_YEAR_OF_CREATION = "yearOfCreation";
    private static final String CURRENT_GENRE = "genre";
    private static final String CURRENT_LINK = "link";
    private static final MediaService mediaService = MediaService.getInstance();
    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = ConfigurationManager.getProperty("path.page.film");
        FilmInfo filmInfo;
        String currentFilmId = request.getParameter(CURRENT_FILM_ID);
        long id = Long.parseLong(currentFilmId);
        String filmName = request.getParameter(CURRENT_FILM_NAME);
        String realName = request.getParameter(CURRENT_REAL_FILM_NAME);
        try {
            filmInfo = mediaService.findInfoById(id);
            request.getSession().setAttribute(CURRENT_DESCRIPTION, filmInfo.getDescription());
            request.getSession().setAttribute(CURRENT_YEAR_OF_CREATION, filmInfo.getYearOfCreation());
            request.getSession().setAttribute(CURRENT_GENRE, filmInfo.getGenre());
            request.getSession().setAttribute(CURRENT_LINK, filmInfo.getLink());
            request.getSession().setAttribute(CURRENT_FILM_NAME, filmName);
            request.getSession().setAttribute(CURRENT_REAL_FILM_NAME, realName);

        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Film not found", e);
            throw new CommandException("Film not found", e);
        }
        return new Router(page, Router.Type.FORWARD);
    }
}
