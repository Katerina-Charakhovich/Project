package by.epam.project.service.impl;

import by.epam.project.dao.exception.DaoException;
import by.epam.project.dao.MediaDao;
import by.epam.project.dao.impl.MediaDaoImpl;
import by.epam.project.entity.impl.Film;
import by.epam.project.entity.impl.FilmInfo;
import by.epam.project.service.MediaService;
import by.epam.project.service.exception.ServiceException;

import java.util.List;

import static by.epam.project.service.TypeOfSearch.*;

/**
 * Service that works with user data
 */
public class MediaServiceImpl implements MediaService {
    private static final MediaServiceImpl instance = new MediaServiceImpl();
    private static final MediaDao mediaDao = MediaDaoImpl.getInstance();

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

    @Override
    public boolean isFilmExist(String name) throws ServiceException {
        try {
            return mediaDao.isFilmExist(name);
        } catch (DaoException e) {
            throw new ServiceException("Film not found", e);
        }
    }

    @Override
    public List<Film> findAllActiveFilms(int currentPage, int filmsOnPage, String language,
                                         boolean active) throws ServiceException {
        try {
            return mediaDao.findAllActiveFilms(currentPage, filmsOnPage, language, active);
        } catch (DaoException e) {
            throw new ServiceException("Film not found", e);
        }
    }

    @Override
    public int calculateNumberOfAllRows() throws ServiceException {
        try {
            return mediaDao.calculateNumberOfAllRows();
        } catch (DaoException e) {
            throw new ServiceException("Film not found", e);
        }
    }

    @Override
    public int calculateNumberOfActiveRows() throws ServiceException {
        try {
            return mediaDao.calculateNumberOfActiveRows();
        } catch (DaoException e) {
            throw new ServiceException("Film not found", e);
        }
    }

    @Override
    public Film findFilmByName(String name, String language) throws ServiceException {
        try {
            return mediaDao.findFilmByName(name, language);
        } catch (DaoException e) {
            throw new ServiceException("Film not found", e);
        }
    }

    @Override
    public Film findFilmById(long id, String language) throws ServiceException {
        try {
            return mediaDao.findFilmById(id, language);
        } catch (DaoException e) {
            throw new ServiceException("Film not found", e);
        }
    }

    @Override
    public FilmInfo findInfoById(long filmId, String language) throws ServiceException {
        try {
            return mediaDao.findInfoById(filmId, language);
        } catch (DaoException e) {
            throw new ServiceException("Film not found", e);
        }
    }

    @Override
    public boolean createFilm(String description, String yearOfCreation, String genre,
                              String link, String filmName, String language) throws ServiceException {
        boolean result = false;
        try {
            if (!isFilmExist(filmName)) {
                Film film = new Film();
                film.setFilmName(filmName);
                film.setFilmInfo(new FilmInfo(description, yearOfCreation, genre, link));
                result = mediaDao.create(film);
            }
        } catch (DaoException e) {
            throw new ServiceException("Creation failed", e);
        }
        return result;
    }

    @Override
    public Film updateFilm(long id, String filmName, String description,
                           String genre, String language, String link) throws ServiceException {
        Film film;
        try {
            film = mediaDao.findFilmById(id, language);
            if (film != null) {
                film.setFilmName(filmName);
                film.getFilmInfo().setDescription(description);
                film.getFilmInfo().setGenre(genre);
                film.getFilmInfo().setLink(link);
                film = mediaDao.update(film);
            }
        } catch (DaoException e) {
            throw new ServiceException("Update not successfully", e);
        }
        return film;
    }

    @Override
    public void updateAvatarRu(String filmName, String avatarRu, String language) throws ServiceException {
        try {
            mediaDao.updateAvatarRu(avatarRu, filmName);
        } catch (DaoException e) {
            throw new ServiceException("Update not successfully", e);
        }
    }

    @Override
    public void updateAvatarEn(String filmName, String avatarEn, String language) throws ServiceException {
        Film film;
        try {
            film = mediaDao.findFilmByName(filmName, "en");
            film.setFilmAvatar(avatarEn);
            mediaDao.updateAvatarEn(film);
        } catch (DaoException e) {
            throw new ServiceException("Update not successfully", e);
        }
    }

    @Override
    public Film updateInfoEn(long filmId, String filmNameEn,
                             String descriptionEn, String genreEn, String language,
                             String linkEn, String yearOfCreation) throws ServiceException {
        Film film;
        try {
            film = mediaDao.findFilmById(filmId, language);
            if (film != null) {
                film.setFilmName(filmNameEn);
                film.getFilmInfo().setDescription(descriptionEn);
                film.getFilmInfo().setGenre(genreEn);
                film.getFilmInfo().setLink(linkEn);
                film.getFilmInfo().setYearOfCreation(yearOfCreation);
                film = mediaDao.updateInfoEn(film);
            }
        } catch (DaoException e) {
            throw new ServiceException("Update not successfully", e);
        }
        return film;
    }

    @Override
    public List<Film> findAllFilms(int currentPage, int filmsOnPage, String language) throws ServiceException {
        try {
            return mediaDao.findAllFilms(currentPage, filmsOnPage, language);
        } catch (DaoException e) {
            throw new ServiceException("Film not found", e);
        }
    }

    @Override
    public Film changeActiveFilm(Film film) throws ServiceException {
        if (isFilmExist(film.getFilmName())) {
            film.setActive(!film.isActive());
            try {
                film = mediaDao.changeActiveFilm(film);
            } catch (DaoException e) {
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
            throw new ServiceException("Film id not found", e);
        }
    }

    @Override
    public List<Film> findFilmByFilmName(String filmName, String language) throws ServiceException {
        try {
            return mediaDao.findFilmByFilmName(filmName, language);
        } catch (DaoException e) {
            throw new ServiceException("Film not found", e);
        }
    }

    @Override
    public List<Film> findFilmByDescription(String description, String language) throws ServiceException {
        try {
            return mediaDao.findFilmByDescription(description, language);
        } catch (DaoException e) {
            throw new ServiceException("Film not found", e);
        }
    }

    @Override
    public List<Film> findFilmByGenre(String genre, String language) throws ServiceException {
        try {
            return mediaDao.findFilmByGenre(genre, language);
        } catch (DaoException e) {
            throw new ServiceException("Film not found", e);
        }
    }

    @Override
    public List<Film> findFilms(String searchContent, String typeOfSearch, String language) throws ServiceException {
        List<Film> films;
        switch (typeOfSearch) {
            case FILM_NAME:
                films = findFilmByFilmName(searchContent, language.toLowerCase());
                break;
            case DESCRIPTION:
                films = findFilmByDescription(searchContent, language.toLowerCase());
                break;
            case GENRE:
                films = findFilmByGenre(searchContent, language.toLowerCase());
                break;
            default:
                throw new ServiceException("Type of search not found");
        }
        return films;
    }
}

