package by.epam.project.dao;

import by.epam.project.dao.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public interface BaseDao<T> {
    public static final Logger LOGGER = LogManager.getLogger();

    T findEntityById(long id) throws DaoException;

    boolean delete(T t) throws DaoException;

    boolean delete(long id) throws DaoException;

    boolean create(T t) throws DaoException;

    T update(T t) throws DaoException;


    default void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, "Error during closing statement");
            }
        }
    }

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
