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
 * Movie editing command
 */
public class EditFilmCommand implements Command {

    public static final Logger LOGGER = LogManager.getLogger();
    private MediaService mediaService = MediaServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router response;
        try {
            String filmName = request.getParameter(RequestAttribute.FILM_NAME);
            String description = request.getParameter(RequestAttribute.CURRENT_DESCRIPTION);
            String genre = request.getParameter(RequestAttribute.CURRENT_GENRE);
            String link = request.getParameter(RequestAttribute.CURRENT_LINK);
            String currentYearOfCreation = request.getParameter(RequestAttribute.CURRENT_YEAR_OF_CREATION) == null
                    ? (String) request.getSession().getAttribute(RequestAttribute.CURRENT_YEAR_OF_CREATION)
                    : request.getParameter(RequestAttribute.CURRENT_YEAR_OF_CREATION);
            String currentFilmLang = (String) request.getSession().getAttribute(RequestAttribute.CURRENT_FILM_LANG);

            response = CommandUtil.validateCommonFilmFields(request, description, genre,
                    link, currentYearOfCreation, filmName, PathToPage.FILM_EDIT_EN, currentFilmLang);

            if (response != null) {
                request.setAttribute(RequestAttribute.FILM_NAME, filmName);
                request.setAttribute(RequestAttribute.CURRENT_DESCRIPTION, description);
                request.setAttribute(RequestAttribute.CURRENT_GENRE, genre);
                request.setAttribute(RequestAttribute.CURRENT_LINK, link);
                request.setAttribute(RequestAttribute.CURRENT_YEAR_OF_CREATION, currentYearOfCreation);
                return response;
            } else {
                response = processFilmUpdate(request, filmName, description, genre, link, currentYearOfCreation);
            }
            changeActiveFilm(request, filmName, currentFilmLang);
            if (RequestAttribute.EN.equals(currentFilmLang)) {
                request.getSession().setAttribute(RequestAttribute.FILM_NAME, filmName);
            } else {
                request.setAttribute(RequestAttribute.FILM_NAME, filmName);
            }

            request.setAttribute(RequestAttribute.CURRENT_DESCRIPTION, description);
            request.setAttribute(RequestAttribute.CURRENT_GENRE, genre);
            request.setAttribute(RequestAttribute.CURRENT_LINK, link);
            request.setAttribute(RequestAttribute.CURRENT_YEAR_OF_CREATION, currentYearOfCreation);
            request.getSession().setAttribute(RequestAttribute.LANG_CHANGE_PROCESS_COMMAND, RequestAttribute.COMMAND_FILM);
            Film film = mediaService.findFilmByName(filmName, currentFilmLang);
            request.setAttribute(RequestAttribute.CURRENT_FILM_LANG, currentFilmLang);
            request.setAttribute(RequestAttribute.CURRENT_FILM_AVATAR, film.getFilmAvatar());
            request.getSession().setAttribute(RequestAttribute.BACK_BUTTON_PAGE_ADDRESS,
                    CommandType.ADMIN_PAGE_FILMS.name());

        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Command  EditFilm invalid", e);
            throw new CommandException("Command  EditFilm invalid", e);
        }
        return response;
    }

    /**
     * Movie status change
     */
    private void changeActiveFilm(HttpServletRequest request, String filmName, String currentFilmLang) throws ServiceException {
        Film film = mediaService.findFilmByName(filmName, currentFilmLang);
        if (request.getSession().getAttribute(RequestAttribute.FILM_ACTIVE) != null) {
            film = mediaService.changeActiveFilm(film);
            request.setAttribute(RequestAttribute.FILM_ACTIVE, film.isActive());
        }
    }

    /**
     * Preparing to edit a movie
     */
    private Router processFilmUpdate(HttpServletRequest request, String filmName, String description,
                                     String genre, String link, String currentYearOfCreation) throws ServiceException {
        String currentFilmId = request.getParameter(RequestAttribute.FILM_ID) == null ? (String) request.getSession().getAttribute(RequestAttribute.FILM_ID) : request.getParameter(RequestAttribute.FILM_ID);
        long filmId = Long.parseLong(currentFilmId);
        String currentFilmLang = (String) request.getSession().getAttribute(RequestAttribute.CURRENT_FILM_LANG);
        String page = PathToPage.ERROR_PAGE;
        if (RequestAttribute.EN.equals(currentFilmLang)) {
            page = filmUpdateEn(request, filmName, description, genre, link, currentYearOfCreation, filmId, currentFilmLang);
        }
        if (currentFilmLang.equals(RequestAttribute.RU)) {
            page = filmUpdateRu(request, filmName, description, genre, link, filmId, currentFilmLang);
        }
        return new Router(page);
    }

    /**
     * Russian movie update
     */
    private String filmUpdateRu(HttpServletRequest request, String filmName, String description,
                                String genre, String link, long filmId, String currentFilmLang) throws ServiceException {
        String page = PathToPage.INIT_FILM_PAGE;
        Film film = mediaService.updateFilm(filmId, filmName, description, genre, currentFilmLang, link);
        if (film == null) {
            page = PathToPage.ERROR_PAGE;
            request.setAttribute(RequestAttribute.ERROR, "Unsuccessful attempt to update the movie");
        } else {
            request.setAttribute(RequestAttribute.FILM_ID, film.getFilmId());
        }
        return page;
    }

    /**
     * English movie update
     */
    private String filmUpdateEn(HttpServletRequest request, String filmName, String description, String genre,
                                String link, String currentYearOfCreation, long filmId, String currentFilmLang) throws ServiceException {
        String page = PathToPage.INIT_FILM_PAGE;
        Film film = mediaService.updateInfoEn(filmId, filmName, description, genre, currentFilmLang, link, currentYearOfCreation);
        if (film == null) {
            page = PathToPage.ERROR_PAGE;
            request.setAttribute(RequestAttribute.ERROR, RequestAttribute.ERROR_MESSAGE_FOR_EDIT_FILM);
        } else {
            request.setAttribute(RequestAttribute.FILM_ID, film.getFilmId());
        }
        return page;
    }
}
