package by.epam.project.dao.impl;


import by.epam.project.dao.UserDao;
import by.epam.project.pool.ConnectionPool;
import by.epam.project.dao.exception.DaoException;
import by.epam.project.entity.impl.User;
import by.epam.project.pool.exception.PoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoImpl implements UserDao {
    public static final Logger LOGGER = LogManager.getLogger();
    private static UserDaoImpl instance;
    private static final String SQL_SELECT_ALL_USERS =
            "SELECT id, email,name, gender, country, locked FROM testlogin.users WHERE role = ? ORDER BY id DESC LIMIT ?,?";
    private static final String SQL_SELECT_BY_LOGIN =
            "SELECT id, email, password, locked, role FROM testlogin.users WHERE email = ?";
    private static final String SQL_SELECT_BY_ID =
            "SELECT id, email, password, locked, role FROM testlogin.users WHERE id = ?";
    private static final String SQL_SELECT_DELETED_USER =
            "UPDATE testlogin.users SET deleted = 1";
    private static final String INSERT_NEW_USER =
            "INSERT INTO testlogin.users(email,password) VALUES(?,?)";
    private static final String SQL_SELECT_THE_ALL_INFO_BY_LOGIN =
            "SELECT id, email, password, locked, role, name, gender, country,avatar, about_me FROM testlogin.users WHERE email = ?";
    private static final String SQL_SELECT__THE_ALL_INFO_BY_ID =
            "SELECT id, email, password, locked, role, gender, country,about_me FROM testlogin.users WHERE id = ?";
    private static final String USER_NOT_FOUND = "User not found";
    private static final String SQL_UPDATE_INFO_USER =
            "UPDATE testlogin.users SET name = ?, gender = ?, country = ?, about_me = ? WHERE email = ?";
    private static final String SQL_UPDATE_AVATAR_USER =
            "UPDATE testlogin.users SET avatar = ? WHERE email = ?";
    private static final String SQL_SELECT_COUNT_OF_ROWS =
            "SELECT COUNT(id) FROM testlogin.users";
    private static final String SQL_LOCK_USER = "UPDATE testlogin.users SET locked = ? WHERE email = ?";
    private static final String SQL_UNLOCK_USER = "UPDATE testlogin.users SET locked = ? WHERE email = ?";

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
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                long userId = resultSet.getLong("id");
                String role = resultSet.getString("role");
                user = new User(userId, email, password, role);
            }
        } catch (SQLException | PoolException e) {
            LOGGER.log(Level.ERROR, USER_NOT_FOUND, e);
            throw new DaoException(USER_NOT_FOUND, e);
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
        } catch (SQLException | PoolException e) {
            LOGGER.log(Level.ERROR, "User has already been deleted", e);
            throw new DaoException("User has already been deleted", e);
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
            LOGGER.log(Level.ERROR, USER_NOT_FOUND, e);
            throw new DaoException(USER_NOT_FOUND, e);
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
                String userEmail = resultSet.getString("email");
                String password = resultSet.getString("password");
                long id = resultSet.getLong("id");
                String role = resultSet.getString("role");
                user = new User(id, userEmail, password, role);
            }
        } catch (SQLException | PoolException e) {
            LOGGER.log(Level.ERROR, USER_NOT_FOUND, e);
            throw new DaoException(USER_NOT_FOUND, e);
        }
        return user;
    }


    @Override
    public List<User> findAllUndeletedUsers(int currentPage, int usersOnPage) throws DaoException {
        List<User> undeletedUsers = new ArrayList<>();
        int start = currentPage * usersOnPage - usersOnPage;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_USERS);

        ) {
            statement.setInt(2, start);
            statement.setInt(3, usersOnPage);
            statement.setString(1,"user");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setEmail(resultSet.getString("email"));
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setCountry(resultSet.getString("country"));
                user.setGender(resultSet.getString("gender"));
                user.setLocked(resultSet.getString("locked"));
                undeletedUsers.add(user);
            }
        } catch (SQLException | PoolException e) {
            LOGGER.log(Level.ERROR, USER_NOT_FOUND, e);
            throw new DaoException(USER_NOT_FOUND, e);
        }
        return undeletedUsers;
    }

    @Override
    public User findUserWithTheAllInfoByLogin(String email) throws DaoException {
        User user = new User();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_THE_ALL_INFO_BY_LOGIN);

        ) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setLocked(resultSet.getString("locked"));
                user.setRole(resultSet.getString("role"));
                user.setGender(resultSet.getString("gender"));
                user.setCountry(resultSet.getString("country"));
                user.setAboutMe(resultSet.getString("about_me"));
                user.setName(resultSet.getString("name"));
                user.setAvatar(resultSet.getString("avatar"));
            }
        } catch (SQLException | PoolException e) {
            LOGGER.log(Level.ERROR, USER_NOT_FOUND, e);
            throw new DaoException(USER_NOT_FOUND, e);
        }
        return user;
    }

    @Override
    public User updateInfo(User user) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_INFO_USER);
        ) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getGender());
            statement.setString(3, user.getCountry());
            statement.setString(4, user.getAboutMe());
            statement.setString(5, user.getEmail());
            statement.executeUpdate();


        } catch (SQLException | PoolException e) {
            LOGGER.log(Level.ERROR, "Info not updated", e);
            throw new DaoException("Info not updated", e);
        }
        return user;
    }

    @Override
    public User updateAvatar(User user) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_AVATAR_USER);
        ) {
            statement.setString(1, user.getAvatar());
            statement.setString(2, user.getEmail());
            statement.executeUpdate();

        } catch (SQLException | PoolException e) {
            LOGGER.log(Level.ERROR, "Info not updated", e);
            throw new DaoException("Info not updated", e);
        }
        return user;
    }

    @Override
    public int getNumberOfRows() throws DaoException {
        int numOfRows = 0;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_COUNT_OF_ROWS);
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                numOfRows = resultSet.getInt(1);
            }
        } catch (SQLException | PoolException e) {
            LOGGER.log(Level.ERROR, USER_NOT_FOUND, e);
            throw new DaoException(USER_NOT_FOUND, e);
        }
        return numOfRows;
    }

    @Override
    public User lockUser(User user) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_LOCK_USER);
        ) {
            statement.setString(1, user.getLocked());
            statement.setString(2, user.getEmail());
            statement.executeUpdate();
        } catch (SQLException | PoolException e) {
            LOGGER.log(Level.ERROR, "Info not updated", e);
            throw new DaoException("Info not updated", e);
        }
        return user;
    }
}

