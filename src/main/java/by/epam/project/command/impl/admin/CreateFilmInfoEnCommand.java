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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

public class CreateFilmInfoEnCommand implements Command {
    public static final Logger LOGGER = LogManager.getLogger();
    private MediaService mediaService = MediaServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = PathToPage.FILM_INFO_CREATOR_EN;
        String genre = request.getParameter(RequestAttribute.CURRENT_GENRE);
        String genreForPropEn = request.getParameter(RequestAttribute.FILM_GENRE_FOR_PROP_EN);
        String description = request.getParameter(RequestAttribute.CURRENT_DESCRIPTION);
        String descriptionForPropEn = request.getParameter(RequestAttribute.FILM_DESCRIPTION_EN);
        String link = request.getParameter(RequestAttribute.CURRENT_LINK);
        String linkForPropEn = request.getParameter(RequestAttribute.LINK_OF_FILM_ENGLISH);
        String currentYearOfCreation = request.getParameter(RequestAttribute.CURRENT_YEAR_OF_CREATION);
        int yearOfCreation = Integer.parseInt(currentYearOfCreation);
        try {
            String filmName = (String)request.getSession().getAttribute(RequestAttribute.FILM_NAME);
            Film film = mediaService.findFilmByName(filmName);
            if (mediaService.createFilmInfo(link,genre,description,yearOfCreation,film.getFilmId())) {
                writeToProperties(genre, genreForPropEn, PathToPage.PATH_TO_SERVER_PROP_EN, PathToPage.PATH_TO_PROP_EN);
                writeToProperties(description, descriptionForPropEn, PathToPage.PATH_TO_SERVER_PROP_EN, PathToPage.PATH_TO_PROP_EN);
                writeToProperties(link, linkForPropEn, PathToPage.PATH_TO_SERVER_PROP_EN, PathToPage.PATH_TO_PROP_EN);
                request.getSession().setAttribute(RequestAttribute.CURRENT_GENRE,genre);
                request.getSession().setAttribute(RequestAttribute.CURRENT_DESCRIPTION,description);
                request.getSession().setAttribute(RequestAttribute.CURRENT_LINK,link);
                request.setAttribute(RequestAttribute.LANG_CHANGE_PROCESS_COMMAND,
                        RequestAttribute.COMMAND_CREATE_FILM_INFO_EN);
            }
        } catch (IOException | URISyntaxException | ServiceException e) {
            LOGGER.log(Level.ERROR, "Command  CreateFilmInfo invalid", e);
            throw new CommandException("Command  CreateFilmInfo invalid", e);
        }
        return new Router(page);
    }

    private void writeToProperties(String name, String nameForProp, String pathToProp, String pathToPropServer) throws IOException, URISyntaxException {
        InputStream fileInputStream = CreateFilmCommand.class.getClassLoader().getResourceAsStream(pathToProp);
        Properties properties = new Properties();
        properties.load(fileInputStream);
        fileInputStream.close();
        properties.setProperty(name, nameForProp);
        URL url = CreateFilmCommand.class.getClassLoader().getResource(pathToProp);
        FileOutputStream fileOutputStream = new FileOutputStream(new File(url.toURI()));
        FileOutputStream outputStream = new FileOutputStream(pathToPropServer);
        properties.store(fileOutputStream, null);
        properties.store(outputStream, null);
        fileOutputStream.close();
        outputStream.close();
    }
}
