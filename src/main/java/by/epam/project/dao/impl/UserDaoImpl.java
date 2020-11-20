package by.epam.project.dao.impl;


import by.epam.project.dao.ColumnName;
import by.epam.project.dao.UserDao;
import by.epam.project.pool.ConnectionPool;
import by.epam.project.dao.exception.DaoException;
import by.epam.project.entity.impl.User;
import by.epam.project.pool.exception.PoolException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The class contains query`s processing to the database for the user
 */
public class UserDaoImpl implements UserDao {
    private static UserDaoImpl instance;
    private static final String SQL_SELECT_USERS_ON_PAGE =
            "SELECT id_user,role, email,name_user, gender, country, locked FROM testlogin.users " +
                    "WHERE role = ? ORDER BY id_user DESC LIMIT ?,?";
    private static final String SQL_SELECT_BY_LOGIN =
            "SELECT email, password FROM testlogin.users WHERE email = ?";
    private static final String SQL_SELECT_USER_ID_BY_LOGIN =
            "SELECT id_user FROM testlogin.users WHERE email = ?";
    private static final String SQL_SELECT_BY_ID =
            "SELECT id_user, email, locked, role FROM testlogin.users WHERE id = ?";
    private static final String INSERT_NEW_USER =
            "INSERT INTO testlogin.users(email,password) VALUES(?,?)";
    private static final String SQL_SELECT_THE_ALL_INFO_BY_LOGIN =
            "SELECT id_user, email, locked, role, name_user, gender, country,avatar, about_me " +
                    "FROM testlogin.users WHERE email = ?";
    private static final String SQL_UPDATE_INFO_USER =
            "UPDATE testlogin.users SET name_user = ?, gender = ?, country = ?, about_me = ? " +
                    "WHERE email = ?";
    private static final String SQL_UPDATE_AVATAR_USER =
            "UPDATE testlogin.users SET avatar = ? WHERE email = ?";
    private static final String SQL_SELECT_COUNT_OF_ROWS =
            "SELECT COUNT(id_user) FROM testlogin.users WHERE role = ?";
    private static final String SQL_LOCK_USER =
            "UPDATE testlogin.users SET locked = ? WHERE email = ?";
    private static final String SQL_MAKE_ADMIN =
            "UPDATE testlogin.users SET role = ? WHERE email = ?";
    private static final String SQL_SELECT_COUNT_USERS =
            "SELECT count(*) FROM testlogin.users WHERE email = ?";
    private static final String SQL_SELECT_ALL_ADMINS =
            "SELECT id_user,role, email,name_user, gender, country, locked FROM testlogin.users " +
                    "WHERE role = ? ORDER BY id_user DESC LIMIT ?,?";
    private static final String SQL_MAKE_USER =
            "UPDATE testlogin.users SET role = ? WHERE email = ?";

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
             ResultSet resultSet = statement.executeQuery()) {
            statement.setLong(1, id);
            while (resultSet.next()) {
                String email = resultSet.getString(ColumnName.EMAIL);
                long userId = resultSet.getLong(ColumnName.ID_USER);
                String role = resultSet.getString(ColumnName.ROLE_USER);
                user = new User(userId, email, role);
            }
        } catch (SQLException | PoolException e) {
            throw new DaoException("User not found", e);
        }
        return user;
    }

    @Override
    public boolean create(User user) throws DaoException {
        throw new UnsupportedOperationException("This operation is not supported");
    }

    @Override
    public boolean create(String email, String password) throws DaoException {
        boolean result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_NEW_USER)) {
            statement.setString(1, email);
            statement.setString(2, password);
            result = statement.executeUpdate() > 0;
        } catch (SQLException | PoolException e) {
            throw new DaoException("Creation failed", e);
        }
        return result;
    }

    @Override
    public void changeRoleToAdmin(User user) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_MAKE_ADMIN)) {
            statement.setString(2, user.getEmail());
            statement.setString(1, User.UserRole.ADMIN.name());
            statement.executeUpdate();
        } catch (SQLException | PoolException e) {
            throw new DaoException("Role not updated", e);
        }
    }

    @Override
    public boolean isUserExist(String email) throws DaoException {
        boolean result = true;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_COUNT_USERS)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int countUsers = resultSet.getInt(1);
                    if (countUsers == 0) {
                        result = false;
                    }
                }
            }
        } catch (SQLException | PoolException e) {
            throw new DaoException("Select operation failed", e);
        }
        return result;
    }

    @Override
    public List<User> findAdminsOnPage(int currentPage, int adminsOnPage) throws DaoException {
        List<User> admins = new ArrayList<>();
        int start = currentPage * adminsOnPage - adminsOnPage;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_ADMINS)) {
            statement.setInt(2, start);
            statement.setInt(3, adminsOnPage);
            statement.setString(1, User.UserRole.ADMIN.name());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setEmail(resultSet.getString(ColumnName.EMAIL));
                    user.setUserId(resultSet.getLong(ColumnName.ID_USER));
                    user.setName(resultSet.getString(ColumnName.NAME_USER));
                    user.setLocked(resultSet.getBoolean(ColumnName.LOCKED));
                    user.setCountry(resultSet.getString(ColumnName.COUNTRY));
                    user.setUserGender(resultSet.getString(ColumnName.GENDER));
                    user.setUserRole(resultSet.getString(ColumnName.ROLE_USER));
                    admins.add(user);
                }
            }
        } catch (SQLException | PoolException e) {
            throw new DaoException("User not found", e);
        }
        return admins;
    }

    @Override
    public User changeRoleToUser(User user) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_MAKE_USER)) {
            statement.setString(2, user.getEmail());
            statement.setString(1, User.UserRole.USER.name());
            statement.executeUpdate();
        } catch (SQLException | PoolException e) {
            throw new DaoException("Role not updated", e);
        }
        return user;
    }

    @Override
    public int calculateNumberOfRowsByAdmin() throws DaoException {
        int numOfRows = 0;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_COUNT_OF_ROWS)) {
            statement.setString(1, User.UserRole.ADMIN.name());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    numOfRows = resultSet.getInt(1);
                }
            }
        } catch (SQLException | PoolException e) {
            throw new DaoException("User not found", e);
        }
        return numOfRows;
    }

    @Override
    public long findUserIdByLogin(String email) throws DaoException {
        long userId = 0;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_ID_BY_LOGIN)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    userId = resultSet.getLong(ColumnName.ID_USER);
                }
            }
        } catch (SQLException | PoolException e) {
            throw new DaoException("User id not found", e);
        }
        return userId;
    }

    @Override
    public User update(User user) throws DaoException {
        throw new UnsupportedOperationException("This operation is not supported");
    }

    @Override
    public boolean findUserByLoginAndPassword(String email, String password) throws DaoException {
        boolean result = false;
        String userEmail = null;
        String userPassword = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_LOGIN)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    userEmail = resultSet.getString(ColumnName.EMAIL);
                    userPassword = resultSet.getString(ColumnName.PASSWORD);
                }
                if (userEmail != null && userPassword != null) {
                    if (userEmail.equals(email) && userPassword.equals(password)) {
                        result = true;
                    }
                }
            }
        } catch (SQLException | PoolException e) {
            throw new DaoException("User not found", e);
        }
        return result;
    }


    @Override
    public List<User> findUsersOnPage(int currentPage, int usersOnPage) throws DaoException {
        List<User> undeletedUsers = new ArrayList<>();
        int start = currentPage * usersOnPage - usersOnPage;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USERS_ON_PAGE)) {
            statement.setInt(2, start);
            statement.setInt(3, usersOnPage);
            statement.setString(1, User.UserRole.USER.name());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setEmail(resultSet.getString(ColumnName.EMAIL));
                    user.setUserId(resultSet.getLong(ColumnName.ID_USER));
                    user.setName(resultSet.getString(ColumnName.NAME_USER));
                    user.setLocked(resultSet.getBoolean(ColumnName.LOCKED));
                    user.setCountry(resultSet.getString(ColumnName.COUNTRY));
                    user.setUserGender(resultSet.getString(ColumnName.GENDER));
                    user.setUserRole(resultSet.getString(ColumnName.ROLE_USER));
                    undeletedUsers.add(user);
                }
            }
        } catch (SQLException | PoolException e) {
            throw new DaoException("User not found", e);
        }
        return undeletedUsers;
    }

    @Override
    public User findUserWithTheAllInfoByLogin(String email) throws DaoException {
        User user = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_THE_ALL_INFO_BY_LOGIN)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    user = new User();
                    user.setUserId(resultSet.getLong(ColumnName.ID_USER));
                    user.setEmail(resultSet.getString(ColumnName.EMAIL));
                    user.setLocked(resultSet.getBoolean(ColumnName.LOCKED));
                    user.setUserRole(resultSet.getString(ColumnName.ROLE_USER));
                    user.setAboutMe(resultSet.getString(ColumnName.ABOUT_ME));
                    user.setName(resultSet.getString(ColumnName.NAME_USER));
                    user.setAvatar(resultSet.getString(ColumnName.AVATAR));
                    user.setCountry(resultSet.getString(ColumnName.COUNTRY));
                    user.setUserGender(resultSet.getString(ColumnName.GENDER));
                }
            }
        } catch (SQLException | PoolException e) {
            throw new DaoException("User not found", e);
        }
        return user;
    }

    @Override
    public User updateInfo(User user) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_INFO_USER)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getUserGender());
            statement.setString(3, user.getCountry());
            statement.setString(4, user.getAboutMe());
            statement.setString(5, user.getEmail());
            statement.executeUpdate();
        } catch (SQLException | PoolException e) {
            throw new DaoException("Info has not been updated", e);
        }
        return user;
    }

    @Override
    public User updateAvatar(User user) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_AVATAR_USER)) {
            statement.setString(1, user.getAvatar());
            statement.setString(2, user.getEmail());
            statement.executeUpdate();
        } catch (SQLException | PoolException e) {
            throw new DaoException("Avatar has not been updated", e);
        }
        return user;
    }

    @Override
    public int calculateNumberOfRowsByUser() throws DaoException {
        int numOfRows = 0;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_COUNT_OF_ROWS)) {
            statement.setString(1, User.UserRole.USER.name());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    numOfRows = resultSet.getInt(1);
                }
            }
        } catch (SQLException | PoolException e) {
            throw new DaoException("User not found", e);
        }
        return numOfRows;
    }

    @Override
    public void lockUser(User user) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_LOCK_USER)) {
            statement.setBoolean(1, user.isLocked());
            statement.setString(2, user.getEmail());
            statement.executeUpdate();
        } catch (SQLException | PoolException e) {
            throw new DaoException("Info not updated", e);
        }
    }
}

