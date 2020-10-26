package by.epam.project.command;

import by.epam.project.command.exception.CommandException;


import javax.servlet.http.HttpServletRequest;

public interface Command {
    Router execute(HttpServletRequest request) throws CommandException;
}
