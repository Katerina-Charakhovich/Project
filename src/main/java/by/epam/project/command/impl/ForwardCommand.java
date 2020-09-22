package by.epam.project.command.impl;

import by.epam.project.command.Command;
import by.epam.project.command.manager.ConfigurationManager;


import javax.servlet.http.HttpServletRequest;


public class ForwardCommand implements Command {
    private static final String PAGE = "page";
    @Override
    public String execute(HttpServletRequest request){
        String page = ConfigurationManager.getProperty(request.getParameter(PAGE));
        request.getSession().invalidate();
        return page;
    }
}
