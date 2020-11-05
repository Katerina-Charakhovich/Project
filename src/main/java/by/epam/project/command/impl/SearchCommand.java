package by.epam.project.command.impl;

import by.epam.project.command.Command;
import by.epam.project.command.PathToPage;
import by.epam.project.command.RequestAttribute;
import by.epam.project.command.Router;
import by.epam.project.command.CommandException;
import by.epam.project.entity.impl.Film;
import by.epam.project.service.exception.ServiceException;
import by.epam.project.service.impl.MediaServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public class SearchCommand implements Command {
    /**
     * The type Search command.
     */

    public static final Logger LOGGER = LogManager.getLogger();
    private MediaServiceImpl mediaService = MediaServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = PathToPage.SEARCH_RESULT;
        String searchRequest = request.getParameter(RequestAttribute.SEARCH_REQUEST);
        String language = (String) request.getSession().getAttribute(RequestAttribute.LANGUAGE);
        String searchContent = request.getParameter(RequestAttribute.SEARCH_CONTENT);
        List<Film> films;

        if (searchContent == null || searchContent.isEmpty()) {
            throw new CommandException("Сontent is empty or equal null");
        }
        try {
            films = mediaService.findFilms(searchContent, searchRequest, language.toLowerCase());
            if (films != null && !films.isEmpty()) {
                request.setAttribute(RequestAttribute.LIST_FILMS, films);
            } else {
                boolean errorSearch = true;
                request.setAttribute(RequestAttribute.ERROR_SEARCH, errorSearch);
            }
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Command  Search invalid", e);
            throw new CommandException("Command  Search invalid", e);
        }
        return new Router(page);
    }
}
