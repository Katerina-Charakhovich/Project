package by.epam.project.service.impl;

import by.epam.project.dao.exception.DaoException;
import by.epam.project.dao.impl.MediaDaoImpl;
import by.epam.project.entity.impl.Film;
import by.epam.project.entity.impl.FilmInfo;
import by.epam.project.service.MediaService;
import by.epam.project.service.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * The type Media service.
 */
public class MediaServiceImpl implements MediaService {
    private static final MediaServiceImpl instance = new MediaServiceImpl();
    private static final MediaDaoImpl mediaDao = MediaDaoImpl.getInstance();
    public static final Logger LOGGER = LogManager.getLogger();

    private MediaServiceImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static MediaServiceImpl getInstance() {
        return instance;
    }

    public Film isFilmExist(String name) throws ServiceException {
        Film film;
        try {
            film = mediaDao.findFilmByName(name);
        } catch (DaoException e) {
            throw new ServiceException();
        }
        return film;
    }

    public List<Film> findAllUndeletedFilms(int currentPage, int filmsOnPage) throws ServiceException {
        List<Film> films;
        try {
            films = mediaDao.findAllUndeletedFilms(currentPage, filmsOnPage);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "Film not found");
            throw new ServiceException("Film not found", e);
        }
        return films;
    }

    public int calculateNumberOfRows() throws ServiceException {
        try {
            return mediaDao.calculateNumberOfRows();
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "Film not found", e);
            throw new ServiceException("Film not found", e);
        }
    }

    public Film findFilmByName(String name) throws ServiceException {
        try {
            return mediaDao.findFilmByName(name);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "Film not found", e);
            throw new ServiceException("Film not found", e);
        }
    }

    public Film findFilmById(long id) throws ServiceException {
        try {
            return mediaDao.findFilmById(id);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "Film not found", e);
            throw new ServiceException("Film not found", e);
        }
    }

    public FilmInfo findInfoById(long filmId) throws ServiceException {
        try {
            return mediaDao.findInfoById(filmId);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "Film not found", e);
            throw new ServiceException("Film not found", e);
        }
    }
}

