package by.epam.project.dao;

import by.epam.project.dao.exception.DaoException;
import by.epam.project.entity.impl.User;

import java.util.List;

public interface UserDao extends BaseDao<User> {
    User findUserByLogin(String login) throws DaoException;

    List<User> findAllUndeletedUsers(int currentPage, int filmsOnPage) throws DaoException;

    User findUserWithTheAllInfoByLogin(String login) throws DaoException;

    User updateInfo(User user) throws DaoException;

    User updateAvatar(User user) throws DaoException;

    int getNumberOfRows() throws DaoException;

    User lockUser(User user) throws DaoException;

}
