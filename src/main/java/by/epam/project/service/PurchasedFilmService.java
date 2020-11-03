package by.epam.project.service;

import by.epam.project.entity.impl.Film;
import by.epam.project.entity.impl.PurchasedFilm;
import by.epam.project.entity.impl.User;
import by.epam.project.service.exception.ServiceException;

import java.util.List;
import java.util.Map;


public interface PurchasedFilmService {
    List<Film> findAllPurchasedFilmsByUser(String email, String language)
            throws ServiceException;

    PurchasedFilm findPurchasedFilmById(long userId) throws ServiceException;

    boolean purchaseFilm(String email, long filmId) throws ServiceException;

    PurchasedFilm findPurchasedFilmByUserName(String name, long filmId) throws ServiceException;

    Map<User,Film> findAllInfoAboutPurchasedFilms(int currentPage, int purchasedFilmsOnPage, String language) throws ServiceException;

    public int calculateNumberOfRowsByPurchasedFilms() throws ServiceException;
}
