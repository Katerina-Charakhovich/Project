package by.epam.project.dao;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The interface Base dao.
 *
 * @param <T> the type parameter
 */
public interface BaseDao<T> {

    public static final Logger LOGGER = LogManager.getLogger();

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


    /**
     * Close.
     *
     * @param statement the statement
     */
    default void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, "Error during closing statement");
            }
        }
    }

    /**
     * Close.
     *
     * @param connection the connection
     */
    default void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, "Error during closing connection");
            }
        }
    }
}
