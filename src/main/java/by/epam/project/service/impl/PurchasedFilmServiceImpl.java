package by.epam.project.service.impl;

import by.epam.project.dao.PurchasedFilmDao;
import by.epam.project.dao.DaoException;
import by.epam.project.dao.impl.PurchasedDaoImpl;
import by.epam.project.entity.impl.Film;
import by.epam.project.entity.impl.FilmInfo;
import by.epam.project.entity.impl.PurchasedFilm;
import by.epam.project.entity.impl.User;
import by.epam.project.service.MediaService;
import by.epam.project.service.PurchasedFilmService;
import by.epam.project.service.UserService;
import by.epam.project.service.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PurchasedFilmServiceImpl implements PurchasedFilmService {
    public static final Logger LOGGER = LogManager.getLogger();
    private static final PurchasedFilmServiceImpl instance = new PurchasedFilmServiceImpl();
    private UserService userService = UserServiceImpl.getInstance();
    private MediaService mediaService = MediaServiceImpl.getInstance();
    private PurchasedFilmDao purchasedFilmDao = PurchasedDaoImpl.getInstance();

    private PurchasedFilmServiceImpl() {

    }

    public static PurchasedFilmServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Film> findAllPurchasedFilmsByUser(String email, String language) throws ServiceException {
        List<Long> filmsId;
        List<Film> films = new ArrayList<>();
        try {
            long userId = userService.findUserIdByLogin(email);
            filmsId = purchasedFilmDao.findAllPurchasedFilmsByUser(userId);
            for (Long filmId : filmsId) {
                Film film = mediaService.findFilmById(filmId, language);
                FilmInfo filmInfo = mediaService.findInfoById(filmId, language);
                film.setFilmInfo(filmInfo);
                films.add(film);
            }
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "Purchased Films not found");
            throw new ServiceException();
        }
        return films;
    }

    @Override
    public PurchasedFilm findPurchasedFilmById(long userId) throws ServiceException {
        try {
            return purchasedFilmDao.findEntityById(userId);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "Purchased Films not found");
            throw new ServiceException();
        }
    }

    @Override
    public boolean purchaseFilm(String email, long filmId) throws ServiceException {
        boolean result = false;
        try {
            long userId = userService.findUserIdByLogin(email);
            PurchasedFilm purchasedFilm = new PurchasedFilm(userId, filmId);
            if (purchasedFilmDao.create(purchasedFilm)) {
                result = true;
            }

        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "Failed to buy movie", e);
            throw new ServiceException("Failed to buy movie", e);
        }
        return result;
    }

    @Override
    public PurchasedFilm findPurchasedFilmByUserName(String name, long filmId) throws ServiceException {
        PurchasedFilm purchasedFilm = null;
        try {
            long userId = userService.findUserIdByLogin(name);
            List<Long> filmsId = purchasedFilmDao.findAllPurchasedFilmsByUser(userId);
            for (Long idFilm : filmsId) {
                if (filmId == idFilm) {
                    purchasedFilm = new PurchasedFilm(userId, filmId);
                }
            }
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "Purchased Films not found");
            throw new ServiceException();
        }
        return purchasedFilm;
    }

    @Override
    public Map<User, Film> findAllInfoAboutPurchasedFilms
            (int currentPage, int purchasedFilmsOnPage, String language) throws ServiceException {
        try {
            return purchasedFilmDao.findAllInfoAboutUsersAndPurchasedFilms
                    (currentPage, purchasedFilmsOnPage, language);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "Purchased Films not found");
            throw new ServiceException();
        }
    }

    @Override
    public int calculateNumberOfRowsByPurchasedFilms() throws ServiceException {
        try {
            return purchasedFilmDao.calculateNumberOfRowsByPurchasedFilms();
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, "Purchased film not found", e);
            throw new ServiceException("Purchased film not found", e);
        }
    }
}
