package by.epam.project.dao;

import by.epam.project.dao.exception.DaoException;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public interface BaseDao<Entity> {
    Entity findEntityById(long id) throws DaoException;

    boolean delete(Object o) throws DaoException;

    boolean delete(long id) throws DaoException;

    boolean create(Object o) throws DaoException;

    Entity update(Object o) throws DaoException;

    default void close(Statement statement) throws DaoException {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            throw new DaoException(" ", e);
        }
    }

    default void close(Connection connection) throws DaoException {
        try {
            if (connection != null) {
                connection.close(); // or connection return code to the pool
            }
        } catch (SQLException e) {
            throw new DaoException(" ", e);
        }
    }
}
