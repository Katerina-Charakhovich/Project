package by.epam.project.command.impl.user;

import by.epam.project.command.Command;
import by.epam.project.command.PathToPage;
import by.epam.project.command.Router;
import by.epam.project.command.RequestAttribute;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        String page = PathToPage.LOGIN_PAGE;
        String currentPage = (String) request.getSession().getAttribute(RequestAttribute.CURRENT_PAGE);
        String language = (String) request.getSession().getAttribute(RequestAttribute.LANGUAGE);
        request.getSession().invalidate();
        request.getSession().setAttribute(RequestAttribute.CURRENT_PAGE, currentPage);
        request.getSession().setAttribute(RequestAttribute.LANGUAGE, language);
        return new Router(page);
    }
}
