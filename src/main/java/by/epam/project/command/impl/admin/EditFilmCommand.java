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
                request.setAttribute(RequestAttribute.FILM_NAME_EN, filmName);
                request.setAttribute(RequestAttribute.FILM_DESCRIPTION_EN, description);
                request.setAttribute(RequestAttribute.FILM_GENRE_EN, genre);
                request.setAttribute(RequestAttribute.LINK_OF_FILM_ENGLISH, link);
                request.setAttribute(RequestAttribute.CURRENT_YEAR_OF_CREATION, currentYearOfCreation);
                return response;
            } else {
                response = processFilmUpdate(request, filmName, description, genre, link, currentYearOfCreation);
            }
            changeActiveFilm(request, filmName, currentFilmLang);

            request.getSession().setAttribute(RequestAttribute.FILM_NAME, filmName);
            request.setAttribute(RequestAttribute.CURRENT_DESCRIPTION, description);
            request.setAttribute(RequestAttribute.CURRENT_GENRE, genre);
            request.setAttribute(RequestAttribute.CURRENT_LINK, link);
            request.setAttribute(RequestAttribute.CURRENT_YEAR_OF_CREATION, currentYearOfCreation);
            request.setAttribute(RequestAttribute.CURRENT_FILM_AVATAR, request.getParameter(RequestAttribute.CURRENT_FILM_AVATAR));
            request.setAttribute(RequestAttribute.LANG_CHANGE_PROCESS_COMMAND, RequestAttribute.COMMAND_FILM_EDIT);

        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Command  EditFilm invalid", e);
            throw new CommandException("Command  EditFilm invalid", e);
        }
        return response;
    }

    private void changeActiveFilm(HttpServletRequest request, String filmName, String currentFilmLang) throws ServiceException {
        Film film = mediaService.findFilmByName(filmName, currentFilmLang);
        if (request.getSession().getAttribute(RequestAttribute.FILM_ACTIVE) != null){
            film = mediaService.changeActiveFilm(film);
            request.setAttribute(RequestAttribute.FILM_ACTIVE,film.isActive());
        }
    }

    private Router processFilmUpdate(HttpServletRequest request, String filmName,String description,
                                     String genre, String link, String currentYearOfCreation) throws ServiceException {
        String currentFilmId = request.getParameter(RequestAttribute.FILM_ID) == null ? (String) request.getSession().getAttribute(RequestAttribute.FILM_ID) : request.getParameter(RequestAttribute.FILM_ID);
        long filmId = Long.parseLong(currentFilmId);
        String currentFilmLang = (String) request.getSession().getAttribute(RequestAttribute.CURRENT_FILM_LANG);
        String page = PathToPage.ERROR_PAGE;
        if (currentFilmLang.equals(RequestAttribute.LANGUAGE_EN)) {
            page = filmUpdateEn(request, filmName, description, genre, link, currentYearOfCreation, filmId, currentFilmLang);
        }
        if (currentFilmLang.equals(RequestAttribute.LANGUAGE_RU)) {
            page = filmUpdateRu(request, filmName, description, genre, link, filmId, currentFilmLang);
        }
        return new Router(page);
    }

    private String filmUpdateRu(HttpServletRequest request, String filmName, String description,
                                String genre, String link, long filmId, String currentFilmLang) throws ServiceException {
        Film film;
        String page;
        page = PathToPage.INIT_FILM_PAGE;
        film = mediaService.updateFilm(filmId, filmName, description, genre, currentFilmLang, link);
        if (film == null) {
            page = PathToPage.ERROR_PAGE;
            request.setAttribute(RequestAttribute.ERROR, "Unsuccessful attempt to update the movie");
        } else {
            request.setAttribute(RequestAttribute.FILM_ID, film.getFilmId());
        }
        return page;
    }

    private String filmUpdateEn(HttpServletRequest request, String filmName, String description, String genre, String link, String currentYearOfCreation, long filmId, String currentFilmLang) throws ServiceException {
        Film film;
        String page;
        page = PathToPage.INIT_FILM_PAGE;
        film = mediaService.updateInfoEn(filmId, filmName, description, genre
                , currentFilmLang, link, currentYearOfCreation);
        if (film == null) {
            page = PathToPage.ERROR_PAGE;
            request.setAttribute(RequestAttribute.ERROR, "Unsuccessful attempt to update movie information");
        } else {
            request.setAttribute(RequestAttribute.FILM_ID, film.getFilmId());
        }
        return page;
    }
}
