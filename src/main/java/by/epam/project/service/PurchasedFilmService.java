package by.epam.project.service;

import by.epam.project.entity.impl.Film;
import by.epam.project.entity.impl.PurchasedFilm;
import by.epam.project.entity.impl.User;
import by.epam.project.service.exception.ServiceException;

import java.util.List;
import java.util.Map;


/**
 * The interface Purchased film service.
 */
public interface PurchasedFilmService {
    /**
     * Find all purchased films by user list.
     *
     * @param email    the email
     * @param language the language
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Film> findAllPurchasedFilmsByUser(String email, String language)
            throws ServiceException;

    /**
     * Find purchased film by id purchased film.
     *
     * @param userId the user id
     * @return the purchased film
     * @throws ServiceException the service exception
     */
    PurchasedFilm findPurchasedFilmById(long userId) throws ServiceException;

    /**
     * Purchase film boolean.
     *
     * @param email  the email
     * @param filmId the film id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean purchaseFilm(String email, long filmId) throws ServiceException;

    /**
     * Find purchased film by user name purchased film.
     *
     * @param name   the name
     * @param filmId the film id
     * @return the purchased film
     * @throws ServiceException the service exception
     */
    PurchasedFilm findPurchasedFilmByUserName(String name, long filmId) throws ServiceException;

    /**
     * Find all info about purchased films map.
     *
     * @param currentPage          the current page
     * @param purchasedFilmsOnPage the purchased films on page
     * @param language             the language
     * @return the map
     * @throws ServiceException the service exception
     */
    Map<User, Film> findAllInfoAboutPurchasedFilms(int currentPage, int purchasedFilmsOnPage, String language) throws ServiceException;

    /**
     * Calculate number of rows by purchased films int.
     *
     * @return the int
     * @throws ServiceException the service exception
     */
    int calculateNumberOfRowsByPurchasedFilms() throws ServiceException;
}
