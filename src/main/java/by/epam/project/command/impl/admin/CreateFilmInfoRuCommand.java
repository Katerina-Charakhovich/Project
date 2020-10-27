package by.epam.project.command.impl.admin;

import by.epam.project.command.Command;
import by.epam.project.command.PathToPage;
import by.epam.project.command.RequestAttribute;
import by.epam.project.command.Router;
import by.epam.project.command.exception.CommandException;
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

public class CreateFilmInfoRuCommand implements Command {
    public static final Logger LOGGER = LogManager.getLogger();
    private MediaService mediaService = MediaServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = PathToPage.FILM_INFO_CREATOR_RU;
        String genre = (String)request.getSession().getAttribute(RequestAttribute.CURRENT_GENRE);
        String genreForPropRu = request.getParameter(RequestAttribute.FILM_GENRE_FOR_PROP_RU);
        String description = (String)request.getSession().getAttribute(RequestAttribute.CURRENT_DESCRIPTION);
        String descriptionForPropRu = request.getParameter(RequestAttribute.FILM_DESCRIPTION_RU);
        String link = (String)request.getSession().getAttribute(RequestAttribute.CURRENT_LINK);
        String linkForPropRU = request.getParameter(RequestAttribute.LINK_OF_FILM_RUSSIAN);
        try {
            String filmName = (String) request.getSession().getAttribute(RequestAttribute.FILM_NAME);
            if (mediaService.isFilmExist(filmName)) {
                writeToProperties(genre, genreForPropRu, PathToPage.PATH_TO_SERVER_PROP_RU, PathToPage.PATH_TO_PROP_RU);
                writeToProperties(description, descriptionForPropRu, PathToPage.PATH_TO_SERVER_PROP_RU, PathToPage.PATH_TO_PROP_RU);
                writeToProperties(link, linkForPropRU, PathToPage.PATH_TO_SERVER_PROP_RU, PathToPage.PATH_TO_PROP_RU);
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

