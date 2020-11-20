package by.epam.project.command.impl;

import by.epam.project.command.Command;
import by.epam.project.command.PathToPage;
import by.epam.project.command.Router;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Empty command.
 */
public class EmptyCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        String page = PathToPage.INDEX_PAGE;
        return new Router(page);
    }
}
