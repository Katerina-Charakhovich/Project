package by.epam.project.service.impl;


import by.epam.project.dao.exception.DaoException;
import by.epam.project.dao.MediaDao;
import by.epam.project.dao.impl.MediaDaoImpl;
import by.epam.project.entity.impl.Film;
import by.epam.project.entity.impl.FilmInfo;
import by.epam.project.service.MediaService;
import by.epam.project.service.exception.ServiceException;
import org.powermock.reflect.Whitebox;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.*;
import static org.testng.Assert.*;

public class MediaServiceImplTest {
    private MediaDao mediaDao;
    private MediaService mediaService;

    @BeforeTest
    public void init() {
        mediaDao = mock(MediaDaoImpl.class);
        Whitebox.setInternalState(MediaDaoImpl.class, "instance", mediaDao);
        mediaService = MediaServiceImpl.getInstance();
    }

    @Test
    public void isFilmExistPositiveTest() throws ServiceException, DaoException {
        String name = "Me before you";
        when(mediaDao.isFilmExist(name)).thenReturn(true);
        boolean result = mediaService.isFilmExist(name);
        assertTrue(result);
    }

    @Test
    public void isFilmExistNegativeTest() throws ServiceException, DaoException {
        String name = "Win";
        when(mediaDao.isFilmExist(name)).thenReturn(false);
        boolean result = mediaService.isFilmExist(name);
        assertFalse(result);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void isFilmExistThrowServiceExceptionTest() throws ServiceException, DaoException {
        when(mediaDao.isFilmExist(anyString())).thenThrow(DaoException.class);
        mediaService.isFilmExist("Wrong film");
    }

    @Test
    public void findAllActiveFilmsPositiveTest() throws ServiceException, DaoException {
        List<Film> films = new ArrayList<>();
        films.add(new Film(1, "Me before you", "me.jpg", new FilmInfo("description",
                "2020", "detective", "teew.com")));
        films.add(new Film(1, "Avatar", "me.jpg", new FilmInfo("description1",
                "2019", "detective1", "teew.com1")));

        when(mediaDao.findAllActiveFilms(1, 2, "en", true))
                .thenReturn(films);
        List<Film> filmsActual = mediaService.findAllActiveFilms(1, 2, "en", true);
        assertEquals(filmsActual, films);
    }

    @Test
    public void findAllActiveFilmsNegativeTest() throws ServiceException, DaoException {
        List<Film> films = new ArrayList<>();
        films.add(new Film(1, "Me before you", "me.jpg", new FilmInfo("description",
                "2020", "detective", "teew.com")));
        films.add(new Film(1, "Avatar", "me.jpg", new FilmInfo("description1",
                "2019", "detective1", "teew.com1")));

        when(mediaDao.findAllActiveFilms(1, 2, "en", false))
                .thenReturn(films);
        List<Film> filmsActual = mediaService.findAllActiveFilms(1, 2, "en", true);
        assertNotEquals(filmsActual, films);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void findAllActiveFilmsThrowServiceExceptionTest() throws ServiceException, DaoException {
        when(mediaDao.findAllActiveFilms(1, 2, "ru", true)).thenThrow(DaoException.class);
        mediaService.findAllActiveFilms(1, 2, "ru", true);
    }

    @Test
    public void findFilmByNamePositiveTest() throws ServiceException, DaoException {
        Film expected = new Film(15, "Venom", "venom.jpg", new FilmInfo("description",
                "yearOfCreation", "genre", "link"));
        when(mediaDao.findFilmByName("Venom", "en")).thenReturn(expected);
        Film film = mediaService.findFilmByName("Venom", "en");
        assertEquals(film, expected);
    }

    @Test
    public void findFilmByNameNegativeTest() throws ServiceException, DaoException {
        Film expected = new Film(15, "Venom", "venom.jpg", new FilmInfo("description",
                "yearOfCreation", "genre", "link"));
        when(mediaDao.findFilmByName("Venom", "en")).thenReturn(expected);
        Film film = mediaService.findFilmByName("Venom", "ru");
        assertNotEquals(film, expected);
    }

    @Test
    public void findFilmByIdPositiveTest() throws ServiceException, DaoException {
        Film expected = new Film(15, "Venom", "venom.jpg", new FilmInfo("description",
                "yearOfCreation", "genre", "link"));
        when(mediaDao.findFilmById(15, "en")).thenReturn(expected);
        Film film = mediaService.findFilmById(15, "en");
        assertEquals(film, expected);
    }

    @Test
    public void findFilmByIdNegativeTest() throws ServiceException, DaoException {
        Film expected = new Film(15, "Venom", "venom.jpg", new FilmInfo("description",
                "yearOfCreation", "genre", "link"));
        when(mediaDao.findFilmById(15, "en")).thenReturn(expected);
        Film film = mediaService.findFilmById(12, "en");
        assertNotEquals(film, expected);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void findFilmByIdThrowServiceExceptionTest() throws ServiceException, DaoException {
        when(mediaDao.findFilmById(15, "en")).thenThrow(DaoException.class);
        mediaService.findFilmById(15, "en");
    }

    @Test
    public void findInfoByIdPositiveTest() throws ServiceException, DaoException {
        FilmInfo expected = new FilmInfo("description", "yearOfCreation",
                "genre", 10, "link");
        when(mediaDao.findInfoById(10, "en")).thenReturn(expected);
        FilmInfo filmInfo = mediaService.findInfoById(10, "en");
        assertEquals(filmInfo, expected);
    }

    @Test
    public void findInfoByIdNegativeTest() throws ServiceException, DaoException {
        FilmInfo expected = new FilmInfo("description", "yearOfCreation",
                "genre", 10, "link");
        when(mediaDao.findInfoById(10, "en")).thenReturn(expected);
        FilmInfo filmInfo = mediaService.findInfoById(1, "en");
        assertNotEquals(filmInfo, expected);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void findInfoByIdThrowServiceExceptionTest() throws ServiceException, DaoException {
        when(mediaDao.findInfoById(10, "en")).thenThrow(DaoException.class);
        mediaService.findInfoById(10, "en");
    }

    @Test
    public void createFilmPositiveTest() throws ServiceException, DaoException {
        when(mediaDao.create(anyObject())).thenReturn(true);
        when(mediaDao.isFilmExist("Venom")).thenReturn(false);
        boolean result = mediaService.createFilm("description", "yearOfCreation",
                "genre", "link", "Venom", "en");
        assertTrue(result);
    }

    @Test
    public void createFilmNegativeTest() throws ServiceException, DaoException {
        when(mediaDao.create(anyObject())).thenReturn(true);
        when(mediaDao.isFilmExist("Venom")).thenReturn(true);
        boolean result = mediaService.createFilm("description", "yearOfCreation",
                "genre", "link", "Venom", "en");
        assertFalse(result);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void createFilmThrowServiceExceptionTest() throws ServiceException, DaoException {
        when(mediaDao.create(anyObject())).thenThrow(DaoException.class);
        mediaService.createFilm("description", "yearOfCreation",
                "genre", "link", "Venom", "en");
    }

    @Test
    public void updateFilmPositiveTest() throws ServiceException, DaoException {
        Film expected = new Film(15, "Venom", "venom.jpg", new FilmInfo("description",
                "yearOfCreation", "genre", "link"));
        when(mediaDao.findFilmById(15, "ru")).thenReturn(expected);
        when(mediaDao.update(anyObject())).thenReturn(expected);

        Film actual = mediaService.updateFilm(15, "Venom", "description", "yearOfCreation",
                "ru", "link");
        assertEquals(actual, expected);
    }

    @Test
    public void updateFilmNegativeTest() throws ServiceException, DaoException {
        Film expected = new Film(15, "Venom", "venom.jpg", new FilmInfo("description",
                "yearOfCreation", "genre", "link"));
        when(mediaDao.findFilmById(15, "ru")).thenReturn(expected);
        when(mediaDao.update(anyObject())).thenReturn(expected);

        Film actual = mediaService.updateFilm(10, "Venom", "description", "yearOfCreation",
                "ru", "link");
        assertNotEquals(actual, expected);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void updateFilmThrowServiceExceptionTest() throws ServiceException, DaoException {
        when(mediaDao.update(anyObject())).thenThrow(DaoException.class);
        mediaService.updateFilm(15, "Venom", "description", "yearOfCreation",
                "ru", "link");
    }

    @Test
    public void updateInfoEnPositiveTest() throws ServiceException, DaoException {
        Film expected = new Film(8, "Venom", "venom.jpg", new FilmInfo("description",
                "yearOfCreation", "genre", "link"));
        when(mediaDao.findFilmById(8, "en")).thenReturn(expected);
        when(mediaDao.updateInfoEn(anyObject())).thenReturn(expected);

        Film actual = mediaService.updateInfoEn(8, "Venom", "description", "genre",
                "en", "link", "yearOfCreation");
        assertEquals(actual, expected);
    }

    @Test
    public void updateInfoEnNegativeTest() throws ServiceException, DaoException {
        Film expected = new Film(7, "Venom", "venom.jpg", new FilmInfo("description",
                "yearOfCreation", "genre", "link"));
        when(mediaDao.findFilmById(7, "en")).thenReturn(expected);
        when(mediaDao.updateInfoEn(anyObject())).thenReturn(expected);

        Film actual = mediaService.updateInfoEn(9, "Venom", "description", "genre",
                "en", "link", "yearOfCreation");
        assertNotEquals(actual, expected);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void updateInfoEnThrowServiceExceptionTest() throws ServiceException {
        when(mediaService.updateInfoEn(10, "Venom", "description", "genre",
                "en", "link", "yearOfCreation")).thenThrow(ServiceException.class);
        mediaService.updateInfoEn(10, "Venom", "description", "genre",
                "en", "link", "yearOfCreation");
    }

    @Test
    public void findAllFilmsPositiveTest() throws ServiceException, DaoException {
        List<Film> films = new ArrayList<>();
        films.add(new Film(1, "Me before you", "me.jpg", new FilmInfo("description",
                "2020", "detective", "teew.com")));
        films.add(new Film(1, "Avatar", "me.jpg", new FilmInfo("description1",
                "2019", "detective1", "teew.com1")));
        when(mediaDao.findAllFilms(1, 2, "en"))
                .thenReturn(films);
        List<Film> filmsActual = mediaService.findAllFilms(1, 2, "en");
        assertEquals(filmsActual, films);
    }

    @Test
    public void findAllFilmsNegativeTest() throws ServiceException, DaoException {
        List<Film> films = new ArrayList<>();
        films.add(new Film(1, "Me before you", "me.jpg", new FilmInfo("description",
                "2020", "detective", "teew.com")));
        films.add(new Film(1, "Avatar", "me.jpg", new FilmInfo("description1",
                "2019", "detective1", "teew.com1")));
        when(mediaDao.findAllFilms(1, 2, "en"))
                .thenReturn(films);
        List<Film> filmsActual = mediaService.findAllFilms(1, 2, "ru");
        assertNotEquals(filmsActual, films);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void findAllFilmsThrowServiceExceptionTest() throws ServiceException, DaoException {
        when(mediaDao.findAllFilms(1, 2, "en")).thenThrow(DaoException.class);
        mediaService.findAllFilms(1, 2, "en");
    }

    @Test
    public void changeActiveFilmPositiveTest() throws ServiceException, DaoException {
        Film expected = new Film(15, "Venom", "venom.jpg", new FilmInfo("description",
                "yearOfCreation", "genre", "link"));
        when(mediaDao.changeActiveFilm(anyObject())).thenReturn(expected);
        Film actual = mediaService.changeActiveFilm(expected);
        assertEquals(actual, expected);
    }

    @Test
    public void changeActiveFilmNegativeTest() throws ServiceException, DaoException {
        Film expected = new Film(15, "Venom", "venom.jpg", new FilmInfo("description",
                "yearOfCreation", "genre", "link"));
        when(mediaDao.changeActiveFilm(anyObject())).thenReturn(expected);
        Film actual = mediaService.changeActiveFilm(new Film(10, "Venom", "venom.jpg", new FilmInfo("description",
                "yearOfCreation", "genre", "link")));
        assertNotEquals(actual, expected);
    }

    @Test
    public void findFilmIdByNamePositiveTest() throws ServiceException, DaoException {
        long expected = 1;
        when(mediaDao.findFilmIdByFilmName("Avatar")).thenReturn(expected);
        long actual = mediaService.findFilmIdByName("Avatar");
        assertEquals(actual, expected);
    }

    @Test
    public void findFilmIdByNameNegativeTest() throws ServiceException, DaoException {
        long expected = 1;
        when(mediaDao.findFilmIdByFilmName("Avatar")).thenReturn(expected);
        long actual = mediaService.findFilmIdByName("Avatar");
        assertEquals(actual, expected);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void findFilmIdByNameThrowServiceExceptionTest() throws ServiceException, DaoException {
        when(mediaDao.findFilmIdByFilmName("Avatar")).thenThrow(DaoException.class);
        mediaService.findFilmIdByName("Avatar");
    }

    @Test
    public void findFilmByFilmNamePositiveTest() throws ServiceException, DaoException {
        List<Film> films = new ArrayList<>();
        films.add(new Film(1, "Me before you", "me.jpg", new FilmInfo("description",
                "2020", "detective", "teew.com")));
        films.add(new Film(1, "Avatar", "me.jpg", new FilmInfo("description1",
                "2019", "detective1", "teew.com1")));
        when(mediaDao.findFilmByFilmName("Me before you", "en")).thenReturn(films);
        List<Film> filmsActual = mediaService.findFilmByFilmName("Me before you", "en");
        assertEquals(filmsActual, films);
    }

    @Test
    public void findFilmByFilmNameNegativeTest() throws ServiceException, DaoException {
        List<Film> films = new ArrayList<>();
        films.add(new Film(1, "Me before you", "me.jpg", new FilmInfo("description",
                "2020", "detective", "teew.com")));
        films.add(new Film(1, "Avatar", "me.jpg", new FilmInfo("description1",
                "2019", "detective1", "teew.com1")));
        when(mediaDao.findFilmByFilmName("Me before you", "en")).thenReturn(films);
        List<Film> filmsActual = mediaService.findFilmByFilmName("Me before you", "ru");
        assertNotEquals(filmsActual, films);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void findFilmByFilmNameThrowServiceExceptionTest() throws ServiceException, DaoException {
        when(mediaDao.findFilmByFilmName("Me before you", "en")).thenThrow(DaoException.class);
        mediaService.findFilmByFilmName("Me before you", "en");
    }

    @Test
    public void findFilmByDescriptionPositiveTest() throws ServiceException, DaoException {
        List<Film> films = new ArrayList<>();
        films.add(new Film(1, "Me before you", "me.jpg", new FilmInfo("description",
                "2020", "detective", "teew.com")));
        films.add(new Film(1, "Avatar", "me.jpg", new FilmInfo("description1",
                "2019", "detective1", "teew.com1")));
        when(mediaDao.findFilmByDescription("Me before you", "en")).thenReturn(films);
        List<Film> filmsActual = mediaService.findFilmByDescription("Me before you", "en");
        assertEquals(filmsActual, films);
    }

    @Test
    public void findFilmByDescriptionNegativeTest() throws ServiceException, DaoException {
        List<Film> films = new ArrayList<>();
        films.add(new Film(1, "Me before you", "me.jpg", new FilmInfo("description",
                "2020", "detective", "teew.com")));
        films.add(new Film(1, "Avatar", "me.jpg", new FilmInfo("description1",
                "2019", "detective1", "teew.com1")));
        when(mediaDao.findFilmByDescription("Me before you", "en")).thenReturn(films);
        List<Film> filmsActual = mediaService.findFilmByDescription("Me before you", "ru");
        assertNotEquals(filmsActual, films);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void findFilmByDescriptionThrowServiceExceptionTest() throws ServiceException, DaoException {
        when(mediaDao.findFilmByDescription("Me before you", "en")).thenThrow(DaoException.class);
        mediaService.findFilmByDescription("Me before you", "en");
    }

    @Test
    public void findFilmByGenrePositiveTest() throws ServiceException, DaoException {
        List<Film> films = new ArrayList<>();
        films.add(new Film(1, "Me before you", "me.jpg", new FilmInfo("description",
                "2020", "detective", "teew.com")));
        films.add(new Film(1, "Avatar", "me.jpg", new FilmInfo("description1",
                "2019", "detective1", "teew.com1")));
        when(mediaDao.findFilmByGenre("Me before you", "en")).thenReturn(films);
        List<Film> filmsActual = mediaService.findFilmByGenre("Me before you", "en");
        assertEquals(filmsActual, films);
    }

    @Test
    public void findFilmByGenreNegativeTest() throws ServiceException, DaoException {
        List<Film> films = new ArrayList<>();
        films.add(new Film(1, "Me before you", "me.jpg", new FilmInfo("description",
                "2020", "detective", "teew.com")));
        films.add(new Film(1, "Avatar", "me.jpg", new FilmInfo("description1",
                "2019", "detective1", "teew.com1")));
        when(mediaDao.findFilmByGenre("Me before you", "en")).thenReturn(films);
        List<Film> filmsActual = mediaService.findFilmByGenre("Me before you", "en");
        assertEquals(filmsActual, films);
    }
}