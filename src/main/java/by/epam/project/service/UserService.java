package by.epam.project.service;

import by.epam.project.entity.impl.User;
import by.epam.project.service.exception.ServiceException;

import java.util.List;

public interface UserService {
    Boolean isLoginAndPasswordValid(String enteredLogin, String enteredPassword) throws
            ServiceException;

    Boolean create(String enteredLogin, String enteredPassword) throws ServiceException;

    Boolean isLoginExists(String enteredLogin) throws ServiceException;

    String findUserRole(String enteredLogin) throws ServiceException;

    User findUserWithTheAllInfoByLogin(String email) throws ServiceException;

    User updateInfo(String email, String name,
                    String aboutMe, String country, String gender) throws ServiceException;

    User updateAvatar(String email, String avatar) throws ServiceException;

    List<User> findAllUndeletedUsers(int currentPage, int usersOnPage) throws ServiceException;

    int getNumberOfRows() throws ServiceException;

    void lockUser(String email) throws ServiceException;

    Boolean isUserLocked(String name) throws ServiceException;
}
