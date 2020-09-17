package by.epam.project.command;

import by.epam.project.dao.exception.DaoException;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    String execute(HttpServletRequest request) throws DaoException;
}
