package by.epam.project.command.impl;

import by.epam.project.command.Command;
import by.epam.project.command.PathToPage;
import by.epam.project.command.RequestAttribute;
import by.epam.project.command.CommandException;
import by.epam.project.command.factory.ActionFactory;
import by.epam.project.command.Router;

import javax.servlet.http.HttpServletRequest;


public class LanguageCommand implements Command {
    private static final String LOCALE = "en";

    /**
     * The type Language command.
     */

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String localeFromNavButton = request.getParameter(RequestAttribute.CURRENT_NAV_LOCALE);
        String action = request.getParameter(RequestAttribute.LANG_CHANGE_PROCESS_COMMAND);
        if (LOCALE.equalsIgnoreCase(localeFromNavButton)) {
            request.getSession().setAttribute(RequestAttribute.LANGUAGE, RequestAttribute.LOCALE_RU);
        } else {
            request.getSession().setAttribute(RequestAttribute.LANGUAGE, RequestAttribute.LOCALE_EN);
        }
        String currentPage = (String) request.getSession().getAttribute(RequestAttribute.CURRENT_PAGE);
        Router router = new Router(currentPage);
        if (action != null && !action.isEmpty()) {
            Command current = new EmptyCommand();
            current = ActionFactory.getCommand(request, current, action);
            router = current.execute(request);
        }
        router.setPage(currentPage != null ? currentPage : PathToPage.LOGIN_PAGE);
        return router;
    }
}
