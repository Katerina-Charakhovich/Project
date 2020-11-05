package by.epam.project.command.impl;

import by.epam.project.command.Command;
import by.epam.project.command.PathToPage;
import by.epam.project.command.Router;

import javax.servlet.http.HttpServletRequest;


public class EmptyCommand implements Command {
    /**
     * The type Empty command.
     */

    @Override
    public Router execute(HttpServletRequest request) {
        String page = PathToPage.INDEX_PAGE;
        return new Router(page);
    }
}
