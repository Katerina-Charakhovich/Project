package by.epam.project.service;

import by.epam.project.dao.exception.DaoException;

public interface UserService {
    boolean checkLoginAndPass(String enterLogin, String enterPass) throws DaoException;
}
