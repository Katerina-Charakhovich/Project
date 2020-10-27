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
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;


public class CreateFilmCommand implements Command {
    public static final Logger LOGGER = LogManager.getLogger();
    private MediaService mediaService = MediaServiceImpl.getInstance();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = PathToPage.FILM_CREATOR;
        try {
            String filmName = request.getParameter(RequestAttribute.FILM_NAME);
            String filmNameForPropEn = request.getParameter(RequestAttribute.FILM_NAME_FOR_PROP_EN);
            String filmNameForPropRu = request.getParameter(RequestAttribute.FILM_NAME_FOR_PROP_RU);
            if (mediaService.createFilm(filmName)){
                request.getSession().setAttribute(RequestAttribute.FILM_NAME,filmName);
                writeToProperties(filmName, filmNameForPropEn,PathToPage.PATH_TO_SERVER_PROP_EN,PathToPage.PATH_TO_PROP_EN);
                writeToProperties(filmName,filmNameForPropRu,PathToPage.PATH_TO_SERVER_PROP_RU,PathToPage.PATH_TO_PROP_RU);
            }
        } catch (IOException | URISyntaxException | ServiceException e) {
            LOGGER.log(Level.ERROR, "Command  CreateFilm invalid", e);
            throw new CommandException("Command  CreateFilm invalid", e);
        }
        return new Router(page);
    }

    private void writeToProperties(String filmName, String filmNameForProp, String pathToProp, String pathToPropServer) throws IOException, URISyntaxException {
        InputStream fileInputStream = CreateFilmCommand.class.getClassLoader().getResourceAsStream(pathToProp);
        Properties properties = new Properties();
        properties.load(fileInputStream);
        fileInputStream.close();
        properties.setProperty(filmName, filmNameForProp);
        URL url = CreateFilmCommand.class.getClassLoader().getResource(pathToProp);
        FileOutputStream fileOutputStream = new FileOutputStream(new File(url.toURI()));
        FileOutputStream outputStream = new FileOutputStream(pathToPropServer);
        properties.store(fileOutputStream, null);
        properties.store(outputStream,null);
        fileOutputStream.close();
        outputStream.close();
    }
}
