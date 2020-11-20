package by.epam.project.command.impl.admin;

import by.epam.project.command.*;
import by.epam.project.command.exception.CommandException;
import by.epam.project.entity.impl.Film;
import by.epam.project.service.MediaService;
import by.epam.project.service.exception.ServiceException;
import by.epam.project.service.impl.MediaServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;


/**
 * Command for creation a movie
 */
public class CreateFilmCommand implements Command {
    public static final Logger LOGGER = LogManager.getLogger();
    private MediaService mediaService = MediaServiceImpl.getInstance();

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
                    link, currentYearOfCreation, filmName, PathToPage.FILM_CREATOR, RequestAttribute.EN);
            if (response != null) {
                request.setAttribute(RequestAttribute.FILM_NAME, filmName);
                request.setAttribute(RequestAttribute.CURRENT_DESCRIPTION, description);
                request.setAttribute(RequestAttribute.CURRENT_GENRE, genre);
                request.setAttribute(RequestAttribute.CURRENT_LINK, link);
                request.setAttribute(RequestAttribute.CURRENT_YEAR_OF_CREATION, currentYearOfCreation);
                return response;
            } else {
                router = processFilmCreation(request, filmName, description, RequestAttribute.EN, genre, link, currentYearOfCreation);
            }
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Command  CreateFilm invalid", e);
            throw new CommandException("Command  CreateFilm invalid", e);
        }
        return router;
    }

    /**
     * Film making process
     */
    private Router processFilmCreation(HttpServletRequest request, String filmName,
                                       String description, String language, String genre, String link, String yearOfCreation) throws ServiceException {
        Router router = null;
        if (!mediaService.isFilmExist(filmName)) {
            if (mediaService.createFilm(description, yearOfCreation, genre, link, filmName, RequestAttribute.EN)) {
                Film film = mediaService.findFilmByName(filmName, language);
                request.getSession().setAttribute(RequestAttribute.FILM_ID, String.valueOf(film.getFilmId()));
                request.getSession().setAttribute(RequestAttribute.FILM_NAME, filmName);
                request.getSession().setAttribute(RequestAttribute.CURRENT_DESCRIPTION, description);
                request.getSession().setAttribute(RequestAttribute.CURRENT_GENRE, genre);
                request.getSession().setAttribute(RequestAttribute.CURRENT_LINK, link);
                request.getSession().setAttribute(RequestAttribute.CURRENT_YEAR_OF_CREATION, yearOfCreation);
                request.getSession().setAttribute(RequestAttribute.CURRENT_FILM_LANG, RequestAttribute.EN);
                request.getSession().setAttribute(RequestAttribute.CURRENT_FILM_AVATAR, film.getFilmAvatar());
                request.getSession().setAttribute(RequestAttribute.LANG_CHANGE_PROCESS_COMMAND,
                        RequestAttribute.COMMAND_FORWARD_TO_FILM_EDIT);
                router = new Router(PathToPage.FILM_EDIT_EN);
                router.useRedirect();
            }
        } else {
            request.setAttribute(RequestAttribute.ERROR_DATA_FILM_EXISTS, true);
            String page = PathToPage.FILM_CREATOR;
            return new Router(page);
        }
        if (router == null) {
            router = new Router(PathToPage.ERROR_PAGE);
            request.setAttribute(RequestAttribute.ERROR, RequestAttribute.ERROR_MESSAGE_FOR_CREATE_FILM);
        }
        return router;
    }
}
