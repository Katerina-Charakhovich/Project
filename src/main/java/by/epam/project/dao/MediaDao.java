package by.epam.project.dao;

import by.epam.project.dao.exception.DaoException;
import by.epam.project.entity.impl.Film;
import by.epam.project.entity.impl.FilmInfo;

import java.util.List;

public interface MediaDao extends BaseDao<Film> {
    Film findFilmByName(String name, String Language) throws DaoException;

    List<Film> findAllUndeletedFilms(int currentPage, int filmsOnPage, String language) throws DaoException;

    int calculateNumberOfRows() throws DaoException;

    Film findFilmById(long id, String language) throws DaoException;

    FilmInfo findInfoById(long filmId, String language) throws DaoException;

    boolean isFilmExist(String filmName) throws DaoException;

    boolean createFilmInfo(FilmInfo filminfo) throws DaoException;

}
