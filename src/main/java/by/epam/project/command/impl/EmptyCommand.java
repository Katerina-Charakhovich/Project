package by.epam.project.command.impl;

import by.epam.project.command.Command;
import by.epam.project.command.manager.ConfigurationManager;
import by.epam.project.entity.Router;

import javax.servlet.http.HttpServletRequest;

public class EmptyCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty("path.page.index");
        return new Router(page, Router.Type.FORWARD);
    }
}
