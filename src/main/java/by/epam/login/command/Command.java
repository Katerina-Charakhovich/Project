package by.epam.login.command;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    String execute (HttpServletRequest request);
}
