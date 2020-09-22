package by.epam.project.command;

import by.epam.project.dao.exception.DaoException;
import by.epam.project.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    String execute(HttpServletRequest request) throws DaoException, ServiceException;
}
