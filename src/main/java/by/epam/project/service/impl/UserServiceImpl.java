package by.epam.project.service.impl;

import by.epam.project.dao.UserDao;
import by.epam.project.dao.impl.UserDaoImpl;
import by.epam.project.entity.impl.User;
import by.epam.project.dao.exception.DaoException;
import by.epam.project.service.UserService;
import by.epam.project.service.exception.ServiceException;
import by.epam.project.util.HashPassword;

import java.util.List;


public class UserServiceImpl implements UserService {
    private static final UserServiceImpl instance = new UserServiceImpl();
    private UserDao userDao = UserDaoImpl.getInstance();

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
            throw new ServiceException("User not found", e);
        }
        return result;
    }

    public boolean create(String enteredLogin, String enteredPassword) throws ServiceException {
        boolean result = false;
        if (!isLoginExistsForCreationUser(enteredLogin)) {
            String hashPassword = HashPassword.hash(enteredPassword);
            try {
                result = userDao.create(enteredLogin, hashPassword);
            } catch (DaoException e) {
                throw new ServiceException("Creation failed", e);
            }
        }
        return result;
    }

    public boolean isLoginExistsForCreationUser(String enteredLogin) throws ServiceException {
        try {
            return userDao.isUserExist(enteredLogin);

        } catch (DaoException e) {
            throw new ServiceException("Select operation failed. Please contact your system administrator", e);
        }
    }

    public boolean isLoginExists(String enteredLogin) throws ServiceException {
        boolean result;
        try {
            result = userDao.isUserExist(enteredLogin);
            if (!result) {
                throw new ServiceException("User not found. Please contact your system administrator");
            }
        } catch (DaoException e) {
            throw new ServiceException("Select operation failed. Please contact your system administrator", e);
        }
        return result;
    }

    public User findUserWithTheAllInfoByLogin(String email) throws ServiceException {
        try {
            User user = userDao.findUserWithTheAllInfoByLogin(email);
            if (user == null) {
                throw new ServiceException("User not found. Please contact your system administrator");
            }
            return user;
        } catch (DaoException e) {
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
                throw new ServiceException("Avatar has not been updated", e);
            }
        }
        return user;
    }

    public List<User> findUsersOnPage(int currentPage, int usersOnPage) throws ServiceException {
        try {
            return userDao.findUsersOnPage(currentPage, usersOnPage);
        } catch (DaoException e) {
            throw new ServiceException("User not found", e);
        }
    }

    public int calculateNumberOfRowsByUser() throws ServiceException {
        try {
            return userDao.calculateNumberOfRowsByUser();
        } catch (DaoException e) {
            throw new ServiceException("User not found", e);
        }
    }

    @Override
    public int calculateNumberOfRowsByAdmin() throws ServiceException {
        try {
            return userDao.calculateNumberOfRowsByAdmin();
        } catch (DaoException e) {
            throw new ServiceException("User not found", e);
        }
    }

    public User lockUser(User user) throws ServiceException {
        try {
            if (isLoginExists(user.getEmail())) {
                user.setLocked(!user.isLocked());
                userDao.lockUser(user);
            }
        } catch (DaoException e) {
            throw new ServiceException("User not found", e);
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
                throw new ServiceException("Impossible to appoint as admin", e);
            }
        }
        return user;
    }

    @Override
    public List<User> findAllAdmins(int currentPage, int adminsOnPage) throws ServiceException {
        try {
            return userDao.findAdminsOnPage(currentPage, adminsOnPage);
        } catch (DaoException e) {
            throw new ServiceException("Admin not found", e);
        }
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
            throw new ServiceException("UserId not found", e);
        }
    }
}



