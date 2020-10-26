package by.epam.project.dao;

import by.epam.project.dao.exception.DaoException;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public interface BaseDao<T> {
    T findEntityById(long id) throws DaoException;

    boolean delete(T t) throws DaoException;

    boolean delete(long id) throws DaoException;

    boolean create(T t) throws DaoException;

    T update(T t) throws DaoException;
}
