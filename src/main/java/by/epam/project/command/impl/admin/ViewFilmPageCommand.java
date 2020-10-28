package by.epam.project.command.impl.admin;

import by.epam.project.command.Command;
import by.epam.project.command.exception.CommandException;
import by.epam.project.command.PathToPage;
import by.epam.project.command.RequestAttribute;
import by.epam.project.command.Router;
import by.epam.project.entity.impl.FilmInfo;
import by.epam.project.service.impl.MediaServiceImpl;
import by.epam.project.service.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ViewFilmPageCommand implements Command {
    public static final Logger LOGGER = LogManager.getLogger();
    private MediaServiceImpl mediaServiceImpl = MediaServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = PathToPage.FILM_PAGE;
        FilmInfo filmInfo;
        String currentFilmId = request.getParameter(RequestAttribute.FILM_ID);
        long filmId = Long.parseLong(currentFilmId);
        String filmName = request.getParameter(RequestAttribute.CURRENT_FILM_NAME);
        String realName = request.getParameter(RequestAttribute.CURRENT_REAL_FILM_NAME);
        String language = (String)request.getSession().getAttribute(RequestAttribute.LANGUAGE);
        try {
            filmInfo = mediaServiceImpl.findInfoById(filmId,language.toLowerCase());
            request.getSession().setAttribute(RequestAttribute.CURRENT_DESCRIPTION, filmInfo.getDescription());
            request.getSession().setAttribute(RequestAttribute.CURRENT_YEAR_OF_CREATION, filmInfo.getYearOfCreation());
            request.getSession().setAttribute(RequestAttribute.CURRENT_GENRE, filmInfo.getGenre());
            request.getSession().setAttribute(RequestAttribute.CURRENT_LINK, filmInfo.getLink());
            request.getSession().setAttribute(RequestAttribute.CURRENT_FILM_NAME, filmName);
            request.getSession().setAttribute(RequestAttribute.CURRENT_REAL_FILM_NAME, realName);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Film not found", e);
            throw new CommandException("Film not found", e);
        }
        return new Router(page);
    }
}
