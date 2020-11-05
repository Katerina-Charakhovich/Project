package by.epam.project.dao.impl;

import by.epam.project.dao.ColumnName;
import by.epam.project.dao.PurchasedFilmDao;
import by.epam.project.dao.DaoException;
import by.epam.project.entity.impl.Film;
import by.epam.project.entity.impl.FilmInfo;
import by.epam.project.entity.impl.PurchasedFilm;
import by.epam.project.entity.impl.User;
import by.epam.project.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.epam.project.dao.LocalizationHelper.buildLocalizedColumn;

public class PurchasedDaoImpl implements PurchasedFilmDao {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final PurchasedDaoImpl instance = new PurchasedDaoImpl();
    private static final String SQL_SELECT_ALL_FILMS_BY_USER =
            "SELECT f.film_id FROM testlogin.films f LEFT JOIN testlogin.users_films uf " +
                    "ON f.film_id = uf.film_id WHERE uf.id_user = ?";
    private static final String SQL_SELECT_BY_ID =
            "SELECT film_id FROM testlogin.users_films WHERE id_user = ?";
    private static final String SQL_INSERT_NEW_PURCHASED_FILM =
            "INSERT INTO testlogin.users_films (id_user, film_id) VALUES (?,?)";
    private static final String SQL_FIND_ALL_EN_INFO_ABOUT_PURCHASED_FILMS =
            "SELECT u.email, u.name_user, u.country,u.gender, f.film_name_en, fi.description_en, " +
                    "fi.year_of_creation, fi.genre_en, fi.link_en " +
                    "FROM testlogin.users u " +
                    "INNER JOIN testlogin.users_films uf ON u.id_user = uf.id_user " +
                    "INNER JOIN testlogin.films f ON f.film_id = uf.film_id " +
                    "INNER JOIN testlogin.films_info fi ON f.film_id = fi.film_id LIMIT ?,?";
    private static final String SQL_FIND_ALL_RU_INFO_ABOUT_PURCHASED_FILMS =
            "SELECT u.email, u.name_user, u.country,u.gender, f.film_name_ru, fi.description_ru, " +
                    "fi.year_of_creation, fi.genre_ru,fi.link_ru " +
                    "FROM testlogin.users u " +
                    "INNER JOIN testlogin.users_films uf ON u.id_user = uf.id_user " +
                    "INNER JOIN testlogin.films f ON f.film_id = uf.film_id " +
                    "INNER JOIN testlogin.films_info fi ON f.film_id = fi.film_id LIMIT ?,?";
    private static final String SQL_SELECT_COUNT_OF_ROWS =
            "SELECT COUNT(id_user) FROM testlogin.users_films";

    private static final String LANGUAGE_EN = "en";

    private PurchasedDaoImpl() {
    }

    public static PurchasedDaoImpl getInstance() {
        return instance;
    }

    @Override
    public PurchasedFilm findEntityById(long id) throws DaoException {
        PurchasedFilm purchasedFilm = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long film_id = resultSet.getLong(ColumnName.FILM_ID);
                purchasedFilm = new PurchasedFilm(id, film_id);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Purchased film not found", e);
            throw new DaoException("Purchased film not found", e);
        }
        return purchasedFilm;
    }

    @Override
    public boolean create(PurchasedFilm purchasedFilm) throws DaoException {
        boolean result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_NEW_PURCHASED_FILM)) {
            statement.setLong(1, purchasedFilm.getIdUser());
            statement.setLong(2, purchasedFilm.getFilmId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Creation failed", e);
            throw new DaoException("Creation failed", e);
        }
        return result;
    }

    @Override
    public PurchasedFilm update(PurchasedFilm purchasedFilm) throws DaoException {
        throw new UnsupportedOperationException("This operation is not supported");
    }

    @Override
    public List<Long> findAllPurchasedFilmsByUser(long userId) throws DaoException {
        List<Long> films = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_FILMS_BY_USER)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long filmId = resultSet.getLong(ColumnName.FILM_ID);
                films.add(filmId);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Films not found", e);
            throw new DaoException("Films not found", e);
        }
        return films;
    }

    @Override
    public Map<User, Film> findAllInfoAboutUsersAndPurchasedFilms(int currentPage,
                                                                  int purchasedFilmsOnPage,
                                                                  String language) throws DaoException {
        Map<User, Film> purchased = new HashMap<>();
        int start = currentPage * purchasedFilmsOnPage - purchasedFilmsOnPage;
        String query = language.equals(LANGUAGE_EN) ? SQL_FIND_ALL_EN_INFO_ABOUT_PURCHASED_FILMS
                : SQL_FIND_ALL_RU_INFO_ABOUT_PURCHASED_FILMS;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, start);
            statement.setInt(2, purchasedFilmsOnPage);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String filmName = resultSet.getString(buildLocalizedColumn(ColumnName.FILM_NAME, language));
                String genre = resultSet.getString(buildLocalizedColumn(ColumnName.FILM_GENRE, language));
                String description = resultSet.getString(buildLocalizedColumn(ColumnName.FILM_DESCRIPTION, language));
                String yearOfCreation = resultSet.getString(ColumnName.FILM_YEAR_OF_CREATION);
                String link = resultSet.getString(buildLocalizedColumn(ColumnName.FILM_LINK, language));
                String userEmail = resultSet.getString(ColumnName.EMAIL);
                String userName = resultSet.getString(ColumnName.NAME_USER);
                String gender = resultSet.getString(ColumnName.GENDER);
                String country = resultSet.getString(ColumnName.COUNTRY);
                User user = new User();
                user.setEmail(userEmail);
                user.setUserGender(gender);
                user.setCountry(country);
                user.setName(userName);
                Film film = new Film();
                film.setFilmName(filmName);
                film.setFilmInfo(new FilmInfo(description, yearOfCreation, genre, film.getFilmId(), link));
                purchased.put(user, film);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Films not found", e);
            throw new DaoException("Films not found", e);
        }
        return purchased;
    }

    @Override
    public int calculateNumberOfRowsByPurchasedFilms() throws DaoException {
        int numOfRows = 0;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_COUNT_OF_ROWS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                numOfRows = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Purchased film not found", e);
            throw new DaoException("Purchased film not found", e);
        }
        return numOfRows;
    }
}