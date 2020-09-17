package by.epam.project.dao.impl;


import by.epam.project.dao.UserDao;
import by.epam.project.pool.ConnectionPool;
import by.epam.project.dao.exception.DaoException;
import by.epam.project.entity.impl.User;
import by.epam.project.pool.exception.PoolException;
import by.epam.project.command.manager.MessageManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoImpl implements UserDao {
    public static final Logger LOGGER = LogManager.getLogger();
    private static final String SQL_SELECT_ALL_UNDELETED_USERS =
            "SELECT id, login,password FROM testlogin.users WHERE deleted = 0";
    private static final String SQL_SELECT_ALL_DELETED_USERS =
            "SELECT id, login,password FROM testlogin.users WHERE deleted = 1";
    private static final String SQL_SELECT_BY_LOGIN =
            "SELECT id, password, deleted FROM testlogin.users WHERE login = ? AND deleted = 0";
    private static final String SQL_SELECT_BY_ID =
            "SELECT login, password, deleted FROM testlogin.users WHERE id = ? AND deleted = 0";
    private static final String SQL_SELECT_DELETED_USER =
            "UPDATE testlogin.users SET deleted = 1";


    @Override
    public User findEntityById(long id) throws DaoException {
        User user = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID);
        ) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String login = resultSet.getString(2);
                String password = resultSet.getString(3);
                int deleted = resultSet.getInt(4);
                user = new User(id, deleted, login, password);
            }
        } catch (SQLException | PoolException e) {
            LOGGER.log(Level.ERROR, MessageManager.getProperty("message.usernotfound"), e);
            throw new DaoException(MessageManager.getProperty("message.usernotfound"), e);
        }
        return user;
    }

    @Override
    public boolean delete(Object o) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(long id) throws DaoException {
        User user = findEntityById(id);
        boolean result = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_DELETED_USER);
        ) {
            statement.executeUpdate();
            if (user.getDeleted() == 0) {
                statement.executeUpdate();
                result = true;
            }
        } catch (SQLException | PoolException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean create(Object o) throws DaoException {
        return false;
    }

    @Override
    public Object update(Object o) throws DaoException {
        return null;
    }

    @Override
    public User findUserByLogin(String login) throws DaoException {
        User user = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_LOGIN);
        ) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String password = resultSet.getString(3);
                int deleted = resultSet.getInt(4);
                user = new User(id, deleted, login, password);
            }
        } catch (SQLException | PoolException e) {
            LOGGER.log(Level.ERROR, MessageManager.getProperty("message.usernotfound"), e);
            throw new DaoException(MessageManager.getProperty("message.usernotfound"), e);
        }
        return user;
    }

    @Override
    public List<User> findAllUndeletedUsers(User user) throws DaoException {
        List<User> undeletedUsers = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_UNDELETED_USERS);
        ) {
            statement.setObject(1, user);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String login = resultSet.getString(2);
                String password = resultSet.getString(3);
                int deleted = resultSet.getInt(4);
                undeletedUsers.add(new User(id, deleted, login, password));
            }
        } catch (SQLException | PoolException e) {
            LOGGER.log(Level.ERROR, MessageManager.getProperty("message.usernotfound"), e);
            throw new DaoException(MessageManager.getProperty("message.usernotfound"), e);
        }
        return undeletedUsers;
    }

    @Override
    public List<User> findAllDeletedUsers(User user) throws DaoException {
        List<User> deletedUsers = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_DELETED_USERS);
        ) {
            statement.setObject(1, user);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String login = resultSet.getString(2);
                String password = resultSet.getString(3);
                int deleted = resultSet.getInt(4);
                deletedUsers.add(new User(id, deleted, login, password));
            }
        } catch (SQLException | PoolException e) {
            LOGGER.log(Level.ERROR, MessageManager.getProperty("message.usernotfound"), e);
            throw new DaoException(MessageManager.getProperty("message.usernotfound"), e);
        }
        return deletedUsers;
    }
}
