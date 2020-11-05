package by.epam.project.service;

import by.epam.project.entity.impl.User;
import by.epam.project.service.exception.ServiceException;

import java.util.List;

/**
 * The interface User service.
 */
public interface UserService {
    /**
     * Is login and password valid boolean.
     *
     * @param enteredLogin    the entered login
     * @param enteredPassword the entered password
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean isLoginAndPasswordValid(String enteredLogin, String enteredPassword) throws
            ServiceException;

    /**
     * Create boolean.
     *
     * @param enteredLogin    the entered login
     * @param enteredPassword the entered password
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean create(String enteredLogin, String enteredPassword) throws ServiceException;

    /**
     * Is login exists boolean.
     *
     * @param enteredLogin the entered login
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean isLoginExists(String enteredLogin) throws ServiceException;

    /**
     * Find user with the all info by login user.
     *
     * @param email the email
     * @return the user
     * @throws ServiceException the service exception
     */
    User findUserWithTheAllInfoByLogin(String email) throws ServiceException;

    /**
     * Update info user.
     *
     * @param email   the email
     * @param name    the name
     * @param aboutMe the about me
     * @param country the country
     * @param gender  the gender
     * @return the user
     * @throws ServiceException the service exception
     */
    User updateInfo(String email, String name,
                    String aboutMe, String country, String gender) throws ServiceException;

    /**
     * Update avatar user.
     *
     * @param email  the email
     * @param avatar the avatar
     * @return the user
     * @throws ServiceException the service exception
     */
    User updateAvatar(String email, String avatar) throws ServiceException;

    /**
     * Find users on page list.
     *
     * @param currentPage the current page
     * @param usersOnPage the users on page
     * @return the list
     * @throws ServiceException the service exception
     */
    List<User> findUsersOnPage(int currentPage, int usersOnPage) throws ServiceException;

    /**
     * Calculate number of rows by user int.
     *
     * @return the int
     * @throws ServiceException the service exception
     */
    int calculateNumberOfRowsByUser() throws ServiceException;

    /**
     * Calculate number of rows by admin int.
     *
     * @return the int
     * @throws ServiceException the service exception
     */
    int calculateNumberOfRowsByAdmin() throws ServiceException;

    /**
     * Lock user user.
     *
     * @param user the user
     * @return the user
     * @throws ServiceException the service exception
     */
    User lockUser(User user) throws ServiceException;

    /**
     * Is user locked boolean.
     *
     * @param name the name
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean isUserLocked(String name) throws ServiceException;

    /**
     * Change role to admin user.
     *
     * @param user the user
     * @return the user
     * @throws ServiceException the service exception
     */
    User changeRoleToAdmin(User user) throws ServiceException;

    /**
     * Find all admins list.
     *
     * @param currentPage  the current page
     * @param adminsOnPage the admins on page
     * @return the list
     * @throws ServiceException the service exception
     */
    List<User> findAllAdmins(int currentPage, int adminsOnPage) throws ServiceException;

    /**
     * Change role to user user.
     *
     * @param email the email
     * @return the user
     * @throws ServiceException the service exception
     */
    User changeRoleToUser(String email) throws ServiceException;

    /**
     * Find user id by login long.
     *
     * @param email the email
     * @return the long
     * @throws ServiceException the service exception
     */
    long findUserIdByLogin(String email) throws ServiceException;
}
