package by.epam.project.dao;

import by.epam.project.entity.impl.User;

import java.util.List;

/**
 * The interface User dao.
 */
public interface UserDao extends BaseDao<User> {
    /**
     * Find user by login and password boolean.
     *
     * @param login    the login
     * @param password the password
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean findUserByLoginAndPassword(String login, String password) throws DaoException;

    /**
     * Find users on page list.
     *
     * @param currentPage the current page
     * @param filmsOnPage the films on page
     * @return the list
     * @throws DaoException the dao exception
     */
    List<User> findUsersOnPage(int currentPage, int filmsOnPage) throws DaoException;

    /**
     * Find user with the all info by login user.
     *
     * @param login the login
     * @return the user
     * @throws DaoException the dao exception
     */
    User findUserWithTheAllInfoByLogin(String login) throws DaoException;

    /**
     * Update info user.
     *
     * @param user the user
     * @return the user
     * @throws DaoException the dao exception
     */
    User updateInfo(User user) throws DaoException;

    /**
     * Update avatar user.
     *
     * @param user the user
     * @return the user
     * @throws DaoException the dao exception
     */
    User updateAvatar(User user) throws DaoException;

    /**
     * Calculate number of rows by user int.
     *
     * @return the int
     * @throws DaoException the dao exception
     */
    int calculateNumberOfRowsByUser() throws DaoException;

    /**
     * Lock user.
     *
     * @param user the user
     * @throws DaoException the dao exception
     */
    void lockUser(User user) throws DaoException;

    /**
     * Create boolean.
     *
     * @param email    the email
     * @param password the password
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean create(String email, String password) throws DaoException;

    /**
     * Change role to admin.
     *
     * @param user the user
     * @throws DaoException the dao exception
     */
    void changeRoleToAdmin(User user) throws DaoException;

    /**
     * Is user exist boolean.
     *
     * @param email the email
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean isUserExist(String email) throws DaoException;

    /**
     * Find admins on page list.
     *
     * @param currentPage  the current page
     * @param adminsOnPage the admins on page
     * @return the list
     * @throws DaoException the dao exception
     */
    List<User> findAdminsOnPage(int currentPage, int adminsOnPage) throws DaoException;

    /**
     * Change role to user user.
     *
     * @param user the user
     * @return the user
     * @throws DaoException the dao exception
     */
    User changeRoleToUser(User user) throws DaoException;

    /**
     * Calculate number of rows by admin int.
     *
     * @return the int
     * @throws DaoException the dao exception
     */
    int calculateNumberOfRowsByAdmin() throws DaoException;

    /**
     * Find user id by login long.
     *
     * @param email the email
     * @return the long
     * @throws DaoException the dao exception
     */
    long findUserIdByLogin(String email) throws DaoException;

}

