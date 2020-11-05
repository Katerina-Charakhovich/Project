package by.epam.project.command.impl.admin;

import by.epam.project.command.Command;
import by.epam.project.command.PathToPage;
import by.epam.project.command.RequestAttribute;
import by.epam.project.command.Router;
import by.epam.project.command.CommandException;
import by.epam.project.entity.impl.Film;
import by.epam.project.service.MediaService;
import by.epam.project.service.exception.ServiceException;
import by.epam.project.service.impl.MediaServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;



public class ForwardToEditFilmCommand implements Command {

    public static final Logger LOGGER = LogManager.getLogger();
    private MediaService mediaService = MediaServiceImpl.getInstance();

    /**
     * Forward to edit film command.
     */
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = PathToPage.FILM_EDIT_EN;
        String currentFilmId = request.getParameter(RequestAttribute.FILM_ID)
                == null ? (String) request.getSession().getAttribute(RequestAttribute.FILM_ID)
                : request.getParameter(RequestAttribute.FILM_ID);
        long filmId = Long.parseLong(currentFilmId);
        String currentYearOfCreation = request.getParameter(RequestAttribute.CURRENT_YEAR_OF_CREATION)
                == null ? (String) request.getSession().getAttribute(RequestAttribute.CURRENT_YEAR_OF_CREATION)
                : request.getParameter(RequestAttribute.CURRENT_YEAR_OF_CREATION);
        String currentFilmLang = request.getParameter(RequestAttribute.CURRENT_FILM_LANG)
                == null ? (String) request.getSession().getAttribute(RequestAttribute.CURRENT_FILM_LANG)
                : request.getParameter(RequestAttribute.CURRENT_FILM_LANG);
        String active = request.getParameter(RequestAttribute.FILM_ACTIVE);
        Film film;
        try {
            film = mediaService.findFilmById(filmId, currentFilmLang);
            request.getSession().setAttribute(RequestAttribute.FILM_NAME, film.getFilmName());
            request.getSession().setAttribute(RequestAttribute.CURRENT_DESCRIPTION, film.getFilmInfo().getDescription());
            request.getSession().setAttribute(RequestAttribute.CURRENT_GENRE, film.getFilmInfo().getGenre());
            request.getSession().setAttribute(RequestAttribute.CURRENT_LINK, film.getFilmInfo().getLink());
            request.getSession().setAttribute(RequestAttribute.CURRENT_FILM_AVATAR, film.getFilmAvatar());
            request.getSession().setAttribute(RequestAttribute.CURRENT_FILM_LANG, currentFilmLang);
            request.getSession().setAttribute(RequestAttribute.CURRENT_YEAR_OF_CREATION, currentYearOfCreation);
            request.getSession().setAttribute(RequestAttribute.FILM_ID, String.valueOf(film.getFilmId()));
            request.getSession().setAttribute(RequestAttribute.FILM_ACTIVE, active);
            request.setAttribute(RequestAttribute.LANG_CHANGE_PROCESS_COMMAND, RequestAttribute.COMMAND_FORWARD_TO_FILM_EDIT);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Command  ForwardToEditProfile invalid", e);
            throw new CommandException("Command  ForwardToEditProfile invalid", e);
        }
        return new Router(page);
    }
}
