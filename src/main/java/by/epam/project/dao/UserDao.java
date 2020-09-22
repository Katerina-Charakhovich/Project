package by.epam.project.dao;

import by.epam.project.dao.exception.DaoException;
import by.epam.project.entity.impl.User;

import java.util.List;

public interface UserDao extends BaseDao<User> {
    User findUserByLogin(String login) throws DaoException;

    List<User> findAllUndeletedUsers(User user) throws DaoException;

    List<User> findAllDeletedUsers(User user) throws DaoException;
}
