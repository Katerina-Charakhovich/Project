package by.epam.project.command.impl;

import by.epam.project.command.Command;
import by.epam.project.command.PathToPage;
import by.epam.project.command.RequestAttribute;
import by.epam.project.command.Router;
import by.epam.project.command.exception.CommandException;
import by.epam.project.entity.impl.Film;
import by.epam.project.service.MediaService;
import by.epam.project.service.exception.ServiceException;
import by.epam.project.service.impl.MediaServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Command to search movies by name, description or genre
 */
public class SearchCommand implements Command {

    public static final Logger LOGGER = LogManager.getLogger();
    private MediaService mediaService = MediaServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = PathToPage.SEARCH_RESULT;
        String searchRequest = request.getParameter(RequestAttribute.SEARCH_REQUEST);
        String language = (String) request.getSession().getAttribute(RequestAttribute.LANGUAGE);
        String searchContent = request.getParameter(RequestAttribute.SEARCH_CONTENT);
        List<Film> films;

        request.getSession().setAttribute(RequestAttribute.LANG_CHANGE_PROCESS_COMMAND,
                RequestAttribute.COMMAND_SEARCH);
        if (searchContent == null || searchContent.isEmpty()) {
            request.setAttribute(RequestAttribute.ERROR_SEARCH, true);
            return new Router(page);
        }
        try {
            films = mediaService.findFilms(searchContent, searchRequest, language.toLowerCase());
            if (films != null && !films.isEmpty()) {
                request.setAttribute(RequestAttribute.LIST_FILMS, films);
            } else {
                request.setAttribute(RequestAttribute.ERROR_SEARCH, true);
            }
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Command  Search invalid", e);
            throw new CommandException("Command  Search invalid", e);
        }
        return new Router(page);
    }
}
