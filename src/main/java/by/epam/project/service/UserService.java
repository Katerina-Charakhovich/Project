package by.epam.project.service;
import by.epam.project.dao.impl.UserDaoImpl;
import by.epam.project.entity.impl.User;
import by.epam.project.dao.exception.DaoException;
import by.epam.project.service.exception.ServiceException;
import by.epam.project.service.util.ServiceUtil;

public class UserService {
    private static UserService instance;
    private final UserDaoImpl userDao = UserDaoImpl.getInstance();

    private UserService() {
    }
    public static UserService getInstance(){
        if (instance == null){
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
            throw new ServiceException();
        }
        return user != null && user.getEmail().equals(enteredLogin) &&
                user.getPassword().equals(hashPassword);
    }
    public Boolean create(String enteredLogin, String enteredPassword) throws ServiceException{
        if (isLoginExists(enteredLogin)){
            return false;
        }
        boolean result;
        String hashPassword = ServiceUtil.hash(enteredPassword);
        try {
           result = userDao.create(new User(0,enteredLogin,hashPassword));
        } catch (DaoException e) {
            throw  new ServiceException();
        }
        return result;
    }
    public Boolean isLoginExists(String enteredLogin) throws ServiceException {
        User user;
        try {
            user = userDao.findUserByLogin(enteredLogin);
        } catch (DaoException e) {
            throw new ServiceException();
        }
        return user != null && user.getEmail().equals(enteredLogin);
    }

}
