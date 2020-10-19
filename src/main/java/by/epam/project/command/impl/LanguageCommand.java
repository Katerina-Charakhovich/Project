package by.epam.project.command.impl;

import by.epam.project.command.Command;
import by.epam.project.command.exception.CommandException;
import by.epam.project.command.factory.ActionFactory;
import by.epam.project.command.manager.ConfigurationManager;
import by.epam.project.entity.Router;


import javax.servlet.http.HttpServletRequest;

public class LanguageCommand implements Command {
    private static final String LANGUAGE = "language";
    private static final String COMMAND = "langChangeProcessCommand";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String localeFromNavButton = request.getParameter("currentNavLocale");
        String action = request.getParameter(COMMAND);

        if ("en".equalsIgnoreCase(localeFromNavButton)) {
            request.getSession().setAttribute(LANGUAGE, "RU");

        } else {
            request.getSession().setAttribute(LANGUAGE, "EN");
        }
        String currentPage = (String) request.getSession().getAttribute("currentPage");
        Router router = new Router(currentPage, Router.Type.FORWARD);

        if (action != null && !action.isEmpty()) {
            Command current = new EmptyCommand();
            current = ActionFactory.getCommand(request, current, action);
            router = current.execute(request);
        }
        router.setPage(currentPage != null ? currentPage : ConfigurationManager.getProperty("path.page.login"));
        return router;
    }
}
