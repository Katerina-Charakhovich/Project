package by.epam.project.service;

import by.epam.project.entity.impl.User;
import by.epam.project.service.exception.ServiceException;

import java.util.List;

public interface UserService {
    boolean isLoginAndPasswordValid(String enteredLogin, String enteredPassword) throws
            ServiceException;

    boolean create(String enteredLogin, String enteredPassword) throws ServiceException;

    boolean isLoginExists(String enteredLogin) throws ServiceException;

    User findUserWithTheAllInfoByLogin(String email) throws ServiceException;

    User updateInfo(String email, String name,
                    String aboutMe, String country, String gender) throws ServiceException;

    User updateAvatar(String email, String avatar) throws ServiceException;

    List<User> findUsersOnPage(int currentPage, int usersOnPage) throws ServiceException;

    int calculateNumberOfRowsByUser() throws ServiceException;

    int calculateNumberOfRowsByAdmin() throws ServiceException;

    User lockUser(User user) throws ServiceException;

    boolean isUserLocked(String name) throws ServiceException;

    public User changeRoleToAdmin(User user) throws ServiceException;

    List<User> findAllAdmins(int currentPage, int adminsOnPage) throws ServiceException;

    public User changeRoleToUser(String email) throws ServiceException;
}
