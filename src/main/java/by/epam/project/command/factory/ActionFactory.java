package by.epam.project.command.factory;

import by.epam.project.command.Command;
import by.epam.project.command.CommandType;
import by.epam.project.command.exception.CommandException;
import by.epam.project.command.impl.EmptyCommand;
import by.epam.project.command.RequestAttribute;

import javax.servlet.http.HttpServletRequest;

public class ActionFactory {
    private static final String WRONG_ACTION="Message.wrongaction";
    private ActionFactory() {
    }

    public static Command defineCommand(HttpServletRequest request) throws CommandException{
        Command current = new EmptyCommand();
        String action = request.getParameter(RequestAttribute.COMMAND);
        return getCommand(request, current, action);
    }

    public static Command getCommand(HttpServletRequest request, Command current, String action)
            throws CommandException{
        if (action == null || action.isEmpty()) {
            return current;
        }
        try {
            CommandType currentType = CommandType.valueOf(action.toUpperCase());
            current = currentType.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            throw new CommandException("Wrong action",e);
        }
        return current;
    }
}
