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

    public boolean isFilmExist(String name) throws ServiceException {
        boolean result;
        try {
           result = mediaDao.isFilmExist(name);

        } catch (DaoException e) {
            throw new ServiceException();
        }
        return result;
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

    @Override
    public boolean createFilm(String filmName) throws ServiceException {
        boolean result = false;
        try {
            if (!isFilmExist(filmName)) {
                Film film = new Film();
                film.setFilmName(filmName);
                result = mediaDao.create(film);
            }
            } catch (DaoException e) {
                LOGGER.log(Level.ERROR, "Creation failed", e);
                throw new ServiceException("Creation failed", e);
            }
        return result;
    }

    @Override
    public boolean createFilmInfo(String link, String genre, String description, int yearOfCreation, long filmId) throws ServiceException {
        boolean result = false;
        try {
                Film film = findFilmById(filmId);
                if (film != null){
                    FilmInfo filmInfo = new FilmInfo(description,yearOfCreation,genre,
                            filmId,link);
                    result = mediaDao.createFilmInfo(filmInfo);
            }
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "Creation failed", e);
            throw new ServiceException("Creation failed", e);
        }
        return result;
    }
}

