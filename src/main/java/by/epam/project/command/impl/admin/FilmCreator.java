package by.epam.project.command.impl.admin;

import by.epam.project.command.Command;
import by.epam.project.command.PathToPage;
import by.epam.project.command.RequestAttribute;
import by.epam.project.command.Router;
import by.epam.project.command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class FilmCreator implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = PathToPage.FILM_CREATOR;
        try {
            FileInputStream fileInputStream = new FileInputStream("pagecontent_en.properties");


        String filmName = RequestAttribute.FILM_NAME;
        String filmNameForPropEn= RequestAttribute.FILM_NAME_FOR_PROP_EN;
        String filmNameForPropRu = RequestAttribute.FILM_NAME_FOR_PROP_RU;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new Router(page);
    }
}
