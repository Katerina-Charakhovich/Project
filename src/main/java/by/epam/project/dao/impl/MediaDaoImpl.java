package by.epam.project.dao.impl;


import by.epam.project.dao.MediaDao;
import by.epam.project.dao.exception.DaoException;
import by.epam.project.entity.impl.Film;
import by.epam.project.entity.impl.FilmInfo;
import by.epam.project.pool.ConnectionPool;
import by.epam.project.pool.exception.PoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MediaDaoImpl implements MediaDao {
    private static MediaDaoImpl instance;
    public static final Logger LOGGER = LogManager.getLogger();
    private static final String SQL_SELECT_BY_NAME =
            "SELECT id, film_name, real_name FROM testlogin.films WHERE film_name = ?";
    private static final String SQL_SELECT_ALL_UNDELETED_FILMS =
            "SELECT id, film_name, real_name  FROM testlogin.films ORDER BY id DESC LIMIT ?,?";
    private static final String SQL_SELECT_COUNT_OF_ROWS =
            "SELECT COUNT(id) FROM testlogin.films";
    private static final String SQL_SELECT_BY_ID =
            "SELECT id, film_name, real_name FROM testlogin.films WHERE id = ?";
    private static final String SQL_SELECT_INFO_BY_ID =
            "SELECT id, description, year_of_creation, genre, link, film_id FROM testlogin.films_info WHERE film_id = ?";
    private static final String SQL_SELECT_ALL_FILMS_INFO =
            "SELECT id, description, year_of_creation, genre, link, film_id FROM testlogin.films_info ORDER BY id DESC LIMIT ?,? ";

    private MediaDaoImpl() {
    }

    public static MediaDaoImpl getInstance() {
        if (instance == null) {
            instance = new MediaDaoImpl();
        }
        return instance;
    }

    @Override
    public Film findEntityById(long id) throws DaoException {
        return null;
    }

    @Override
    public boolean delete(Film film) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(long id) throws DaoException {
        return false;
    }

    @Override
    public boolean create(Film film) throws DaoException {
        return false;
    }

    @Override
    public Film update(Film film) throws DaoException {
        return null;
    }


    @Override
    public Film findFilmByName(String name) throws DaoException {
        Film film = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_NAME);

        ) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String realName = resultSet.getString("real_name");
                long id = resultSet.getLong("id");
                String filmName = resultSet.getString("file_name");
                        film = new Film(id, filmName, realName);
            }
        } catch (SQLException | PoolException e) {
            LOGGER.log(Level.ERROR, "Film not found", e);
            throw new DaoException("Film not Found", e);
        }
        return film;
    }

    @Override
    public List<Film> findAllUndeletedFilms(int currentPage, int filmsOnPage) throws DaoException {
        List<Film> undeletedFilms = new ArrayList<>();
        int start = currentPage * filmsOnPage - filmsOnPage;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_UNDELETED_FILMS);
        ) {
            statement.setInt(1,start);
            statement.setInt(2,filmsOnPage);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String realName = resultSet.getString("real_name");
                long id = resultSet.getLong("id");
                String filmName = resultSet.getString("film_name");
                undeletedFilms.add(new Film(id,filmName,realName));
            }
        } catch (SQLException | PoolException e) {
            LOGGER.log(Level.ERROR, "Films not found", e);
            throw new DaoException("Films not found", e);
        }
        return undeletedFilms;
    }

    @Override
    public int getNumberOfRows() throws DaoException {
        int numOfRows = 0;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_COUNT_OF_ROWS);
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                numOfRows = resultSet.getInt(1);
            }
        }catch (SQLException | PoolException e) {
                LOGGER.log(Level.ERROR, "Films not found", e);
                throw new DaoException("Films not found", e);
            }
        return numOfRows;
        }

    @Override
    public Film findFilmById(long id) throws DaoException {
        Film film = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID);
        ) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String realName = resultSet.getString("real_name");
                long filmId = resultSet.getLong("id");
                String filmName = resultSet.getString("file_name");
                film = new Film(filmId, filmName, realName);
            }
        } catch (SQLException | PoolException e) {
            LOGGER.log(Level.ERROR, "Film not found", e);
            throw new DaoException("Film not Found", e);
        }
        return film;
    }

    @Override
    public FilmInfo findInfoById(long filmId) throws DaoException {
        FilmInfo filmInfo = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_INFO_BY_ID);
        ) {
            statement.setLong(1, filmId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
              String description = resultSet.getString("description");
              int yearOfCreation = resultSet.getInt("year_of_creation");
              String genre = resultSet.getString("genre");
              String link = resultSet.getString("link");
              long idFilm = resultSet.getLong("film_id");
              filmInfo = new FilmInfo(description,yearOfCreation,genre,idFilm,link);
            }
        } catch (SQLException | PoolException e) {
            LOGGER.log(Level.ERROR, "Film not found", e);
            throw new DaoException("Film not Found", e);
        }
        return filmInfo;
    }
}

