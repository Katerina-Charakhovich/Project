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
    private static UserDaoImpl instance;
    private static final String SQL_SELECT_ALL_UNDELETED_USERS =
            "SELECT email,password FROM testlogin.users WHERE deleted = 0";
    private static final String SQL_SELECT_ALL_DELETED_USERS =
            "SELECT email,password FROM testlogin.users WHERE deleted = 1";
    private static final String SQL_SELECT_BY_LOGIN =
            "SELECT password, deleted FROM testlogin.users WHERE email = ? AND deleted = 0";
    private static final String SQL_SELECT_BY_ID =
            "SELECT email, password, deleted FROM testlogin.users WHERE id = ? AND deleted = 0";
    private static final String SQL_SELECT_DELETED_USER =
            "UPDATE testlogin.users SET deleted = 1";
    private static final String INSERT_NEW_USER =
            "INSERT INTO testlogin.users(email,password) VALUES(?,?)";

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }

    @Override
    public User findEntityById(long id) throws DaoException {
        User user = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID);
             ResultSet resultSet = statement.executeQuery();
        ) {
            statement.setLong(1, id);

            while (resultSet.next()) {
                String email = resultSet.getString(1);
                String password = resultSet.getString(2);
                int deleted = resultSet.getInt(3);
                user = new User(deleted, email, password);
            }
        } catch (SQLException | PoolException e) {
            LOGGER.log(Level.ERROR, MessageManager.getProperty("message.usernotfound"), e);
            throw new DaoException(MessageManager.getProperty("message.usernotfound"), e);
        }
        return user;
    }

    @Override
    public boolean delete(User user) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(long id) throws DaoException {
        User user = findEntityById(id);
        boolean result = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_DELETED_USER);
        ) {
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
    public boolean create(User user) throws DaoException {
        boolean result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_NEW_USER)
        ) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            result = statement.executeUpdate() > 0;
        } catch (SQLException | PoolException e) {
            LOGGER.log(Level.ERROR, MessageManager.getProperty("message.usernotfound"), e);
            throw new DaoException(MessageManager.getProperty("message.usernotfound"), e);
        }
        return result;
    }

    @Override
    public User update(User user) throws DaoException {
        return null;
    }

    @Override
    public User findUserByLogin(String email) throws DaoException {
        User user = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_LOGIN);

        ) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String password = resultSet.getString(1);
                int deleted = resultSet.getInt(2);
                user = new User(deleted, email, password);
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
             ResultSet resultSet = statement.executeQuery();
        ) {
            statement.setObject(1, user);

            while (resultSet.next()) {
                String email = resultSet.getString(1);
                String password = resultSet.getString(2);
                int deleted = resultSet.getInt(3);
                undeletedUsers.add(new User(deleted, email, password));
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
             ResultSet resultSet = statement.executeQuery()
        ) {
            statement.setObject(1, user);

            while (resultSet.next()) {
                String email = resultSet.getString(1);
                String password = resultSet.getString(2);
                int deleted = resultSet.getInt(3);
                deletedUsers.add(new User(deleted, email, password));
            }
        } catch (SQLException | PoolException e) {
            LOGGER.log(Level.ERROR, MessageManager.getProperty("message.usernotfound"), e);
            throw new DaoException(MessageManager.getProperty("message.usernotfound"), e);
        }
        return deletedUsers;
    }
}
