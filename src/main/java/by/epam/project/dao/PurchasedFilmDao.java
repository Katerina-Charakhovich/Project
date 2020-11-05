package by.epam.project.dao;

import by.epam.project.entity.impl.Film;
import by.epam.project.entity.impl.PurchasedFilm;
import by.epam.project.entity.impl.User;

import java.util.List;
import java.util.Map;


/**
 * The interface Purchased film dao.
 */
public interface PurchasedFilmDao extends BaseDao<PurchasedFilm> {
    /**
     * Find all purchased films by user list.
     *
     * @param id the id
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Long> findAllPurchasedFilmsByUser(long id) throws DaoException;

    /**
     * Find all info about users and purchased films map.
     *
     * @param currentPage          the current page
     * @param purchasedFilmsOnPage the purchased films on page
     * @param language             the language
     * @return the map
     * @throws DaoException the dao exception
     */
    Map<User, Film> findAllInfoAboutUsersAndPurchasedFilms
            (int currentPage, int purchasedFilmsOnPage, String language) throws DaoException;

    /**
     * Calculate number of rows by purchased films int.
     *
     * @return the int
     * @throws DaoException the dao exception
     */
    int calculateNumberOfRowsByPurchasedFilms() throws DaoException;
}
