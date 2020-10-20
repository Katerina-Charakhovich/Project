package by.epam.project.command.impl;

import by.epam.project.command.Command;
import by.epam.project.command.manager.ConfigurationManager;
import by.epam.project.entity.Router;


import javax.servlet.http.HttpServletRequest;


public class ForwardCommand implements Command {
    private static final String PAGE = "page";

    @Override
    public Router execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty(request.getParameter(PAGE));
        return new Router(page, Router.Type.FORWARD);
    }
}
