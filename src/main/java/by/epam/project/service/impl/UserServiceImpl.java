package by.epam.project.service.impl;

import by.epam.project.dao.impl.UserDaoImpl;
import by.epam.project.entity.impl.Film;
import by.epam.project.entity.impl.User;
import by.epam.project.dao.exception.DaoException;
import by.epam.project.service.UserService;
import by.epam.project.service.exception.ServiceException;
import by.epam.project.util.HashPassword;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class UserServiceImpl implements UserService {
    public static final Logger LOGGER = LogManager.getLogger();
    private static final UserServiceImpl instance = new UserServiceImpl();
    private UserDaoImpl userDao = UserDaoImpl.getInstance();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    public boolean isLoginAndPasswordValid(String enteredLogin, String enteredPassword) throws
            ServiceException {
        String hashPassword = HashPassword.hash(enteredPassword);
        boolean result = false;
        try {
            if (userDao.findUserByLoginAndPassword(enteredLogin, hashPassword)) {
                result = true;
            }
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "User not found", e);
            throw new ServiceException("User not found", e);
        }
        return result;
    }

    public boolean create(String enteredLogin, String enteredPassword) throws ServiceException {
        boolean result =    false;
        if (!isLoginExists(enteredLogin)) {
            String hashPassword = HashPassword.hash(enteredPassword);
            try {
                result = userDao.create(enteredLogin, hashPassword);
            } catch (DaoException e) {
                LOGGER.log(Level.ERROR, "Creation failed", e);
                throw new ServiceException("Creation failed", e);
            }
        }
        return result;
    }


    public boolean isLoginExists(String enteredLogin) throws ServiceException {
        boolean result;
        try {
            result = userDao.isUserExist(enteredLogin);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "User not found", e);
            throw new ServiceException("User not found", e);
        }
        return result;
    }

    public User findUserWithTheAllInfoByLogin(String email) throws ServiceException {
        try {
            return userDao.findUserWithTheAllInfoByLogin(email);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "User not found", e);
            throw new ServiceException("User not found", e);
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
            user.setUserGender(gender);
            try {
                userDao.updateInfo(user);
            } catch (DaoException e) {
                LOGGER.log(Level.ERROR, "Info has not been updated", e);
                throw new ServiceException("Info has not been updated", e);
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
                LOGGER.log(Level.ERROR, "Avatar has not been updated", e);
                throw new ServiceException("Avatar has not been updated", e);
            }
        }
        return user;
    }

    public List<User> findUsersOnPage(int currentPage, int usersOnPage) throws ServiceException {
        List<User> users;
        try {
            users = userDao.findUsersOnPage(currentPage, usersOnPage);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "User not found",e);
            throw new ServiceException("User not found", e);
        }
        return users;
    }

    public int calculateNumberOfRowsByUser() throws ServiceException {
        try {
            return userDao.calculateNumberOfRowsByUser();
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "User not found", e);
            throw new ServiceException("User not found", e);
        }
    }

    @Override
    public int calculateNumberOfRowsByAdmin() throws ServiceException {
        try {
            return userDao.calculateNumberOfRowsByAdmin();
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "User not found", e);
            throw new ServiceException("User not found", e);
        }
    }

    public User lockUser(User user) throws ServiceException {
        if (isLoginExists(user.getEmail())) {
            if (!user.isLocked()) {
                user.setLocked(true);
            } else {
                user.setLocked(false);
            }
            try {
               userDao.lockUser(user);
            } catch (DaoException e) {
                LOGGER.log(Level.ERROR, "User not found", e);
                throw new ServiceException("User not found", e);
            }
        }
        return user;
    }

    public boolean isUserLocked(String name) throws ServiceException {
        User user = findUserWithTheAllInfoByLogin(name);
        return user.isLocked();
    }

    @Override
    public User changeRoleToAdmin(User user) throws ServiceException {
        if (isLoginExists(user.getEmail())) {
            try {
                if (User.UserRole.USER == user.getUserRole()) {
                   userDao.changeRoleToAdmin(user);
                   user.setUserRole(User.UserRole.ADMIN.name());
                }
            } catch (DaoException e) {
                LOGGER.log(Level.ERROR, "Impossible to appoint as admin",e);
                throw new ServiceException("Impossible to appoint as admin", e);
            }
        }
        return user;
    }

    @Override
    public List<User> findAllAdmins(int currentPage, int adminsOnPage) throws ServiceException {
        List<User> admins;
        try {
            admins = userDao.findAdminsOnPage(currentPage, adminsOnPage);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "Admin not found",e);
            throw new ServiceException("Admin not found", e);
        }
        return admins;
    }

    @Override
    public User changeRoleToUser(String email) throws ServiceException {
        User user = null;
        if (isLoginExists(email)) {
            user = findUserWithTheAllInfoByLogin(email);
            try {
                if (User.UserRole.ADMIN == user.getUserRole()) {
                    userDao.changeRoleToUser(user);
                }
            } catch (DaoException e) {
                LOGGER.log(Level.ERROR, "Impossible to appoint as admin",e);
                throw new ServiceException("Impossible to appoint as admin", e);
            }
        }
        return user;
    }

    @Override
    public long findUserIdByLogin(String email) throws ServiceException {
        try {
            return userDao.findUserIdByLogin(email);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "UserId not found",e);
            throw new ServiceException("UserId not found", e);
        }
    }
}



