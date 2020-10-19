package by.epam.project.command.impl.user;

import by.epam.project.command.Command;
import by.epam.project.command.manager.ConfigurationManager;
import by.epam.project.entity.Router;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty("path.page.index");
        String currentPage = (String) request.getSession().getAttribute("currentPage");
        request.getSession().invalidate();
        request.getSession().setAttribute("currentPage", currentPage);
        return new Router(page, Router.Type.FORWARD);
    }
}
