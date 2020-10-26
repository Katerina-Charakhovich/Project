package by.epam.project.service;

import by.epam.project.entity.impl.Film;
import by.epam.project.entity.impl.FilmInfo;
import by.epam.project.service.exception.ServiceException;

import java.util.List;

public interface MediaService {
    Film isFilmExist(String name) throws ServiceException;

    List<Film> findAllUndeletedFilms(int currentPage, int filmsOnPage) throws ServiceException;

    int calculateNumberOfRows() throws ServiceException;

    Film findFilmByName(String name) throws ServiceException;

    Film findFilmById(long id) throws ServiceException;

    FilmInfo findInfoById(long filmId) throws ServiceException;
}
