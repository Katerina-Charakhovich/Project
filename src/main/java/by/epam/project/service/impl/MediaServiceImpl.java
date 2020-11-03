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
            LOGGER.log(Level.ERROR, "Film not found");
            throw new ServiceException();
        }
        return result;
    }

    public List<Film> findAllActiveFilms(int currentPage, int filmsOnPage, String language, boolean active) throws ServiceException {
        List<Film> films;
        try {
            films = mediaDao.findAllActiveFilms(currentPage, filmsOnPage, language,active);
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

    public Film findFilmByName(String name, String language) throws ServiceException {
        try {
            return mediaDao.findFilmByName(name, language);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "Film not found", e);
            throw new ServiceException("Film not found", e);
        }
    }

    public Film findFilmById(long id, String language) throws ServiceException {
        try {
            return mediaDao.findFilmById(id, language);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "Film not found", e);
            throw new ServiceException("Film not found", e);
        }
    }

    public FilmInfo findInfoById(long filmId, String language) throws ServiceException {
        try {
            return mediaDao.findInfoById(filmId, language);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "Film not found", e);
            throw new ServiceException("Film not found", e);
        }
    }

    @Override
    public boolean createFilm(String description, String yearOfCreation, String genre, String link, String filmName,String language) throws ServiceException {
        boolean result = false;
        try {
            if (!isFilmExist(filmName)) {
                Film film = new Film();
                film.setFilmName(filmName);
                film.setFilmInfo(new FilmInfo(description,yearOfCreation,genre,link));
                result = mediaDao.create(film);
            }
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "Creation failed", e);
            throw new ServiceException("Creation failed", e);
        }
        return result;
    }

    @Override
    public Film updateFilm(long id, String filmNameRu, String descriptionRu, String genreRu, String language, String linkRu) throws ServiceException {
        Film film;
        try {
            film = mediaDao.findFilmById(id, language);
            if (film != null) {
                film.setFilmName(filmNameRu);
                film.getFilmInfo().setDescription(descriptionRu);
                film.getFilmInfo().setGenre(genreRu);
                film.getFilmInfo().setLink(linkRu);
                film = mediaDao.update(film);
            }
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "Update not successfully", e);
            throw new ServiceException("Update not successfully", e);
        }
        return film;
    }

    @Override
    public void updateAvatarRu(String filmName, String avatarRu, String language) throws ServiceException {
        Film film ;
        try {
            film = mediaDao.findFilmByName(filmName, "ru");
            film.setFilmAvatar(avatarRu);
            mediaDao.updateAvatarRu(film);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "Update not successfully", e);
            throw new ServiceException("Update not successfully", e);
        }
    }

    @Override
    public void updateAvatarEn(String filmName, String avatarEn, String language) throws ServiceException {
        Film film ;
        try {
            film = mediaDao.findFilmByName(filmName, "en");
            film.setFilmAvatar(avatarEn);
            mediaDao.updateAvatarEn(film);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "Update not successfully", e);
            throw new ServiceException("Update not successfully", e);
        }
    }

    @Override
    public Film updateInfoEn(long filmId, String filmNameEn, String descriptionEn, String genreEn, String language, String linkEn, String yearOfCreation) throws ServiceException {
        Film film;
        try {
            film = mediaDao.findFilmById(filmId,language);
            if (film != null) {
                film.setFilmName(filmNameEn);
                film.getFilmInfo().setDescription(descriptionEn);
                film.getFilmInfo().setGenre(genreEn);
                film.getFilmInfo().setLink(linkEn);
                film.getFilmInfo().setYearOfCreation(yearOfCreation);
                film = mediaDao.updateInfoEn(film);
            }
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "Update not successfully", e);
            throw new ServiceException("Update not successfully", e);
        }
        return film;
    }

    @Override
    public List<Film> findAllFilms(int currentPage, int filmsOnPage, String language) throws ServiceException {
        List<Film> films;
        try {
            films = mediaDao.findAllFilms(currentPage, filmsOnPage, language);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "Film not found");
            throw new ServiceException("Film not found", e);
        }
        return films;
    }

    @Override
    public Film changeActiveFilm(Film film) throws ServiceException {
        if (isFilmExist(film.getFilmName())) {
            if (!film.isActive()) {
                film.setActive(true);
            } else {
                film.setActive(false);
            }
            try {
               film = mediaDao.changeActiveFilm(film);
            } catch (DaoException e) {
                LOGGER.log(Level.ERROR, "Activity could not be changed", e);
                throw new ServiceException("Activity could not be changed", e);
            }
        }
        return film;
    }

    @Override
    public long findFilmIdByName(String filmName) throws ServiceException {
        try {
            return mediaDao.findFilmIdByFilmName(filmName);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "Film id not found", e);
            throw new ServiceException("Film id not found", e);
        }
    }

}

