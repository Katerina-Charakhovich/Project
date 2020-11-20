package by.epam.project.command.impl.admin;

import by.epam.project.command.*;
import by.epam.project.command.exception.CommandException;
import by.epam.project.entity.impl.FilmInfo;
import by.epam.project.service.MediaService;
import by.epam.project.service.impl.MediaServiceImpl;
import by.epam.project.service.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Command to view the movie page
 */
public class ViewFilmPageCommand implements Command {

    public static final Logger LOGGER = LogManager.getLogger();
    private MediaService mediaService = MediaServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = PathToPage.INIT_FILM_PAGE;
        String currentFilmId = request.getParameter(RequestAttribute.FILM_ID);
        long filmId = Long.parseLong(currentFilmId);
        String filmName = request.getParameter(RequestAttribute.CURRENT_FILM_NAME);
        String realName = request.getParameter(RequestAttribute.CURRENT_FILM_AVATAR);
        String language = (String) request.getSession().getAttribute(RequestAttribute.LANGUAGE);
        try {
            FilmInfo filmInfo = mediaService.findInfoById(filmId, language.toLowerCase());
            if (filmInfo == null) {
                throw new CommandException("Film info is broken. Please contact your system administrator");
            }
            request.getSession().setAttribute(RequestAttribute.CURRENT_DESCRIPTION, filmInfo.getDescription());
            request.getSession().setAttribute(RequestAttribute.CURRENT_YEAR_OF_CREATION, filmInfo.getYearOfCreation());
            request.getSession().setAttribute(RequestAttribute.CURRENT_GENRE, filmInfo.getGenre());
            request.getSession().setAttribute(RequestAttribute.CURRENT_LINK, filmInfo.getLink());
            request.getSession().setAttribute(RequestAttribute.CURRENT_FILM_NAME, filmName);
            request.getSession().setAttribute(RequestAttribute.CURRENT_FILM_AVATAR, realName);
            request.setAttribute(RequestAttribute.LANG_CHANGE_PROCESS_COMMAND,
                    RequestAttribute.COMMAND_VIEW_FILM_PAGE);
            request.getSession().setAttribute(RequestAttribute.BACK_BUTTON_PAGE_ADDRESS,
                    CommandType.ADMIN_PAGE_FILMS.name());
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Film not found", e);
            throw new CommandException("Film not found", e);
        }
        return new Router(page);
    }
}
