package by.epam.project.service;

import by.epam.project.entity.impl.Film;
import by.epam.project.entity.impl.FilmInfo;
import by.epam.project.service.exception.ServiceException;

import java.util.List;

public interface MediaService {
    boolean isFilmExist(String name) throws ServiceException;

    List<Film> findAllActiveFilms(int currentPage, int filmsOnPage,
                                  String language, boolean active) throws ServiceException;

    int calculateNumberOfRows() throws ServiceException;

    Film findFilmByName(String name,String language) throws ServiceException;

    Film findFilmById(long id,String language) throws ServiceException;

    FilmInfo findInfoById(long filmId,String language) throws ServiceException;

    boolean createFilm(String description, String yearOfCreation,
                       String genre, String link, String filmName, String language) throws ServiceException;

    Film updateFilm(long id,String filmNameRu, String descriptionRu,
                    String genreRu,String language, String linkRu) throws ServiceException;

    void updateAvatarRu(String filmName, String avatarRu,String language) throws ServiceException;

    void updateAvatarEn(String filmName, String avatarEn, String language) throws ServiceException;

    Film updateInfoEn(long filmId, String filmNameEn, String descriptionEn,
                    String genreEn, String language, String linkEn, String yearOfCreation) throws ServiceException;

    List<Film> findAllFilms(int currentPage, int filmsOnPage, String language) throws ServiceException;

    Film changeActiveFilm(Film film) throws ServiceException;

    long findFilmIdByName(String filmName) throws ServiceException;
}
