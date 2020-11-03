package by.epam.project.command.impl.admin;

import by.epam.project.command.*;
import by.epam.project.command.exception.CommandException;
import by.epam.project.entity.impl.Film;
import by.epam.project.service.MediaService;
import by.epam.project.service.exception.ServiceException;
import by.epam.project.service.impl.MediaServiceImpl;
import by.epam.project.util.ValidationMedia;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;


public class CreateFilmCommand implements Command {
    public static final Logger LOGGER = LogManager.getLogger();
    private MediaService mediaService = MediaServiceImpl.getInstance();
    private static String FILM_LANG = "en";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router;
        try {
            String filmName = request.getParameter(RequestAttribute.FILM_NAME);
            String description = request.getParameter(RequestAttribute.CURRENT_DESCRIPTION);
            String genre = request.getParameter(RequestAttribute.CURRENT_GENRE);
            String link = request.getParameter(RequestAttribute.CURRENT_LINK);
            String currentYearOfCreation = request.getParameter(RequestAttribute.CURRENT_YEAR_OF_CREATION);
            Router response = CommandUtil.validateCommonFilmFields(request, description, genre,
                    link, currentYearOfCreation, filmName, PathToPage.FILM_CREATOR, FILM_LANG);
            if (response != null) {
                request.setAttribute(RequestAttribute.FILM_NAME, filmName);
                request.setAttribute(RequestAttribute.CURRENT_DESCRIPTION, description);
                request.setAttribute(RequestAttribute.CURRENT_GENRE, genre);
                request.setAttribute(RequestAttribute.CURRENT_LINK, link);
                request.setAttribute(RequestAttribute.CURRENT_YEAR_OF_CREATION, currentYearOfCreation);

                return response;
            } else {
                router = processFilmCreation(request, filmName, description, FILM_LANG, genre, link, currentYearOfCreation);
            }
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Command  CreateFilm invalid", e);
            throw new CommandException("Command  CreateFilm invalid", e);
        }
        return router;
    }

    private Router processFilmCreation(HttpServletRequest request, String filmName,
                                       String description, String language, String genre, String link, String yearOfCreation) throws ServiceException {
        Film film;
        String page;
        Router router = null;
        if (mediaService.createFilm(description, yearOfCreation, genre, link, filmName, FILM_LANG)) {
            film = mediaService.findFilmByName(filmName, language);
            request.getSession().setAttribute(RequestAttribute.FILM_ID, film.getFilmId());
            request.getSession().setAttribute(RequestAttribute.FILM_NAME, filmName);
            request.getSession().setAttribute(RequestAttribute.CURRENT_DESCRIPTION, description);
            request.getSession().setAttribute(RequestAttribute.CURRENT_GENRE, genre);
            request.getSession().setAttribute(RequestAttribute.CURRENT_LINK, link);
            request.getSession().setAttribute(RequestAttribute.CURRENT_YEAR_OF_CREATION, yearOfCreation);
            request.getSession().setAttribute(RequestAttribute.CURRENT_FILM_LANG, FILM_LANG);
            page = PathToPage.FILM_EDIT_EN;
            router = new Router(page);
            router.useRedirect();
        }
        if (router == null) {
            router = new Router(PathToPage.ERROR_PAGE);
            request.setAttribute(RequestAttribute.ERROR, "Unsuccessful attempt to create a movie");
        }
        return router;
    }
}
