package by.epam.project.dao;

import by.epam.project.dao.exception.DaoException;
import by.epam.project.entity.impl.Film;
import by.epam.project.entity.impl.PurchasedFilm;
import by.epam.project.entity.impl.User;

import java.util.List;
import java.util.Map;


public interface PurchasedFilmDao extends BaseDao<PurchasedFilm> {
    List<Long> findAllPurchasedFilmsByUser(long id) throws DaoException;

    Map<User, Film> findAllInfoAboutUsersAndPurchasedFilms
            (int currentPage, int purchasedFilmsOnPage, String language) throws DaoException;

    int calculateNumberOfRowsByPurchasedFilms() throws DaoException;
}
