package by.epam.project.service;

import by.epam.project.dao.exception.DaoException;
import by.epam.project.dao.impl.MediaDaoImpl;
import by.epam.project.entity.impl.Film;
import by.epam.project.entity.impl.FilmInfo;
import by.epam.project.service.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class MediaService {
    private static MediaService instance;
    private static final MediaDaoImpl mediaDao = MediaDaoImpl.getInstance();
    public static final Logger LOGGER = LogManager.getLogger();

    private MediaService() {
    }

    public static MediaService getInstance() {
        if (instance == null) {
            instance = new MediaService();
        }
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
        List<Film>films;
        try {
            films = mediaDao.findAllUndeletedFilms(currentPage, filmsOnPage);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR,"Film not found");
            throw new ServiceException("Film not found",e);
        }
        return films;
    }
    public int getNumberOfRows() throws ServiceException {
        try {
            return mediaDao.getNumberOfRows();
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR,"Film not found",e);
            throw new ServiceException("Film not found",e);
        }
    }
    public Film findFilmByName(String name) throws ServiceException {
        try {
            return  mediaDao.findFilmByName(name);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR,"Film not found",e);
            throw new ServiceException("Film not found",e);
        }
    }
    public Film findFilmById(long id) throws ServiceException {
        try {
            return  mediaDao.findFilmById(id);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR,"Film not found",e);
            throw new ServiceException("Film not found",e);
        }
    }
    public FilmInfo findInfoById(long filmId) throws ServiceException {
        try {
            return  mediaDao.findInfoById(filmId);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR,"Film not found",e);
            throw new ServiceException("Film not found",e);
        }
    }
}

