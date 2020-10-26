package by.epam.project.command.impl;

import by.epam.project.command.Command;
import by.epam.project.command.PathToPage;
import by.epam.project.command.Router;


import javax.servlet.http.HttpServletRequest;


/**
 * The type Forward command.
 */
public class ForwardCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        String page = request.getParameter(PathToPage.NEXT_PAGE);
        return new Router(page);
    }
}
