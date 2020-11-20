package by.epam.project.dao;

import by.epam.project.dao.exception.DaoException;

/**
 * The interface Base dao.
 *
 * @param <T> the type parameter
 */
public interface BaseDao<T> {

    /**
     * Find entity by id t.
     *
     * @param id the id
     * @return the t
     * @throws DaoException the dao exception
     */
    T findEntityById(long id) throws DaoException;

    /**
     * Create boolean.
     *
     * @param t the t
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean create(T t) throws DaoException;

    /**
     * Update t.
     *
     * @param t the t
     * @return the t
     * @throws DaoException the dao exception
     */
    T update(T t) throws DaoException;
}
