package by.epam.project.service.impl;

import by.epam.project.dao.UserDao;
import by.epam.project.dao.impl.UserDaoImpl;
import by.epam.project.entity.impl.User;
import by.epam.project.dao.exception.DaoException;
import by.epam.project.service.UserService;
import by.epam.project.service.util.ServiceUtil;

public class UserServiceImpl implements UserService {
    @Override
    public boolean checkLoginAndPass(String enteredLogin, String enteredPass)
            throws DaoException {
        UserDao userDao = new UserDaoImpl();
        String hashPassword = ServiceUtil.hash(enteredPass);
        User user = userDao.findUserByLogin(enteredLogin);
        return user.getLogin().equals(enteredLogin) &&
                user.getPassword().equals(hashPassword);
    }
}
