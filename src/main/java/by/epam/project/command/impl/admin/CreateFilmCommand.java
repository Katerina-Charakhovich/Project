package by.epam.project.command.impl.admin;

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


public class CreateFilmCommand implements Command {
    public static final Logger LOGGER = LogManager.getLogger();
    private MediaService mediaService = MediaServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = PathToPage.FILM_CREATOR;
        try {
            String filmNameEn = request.getParameter(RequestAttribute.FILM_NAME_EN);
            String language = (String) request.getSession().getAttribute(RequestAttribute.LANGUAGE);
            String description_en = request.getParameter(RequestAttribute.FILM_DESCRIPTION_EN);
            String genre_en = request.getParameter(RequestAttribute.FILM_GENRE_EN);
            String link_en = request.getParameter(RequestAttribute.LINK_OF_FILM_ENGLISH);
            String currentYearOfCreation = request.getParameter(RequestAttribute.CURRENT_YEAR_OF_CREATION);
            int yearOfCreation = Integer.parseInt(currentYearOfCreation);
            Film film;
            if (mediaService.createFilm(filmNameEn)) {
                film = mediaService.findFilmByName(filmNameEn, language);
                if (mediaService.createFilmInfo(link_en, genre_en, description_en, yearOfCreation, film.getFilmId(), language)) {
                    page = PathToPage.FILM_INFO_CREATOR_EN;
                }
            }
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Command  CreateFilm invalid", e);
            throw new CommandException("Command  CreateFilm invalid", e);
        }
        return new Router(page);
    }
}
