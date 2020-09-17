package by.epam.project.command.factory;

import by.epam.project.command.Command;
import by.epam.project.command.CommandType;
import by.epam.project.command.impl.EmptyCommand;
import by.epam.project.command.manager.MessageManager;

import javax.servlet.http.HttpServletRequest;

public class ActionFactory {
    private ActionFactory() {
    }

    public static Command defineCommand(HttpServletRequest request) {
        Command current = new EmptyCommand();
        String action = request.getParameter("command");
        if (action == null || action.isEmpty()) {
            return current;
        }
        try {
            CommandType currentType = CommandType.valueOf(action.toUpperCase());
            current = currentType.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            request.setAttribute("wrongAction", action +
                    MessageManager.getProperty("message.wrongaction"));
        }
        return current;
    }
}
