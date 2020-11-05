package by.epam.project.dao;

import by.epam.project.entity.impl.Film;
import by.epam.project.entity.impl.FilmInfo;

import java.util.List;

/**
 * The interface Media dao.
 */
public interface MediaDao extends BaseDao<Film> {
    /**
     * Find film by name film.
     *
     * @param name     the name
     * @param Language the language
     * @return the film
     * @throws DaoException the dao exception
     */
    Film findFilmByName(String name, String Language) throws DaoException;

    /**
     * Find all active films list.
     *
     * @param currentPage the current page
     * @param filmsOnPage the films on page
     * @param language    the language
     * @param active      the active
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Film> findAllActiveFilms(int currentPage, int filmsOnPage, String language,
                                  boolean active) throws DaoException;

    /**
     * Calculate number of rows int.
     *
     * @return the int
     * @throws DaoException the dao exception
     */
    int calculateNumberOfRows() throws DaoException;

    /**
     * Find film by id film.
     *
     * @param id       the id
     * @param language the language
     * @return the film
     * @throws DaoException the dao exception
     */
    Film findFilmById(long id, String language) throws DaoException;

    /**
     * Find active film by id film.
     *
     * @param id       the id
     * @param language the language
     * @return the film
     * @throws DaoException the dao exception
     */
    Film findActiveFilmById(long id, String language) throws DaoException;

    /**
     * Find info by id film info.
     *
     * @param filmId   the film id
     * @param language the language
     * @return the film info
     * @throws DaoException the dao exception
     */
    FilmInfo findInfoById(long filmId, String language) throws DaoException;

    /**
     * Is film exist boolean.
     *
     * @param filmName the film name
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean isFilmExist(String filmName) throws DaoException;

    /**
     * Update avatar ru film.
     *
     * @param film the film
     * @return the film
     * @throws DaoException the dao exception
     */
    Film updateAvatarRu(Film film) throws DaoException;

    /**
     * Update avatar en film.
     *
     * @param film the film
     * @return the film
     * @throws DaoException the dao exception
     */
    Film updateAvatarEn(Film film) throws DaoException;

    /**
     * Update info en film.
     *
     * @param film the film
     * @return the film
     */
    Film updateInfoEn(Film film);

    /**
     * Find all films list.
     *
     * @param currentPage the current page
     * @param filmsOnPage the films on page
     * @param language    the language
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Film> findAllFilms(int currentPage, int filmsOnPage, String language) throws DaoException;

    /**
     * Change active film film.
     *
     * @param film the film
     * @return the film
     * @throws DaoException the dao exception
     */
    Film changeActiveFilm(Film film) throws DaoException;

    /**
     * Find film id by film name long.
     *
     * @param filmName the film name
     * @return the long
     * @throws DaoException the dao exception
     */
    long findFilmIdByFilmName(String filmName) throws DaoException;

    /**
     * Find film by film name list.
     *
     * @param filmName the film name
     * @param language the language
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Film> findFilmByFilmName(String filmName, String language) throws DaoException;

    /**
     * Find film by description list.
     *
     * @param description the description
     * @param language    the language
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Film> findFilmByDescription(String description, String language) throws DaoException;

    /**
     * Find film by genre list.
     *
     * @param genre    the genre
     * @param language the language
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Film> findFilmByGenre(String genre, String language) throws DaoException;


}
