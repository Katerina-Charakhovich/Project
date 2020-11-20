package by.epam.project.command.impl;

import by.epam.project.command.*;
import by.epam.project.command.exception.CommandException;
import by.epam.project.command.factory.ActionFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Command for changing the language in the application
 */
public class LanguageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String localeFromNavButton = request.getParameter(RequestAttribute.CURRENT_NAV_LOCALE) == null
                ? RequestAttribute.EN : request.getParameter(RequestAttribute.CURRENT_NAV_LOCALE);
        String action = request.getParameter(RequestAttribute.LANG_CHANGE_PROCESS_COMMAND) == null
                ? (String) request.getSession().getAttribute(RequestAttribute.LANG_CHANGE_PROCESS_COMMAND)
                : request.getParameter(RequestAttribute.LANG_CHANGE_PROCESS_COMMAND);
        String language = (String) request.getSession().getAttribute(RequestAttribute.LANGUAGE);
        if (RequestAttribute.EN.equalsIgnoreCase(localeFromNavButton) || RequestAttribute.EN.equalsIgnoreCase(language)) {
            request.getSession().setAttribute(RequestAttribute.LANGUAGE, RequestAttribute.LOCALE_RU);
        } else {
            request.getSession().setAttribute(RequestAttribute.LANGUAGE, RequestAttribute.LOCALE_EN);
        }
        request.setAttribute(RequestAttribute.LANGUAGE_CHANGED, true);
        String sessionCurrentPage = (String) request.getSession().getAttribute(RequestAttribute.CURRENT_PAGE);
        String currentPage = sessionCurrentPage == null ? PathToPage.LOGIN_PAGE : sessionCurrentPage;
        if (PathToPage.INDEX_PAGE.equals(currentPage) && RequestAttribute.EN.equalsIgnoreCase(language)) {
            currentPage = PathToPage.LOGIN_PAGE;
        }
        Router router = new Router(currentPage);
        if (action != null && !action.isEmpty()) {
            Command current = new EmptyCommand();
            current = ActionFactory.getCommand(current, action);
            router = current.execute(request);
        }
        return router;
    }
}
