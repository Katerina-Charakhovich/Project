package by.epam.project.service.impl;

import by.epam.project.dao.exception.DaoException;
import by.epam.project.dao.UserDao;
import by.epam.project.dao.impl.UserDaoImpl;
import by.epam.project.entity.impl.User;
import by.epam.project.service.UserService;
import by.epam.project.service.exception.ServiceException;
import org.powermock.reflect.Whitebox;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.testng.Assert.*;

public class UserServiceImplTest {
    private UserDao userDao;
    private UserService userService;

    @BeforeTest
    public void init() {
        userDao = mock(UserDaoImpl.class);
        Whitebox.setInternalState(UserDaoImpl.class, "instance", userDao);
        userService = UserServiceImpl.getInstance();
    }

    @Test
    public void findUserWithTheAllInfoByLoginPositiveTest() throws ServiceException, DaoException {
        User expected = new User(25, "eertsss", "ADMIN");
        when(userDao.findUserWithTheAllInfoByLogin("eertsss")).thenReturn(expected);
        User actual = userService.findUserWithTheAllInfoByLogin("eertsss");
        assertEquals(actual, expected);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void findUserWithTheAllInfoByLoginThrowServiceExceptionTest() throws ServiceException, DaoException {
        User expected = new User(12, "eert", "ADMIN");
        when(userDao.findUserWithTheAllInfoByLogin("eert")).thenReturn(expected);
        User actual = userService.findUserWithTheAllInfoByLogin("eet");
        assertNotEquals(actual, expected);
    }

    @Test
    public void findUsersOnPagePositiveTest() throws ServiceException, DaoException {
        List<User> users = new ArrayList<>();
        users.add(new User(12, "eert", "ADMIN"));
        users.add(new User(14, "login", "ADMIN"));
        when(userDao.findUsersOnPage(1, 4)).thenReturn(users);
        List<User> actual = userService.findUsersOnPage(1, 4);
        assertEquals(actual, users);
    }

    @Test
    public void findUsersOnPageNegativeTest() throws ServiceException, DaoException {
        List<User> users = new ArrayList<>();
        users.add(new User(12, "eert", "ADMIN"));
        users.add(new User(14, "login", "ADMIN"));
        when(userDao.findUsersOnPage(1, 4)).thenReturn(users);
        List<User> actual = userService.findUsersOnPage(5, 8);
        assertNotEquals(actual, users);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void findUsersOnPageThrowServiceExceptionTest() throws ServiceException, DaoException {
        when(userDao.findUsersOnPage(1, 4)).thenThrow(DaoException.class);
        userService.findUsersOnPage(1, 4);
    }

    @Test
    public void calculateNumberOfRowsByUserPositiveTest() throws ServiceException, DaoException {
        int expected = 1;
        when(userDao.calculateNumberOfRowsByUser()).thenReturn(expected);
        int actual = userService.calculateNumberOfRowsByUser();
        assertEquals(actual, expected);
    }

    @Test
    public void calculateNumberOfRowsByUserNegativeTest() throws ServiceException, DaoException {
        int expected = 1;
        when(userDao.calculateNumberOfRowsByUser()).thenReturn(expected);
        int actual = userService.calculateNumberOfRowsByUser();
        assertEquals(actual, expected);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void calculateNumberOfRowsByUserThrowServiceExceptionTest() throws ServiceException, DaoException {
        when(userDao.calculateNumberOfRowsByUser()).thenThrow(DaoException.class);
        userService.calculateNumberOfRowsByUser();
    }
}