package by.epam.project.dao;

import by.epam.project.dao.exception.DaoException;
import by.epam.project.entity.impl.User;

import java.util.List;

public interface UserDao extends BaseDao<User> {
    boolean findUserByLoginAndPassword(String login, String password) throws DaoException;

    List<User> findUsersOnPage(int currentPage, int filmsOnPage) throws DaoException;

    User findUserWithTheAllInfoByLogin(String login) throws DaoException;

    User updateInfo(User user) throws DaoException;

    User updateAvatar(User user) throws DaoException;

    int calculateNumberOfRowsByUser() throws DaoException;

    void lockUser(User user) throws DaoException;

    boolean create(String email, String password) throws DaoException;

    void changeRoleToAdmin(User user) throws DaoException;

    boolean isUserExist(String email) throws DaoException;

    List<User> findAdminsOnPage(int currentPage, int adminsOnPage) throws DaoException;

    User changeRoleToUser(User user) throws DaoException;
    int calculateNumberOfRowsByAdmin() throws DaoException;
}

