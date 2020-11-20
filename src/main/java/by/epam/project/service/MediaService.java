package by.epam.project.service;

import by.epam.project.entity.impl.Film;
import by.epam.project.entity.impl.FilmInfo;
import by.epam.project.service.exception.ServiceException;

import java.util.List;

/**
 * The interface Media service.
 */
public interface MediaService {
    /**
     * Is film exist boolean.
     *
     * @param name the name
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean isFilmExist(String name) throws ServiceException;

    /**
     * Find all active films list.
     *
     * @param currentPage the current page
     * @param filmsOnPage the films on page
     * @param language    the language
     * @param active      the active
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Film> findAllActiveFilms(int currentPage, int filmsOnPage,
                                  String language, boolean active) throws ServiceException;

    /**
     * Calculate number of active rows int.
     *
     * @return the int
     * @throws ServiceException the service exception
     */
    int calculateNumberOfActiveRows() throws ServiceException;

    /**
     * Calculate number of all rows int.
     *
     * @return the int
     * @throws ServiceException the service exception
     */
    int calculateNumberOfAllRows() throws ServiceException;

    /**
     * Find film by name film.
     *
     * @param name     the name
     * @param language the language
     * @return the film
     * @throws ServiceException the service exception
     */
    Film findFilmByName(String name, String language) throws ServiceException;

    /**
     * Find film by id film.
     *
     * @param id       the id
     * @param language the language
     * @return the film
     * @throws ServiceException the service exception
     */
    Film findFilmById(long id, String language) throws ServiceException;

    /**
     * Find info by id film info.
     *
     * @param filmId   the film id
     * @param language the language
     * @return the film info
     * @throws ServiceException the service exception
     */
    FilmInfo findInfoById(long filmId, String language) throws ServiceException;

    /**
     * Create film boolean.
     *
     * @param description    the description
     * @param yearOfCreation the year of creation
     * @param genre          the genre
     * @param link           the link
     * @param filmName       the film name
     * @param language       the language
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean createFilm(String description, String yearOfCreation,
                       String genre, String link, String filmName, String language) throws ServiceException;

    /**
     * Update film film.
     *
     * @param id            the id
     * @param filmNameRu    the film name ru
     * @param descriptionRu the description ru
     * @param genreRu       the genre ru
     * @param language      the language
     * @param linkRu        the link ru
     * @return the film
     * @throws ServiceException the service exception
     */
    Film updateFilm(long id, String filmName, String description,
                    String genre, String language, String link) throws ServiceException;

    /**
     * Update avatar ru.
     *
     * @param filmName the film name
     * @param avatarRu the avatar ru
     * @param language the language
     * @throws ServiceException the service exception
     */
    void updateAvatarRu(String filmName, String avatarRu, String language) throws ServiceException;

    /**
     * Update avatar en.
     *
     * @param filmName the film name
     * @param avatarEn the avatar en
     * @param language the language
     * @throws ServiceException the service exception
     */
    void updateAvatarEn(String filmName, String avatarEn, String language) throws ServiceException;

    /**
     * Update info en film.
     *
     * @param filmId         the film id
     * @param filmNameEn     the film name en
     * @param descriptionEn  the description en
     * @param genreEn        the genre en
     * @param language       the language
     * @param linkEn         the link en
     * @param yearOfCreation the year of creation
     * @return the film
     * @throws ServiceException the service exception
     */
    Film updateInfoEn(long filmId, String filmNameEn, String descriptionEn,
                      String genreEn, String language, String linkEn, String yearOfCreation) throws ServiceException;

    /**
     * Find all films list.
     *
     * @param currentPage the current page
     * @param filmsOnPage the films on page
     * @param language    the language
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Film> findAllFilms(int currentPage, int filmsOnPage, String language) throws ServiceException;

    /**
     * Change active film film.
     *
     * @param film the film
     * @return the film
     * @throws ServiceException the service exception
     */
    Film changeActiveFilm(Film film) throws ServiceException;

    /**
     * Find film id by name long.
     *
     * @param filmName the film name
     * @return the long
     * @throws ServiceException the service exception
     */
    long findFilmIdByName(String filmName) throws ServiceException;

    /**
     * Find film by film name list.
     *
     * @param filmName the film name
     * @param language the language
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Film> findFilmByFilmName(String filmName, String language) throws ServiceException;

    /**
     * Find film by description list.
     *
     * @param description the description
     * @param language    the language
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Film> findFilmByDescription(String description, String language) throws ServiceException;

    /**
     * Find film by genre list.
     *
     * @param genre    the genre
     * @param language the language
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Film> findFilmByGenre(String genre, String language) throws ServiceException;

    /**
     * Find films list.
     *
     * @param searchContent the search content
     * @param typeOfSearch  the type of search
     * @param language      the language
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Film> findFilms(String searchContent, String typeOfSearch, String language) throws ServiceException;
}
