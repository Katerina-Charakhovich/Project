package by.epam.project.service.impl;

import by.epam.project.dao.exception.DaoException;
import by.epam.project.dao.UserDao;
import by.epam.project.dao.impl.PurchasedDaoImpl;;
import by.epam.project.dao.impl.UserDaoImpl;
import by.epam.project.entity.impl.Film;
import by.epam.project.entity.impl.FilmInfo;
import by.epam.project.entity.impl.PurchasedFilm;
import by.epam.project.entity.impl.User;
import by.epam.project.service.PurchasedFilmService;
import by.epam.project.service.UserService;
import by.epam.project.service.exception.ServiceException;
import org.powermock.reflect.Whitebox;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;


public class PurchasedFilmServiceImplTest {
    private PurchasedDaoImpl purchasedDao;
    private PurchasedFilmService purchasedFilmService;
    private UserDao userDao;
    private UserService userService;

    @BeforeTest
    public void init() {
        purchasedDao = mock(PurchasedDaoImpl.class);
        Whitebox.setInternalState(PurchasedDaoImpl.class, "instance", purchasedDao);
        userDao = mock(UserDaoImpl.class);
        Whitebox.setInternalState(UserDaoImpl.class, "instance", userDao);
        purchasedFilmService = PurchasedFilmServiceImpl.getInstance();
        userService = UserServiceImpl.getInstance();
    }

    @Test
    public void findPurchasedFilmByIdPositiveTest() throws ServiceException, DaoException {
        PurchasedFilm expected = new PurchasedFilm(12, 15);
        when(purchasedDao.findEntityById(12)).thenReturn(expected);
        PurchasedFilm actual = purchasedFilmService.findPurchasedFilmById(12);
        assertEquals(actual, expected);
    }

    @Test
    public void findPurchasedFilmByIdNegativeTest() throws ServiceException, DaoException {
        PurchasedFilm expected = new PurchasedFilm(12, 15);
        when(purchasedDao.findEntityById(11)).thenReturn(expected);
        PurchasedFilm actual = purchasedFilmService.findPurchasedFilmById(11);
        assertEquals(actual, expected);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void findUserWithTheAllInfoByLoginThrowServiceExceptionTest() throws ServiceException, DaoException {
        when(purchasedDao.findEntityById(11)).thenThrow(DaoException.class);
        purchasedFilmService.findPurchasedFilmById(11);
    }

    @Test
    public void calculateNumberOfRowsByPurchasedFilmsPositiveTest() throws ServiceException, DaoException {
        int expected = 1;
        when(purchasedDao.calculateNumberOfRowsByPurchasedFilms()).thenReturn(expected);
        int actual = purchasedFilmService.calculateNumberOfRowsByPurchasedFilms();
        assertEquals(actual, expected);
    }

    @Test
    public void calculateNumberOfRowsByPurchasedFilmsNegativeTest() throws ServiceException, DaoException {
        int expected = 2;
        when(purchasedDao.calculateNumberOfRowsByPurchasedFilms()).thenReturn(expected);
        int actual = purchasedFilmService.calculateNumberOfRowsByPurchasedFilms();
        assertEquals(actual, expected);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void calculateNumberOfRowsByPurchasedFilmsThrowServiceExceptionTest() throws ServiceException, DaoException {
        when(purchasedDao.calculateNumberOfRowsByPurchasedFilms()).thenThrow(DaoException.class);
        purchasedFilmService.calculateNumberOfRowsByPurchasedFilms();
    }

    @Test
    public void findAllInfoAboutPurchasedFilmsPositiveTest() throws ServiceException, DaoException {
        Map<User, Film> expected = new HashMap<>();
        expected.put(new User(12, "eert", "ADMIN"), new Film(15, "Venom",
                "venom.jpg", new FilmInfo("description",
                "yearOfCreation", "genre", "link")));
        when(purchasedDao.findAllInfoAboutUsersAndPurchasedFilms(1, 2, "en"))
                .thenReturn(expected);
        Map<User, Film> actual = purchasedFilmService.findAllInfoAboutPurchasedFilms(1,
                2, "en");
        assertEquals(actual, expected);
    }

    @Test
    public void findAllInfoAboutPurchasedFilmsNegativeTest() throws ServiceException, DaoException {
        Map<User, Film> expected = new HashMap<>();
        expected.put(new User(12, "eert", "ADMIN"), new Film(15, "Venom",
                "venom.jpg", new FilmInfo("description",
                "yearOfCreation", "genre", "link")));
        when(purchasedDao.findAllInfoAboutUsersAndPurchasedFilms(1, 2, "en"))
                .thenReturn(expected);
        Map<User, Film> actual = purchasedFilmService.findAllInfoAboutPurchasedFilms(1,
                2, "ru");
        assertNotEquals(actual, expected);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void findAllInfoAboutPurchasedFilmsThrowServiceExceptionTest() throws ServiceException, DaoException {
        when(purchasedDao.findAllInfoAboutUsersAndPurchasedFilms(1, 2, "en")).thenThrow(DaoException.class);
        purchasedFilmService.findAllInfoAboutPurchasedFilms(1, 2, "en");
    }
}