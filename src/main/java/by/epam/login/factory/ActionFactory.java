package by.epam.login.factory;

import by.epam.login.command.Command;
import by.epam.login.command.CommandType;
import by.epam.login.command.impl.EmptyCommand;
import by.epam.login.manager.MessageManager;

import javax.servlet.http.HttpServletRequest;

public class ActionFactory {
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
