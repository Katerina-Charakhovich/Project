package by.epam.project.command.factory;

import by.epam.project.command.Command;
import by.epam.project.command.CommandType;
import by.epam.project.command.CommandException;
import by.epam.project.command.impl.EmptyCommand;
import by.epam.project.command.RequestAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Action factory.
 */
public class ActionFactory {
    private static final String WRONG_ACTION = "Message.wrongaction";

    private ActionFactory() {
    }

    /**
     * Define command command.
     *
     * @param request the request
     * @return the command
     * @throws CommandException the command exception
     */
    public static Command defineCommand(HttpServletRequest request) throws CommandException {
        Command current = new EmptyCommand();
        String action = request.getParameter(RequestAttribute.COMMAND);
        return getCommand(request, current, action);
    }

    /**
     * Gets command.
     *
     * @param request the request
     * @param current the current
     * @param action  the action
     * @return the command
     * @throws CommandException the command exception
     */
    public static Command getCommand(HttpServletRequest request, Command current, String action)
            throws CommandException {
        if (action == null || action.isEmpty()) {
            return current;
        }
        try {
            CommandType currentType = CommandType.valueOf(action.toUpperCase());
            current = currentType.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            throw new CommandException("Wrong action", e);
        }
        return current;
    }
}
