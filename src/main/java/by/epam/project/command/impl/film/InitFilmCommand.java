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

public class InitFilmCommand implements Command {
    public static final Logger LOGGER = LogManager.getLogger();
    private  MediaServiceImpl mediaService = MediaServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = PathToPage.FILM_PAGE;
        Film film;
        String currentFilmId = request.getParameter(RequestAttribute.CURRENT_FILM_ID)==null ? (String)request.getSession().getAttribute(RequestAttribute.CURRENT_FILM_ID) : request.getParameter(RequestAttribute.CURRENT_FILM_ID) ;
        String language = (String) request.getSession().getAttribute(RequestAttribute.LANGUAGE);
        long id = Long.parseLong(currentFilmId);
        try {
            film = mediaService.findFilmById(id,language.toLowerCase());
            request.getSession().setAttribute(RequestAttribute.CURRENT_FILM_ID,currentFilmId);
            request.setAttribute(RequestAttribute.CURRENT_DESCRIPTION, film.getFilmInfo().getDescription());
            request.setAttribute(RequestAttribute.CURRENT_YEAR_OF_CREATION, film.getFilmInfo().getYearOfCreation());
            request.setAttribute(RequestAttribute.CURRENT_GENRE, film.getFilmInfo().getGenre());
            request.setAttribute(RequestAttribute.CURRENT_LINK, film.getFilmInfo().getLink());
            request.setAttribute(RequestAttribute.CURRENT_FILM_NAME, film.getFilmName());
            request.setAttribute(RequestAttribute.CURRENT_REAL_FILM_NAME, film.getRealName());
            request.setAttribute(RequestAttribute.LANG_CHANGE_PROCESS_COMMAND,
                    RequestAttribute.COMMAND_FILM);

        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Film not found", e);
            throw new CommandException("Film not found", e);
        }
        return new Router(page);
    }
}