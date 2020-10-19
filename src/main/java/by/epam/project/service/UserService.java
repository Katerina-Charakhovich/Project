package by.epam.project.service;
import by.epam.project.dao.impl.UserDaoImpl;
import by.epam.project.entity.impl.User;
import by.epam.project.dao.exception.DaoException;
import by.epam.project.service.exception.ServiceException;
import by.epam.project.service.util.ServiceUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class UserService {
    private static UserService instance;
    private final UserDaoImpl userDao = UserDaoImpl.getInstance();
    public static final Logger LOGGER = LogManager.getLogger();
    private static final String USER_NOT_FOUND = "User not found";

    private UserService() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public boolean isLoginAndPasswordValid(String enteredLogin, String enteredPassword) throws
            ServiceException {
        String hashPassword = ServiceUtil.hash(enteredPassword);
        User user;
        try {
            user = userDao.findUserByLogin(enteredLogin);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, USER_NOT_FOUND, e);
            throw new ServiceException(USER_NOT_FOUND, e);
        }
        return user != null && user.getEmail().equals(enteredLogin) &&
                user.getPassword().equals(hashPassword);
    }

    public Boolean create(String enteredLogin, String enteredPassword) throws ServiceException {
        if (isLoginExists(enteredLogin)) {
            return false;
        }
        boolean result;
        String hashPassword = ServiceUtil.hash(enteredPassword);
        try {
            result = userDao.create(new User(enteredLogin, hashPassword));
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, USER_NOT_FOUND, e);
            throw new ServiceException(USER_NOT_FOUND, e);
        }
        return result;
    }

    public Boolean isLoginExists(String enteredLogin) throws ServiceException {
        User user;
        try {
            user = userDao.findUserByLogin(enteredLogin);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, USER_NOT_FOUND, e);
            throw new ServiceException(USER_NOT_FOUND, e);
        }
        return user != null && user.getEmail().equals(enteredLogin);
    }

    public String findUserRole(String enteredLogin) throws ServiceException {
        User user;
        String role = null;
        try {
            user = userDao.findUserByLogin(enteredLogin);
            if (user != null) {
                role= user.getRole();
            }
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, USER_NOT_FOUND, e);
            throw new ServiceException(USER_NOT_FOUND, e);
        }
        return role;
    }

    public User findUserWithTheAllInfoByLogin(String email) throws ServiceException {
        try {
            return  userDao.findUserWithTheAllInfoByLogin(email);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, USER_NOT_FOUND, e);
            throw new ServiceException(USER_NOT_FOUND, e);
        }
    }

    public User updateInfo(String email, String name,
                           String aboutMe, String country, String gender) throws ServiceException {
        User user = null;
        if (isLoginExists(email)) {
            user = findUserWithTheAllInfoByLogin(email);
            user.setName(name);
            user.setAboutMe(aboutMe);
            user.setCountry(country);
            user.setGender(gender);
            try {
                userDao.updateInfo(user);
            } catch (DaoException e) {
                LOGGER.log(Level.ERROR, USER_NOT_FOUND, e);
                throw new ServiceException(USER_NOT_FOUND, e);
            }
        }
        return user;
    }
    public User updateAvatar(String email, String avatar) throws ServiceException {
        User user = new User();
        if (isLoginExists(email)) {
            user = findUserWithTheAllInfoByLogin(email);
            user.setAvatar(avatar);
            try {
                userDao.updateAvatar(user);
            } catch (DaoException e) {
                LOGGER.log(Level.ERROR, USER_NOT_FOUND, e);
                throw new ServiceException(USER_NOT_FOUND, e);
            }
        }
        return user;
    }
    public List<User> findAllUndeletedUsers(int currentPage, int usersOnPage) throws ServiceException {
        List<User>users;
        try {
            users = userDao.findAllUndeletedUsers(currentPage,usersOnPage);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR,USER_NOT_FOUND);
            throw new ServiceException(USER_NOT_FOUND,e);
        }
        return users;
    }
    public int getNumberOfRows() throws ServiceException {
        try {
            return userDao.getNumberOfRows();
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR,USER_NOT_FOUND,e);
            throw new ServiceException(USER_NOT_FOUND,e);
        }
    }
    public void lockUser(String email) throws ServiceException {
        User user;
        if (isLoginExists(email)) {
            user = findUserWithTheAllInfoByLogin(email);
            if (user.getLocked().equals("no")){
                user.setLocked("yes");
            }else {
                user.setLocked("no");
            }
            try {
                userDao.lockUser(user);
            } catch (DaoException e) {
                LOGGER.log(Level.ERROR, USER_NOT_FOUND, e);
                throw new ServiceException(USER_NOT_FOUND, e);
            }
        }
    }
    public boolean isUserLocked(String name) throws ServiceException {
        boolean result = false;
        User user = findUserWithTheAllInfoByLogin(name);
        if (user.getLocked().equals("yes")){
            result = true;
        }
        return result;
    }
}



