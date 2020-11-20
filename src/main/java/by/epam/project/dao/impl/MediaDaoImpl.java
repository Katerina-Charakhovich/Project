package by.epam.project.dao.impl;


import by.epam.project.dao.ColumnName;
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

import static by.epam.project.dao.LocalizationHelper.buildLocalizedColumn;
import static by.epam.project.dao.SearchHelper.buildAppealForDataBase;

/**
 * The class contains query`s processing to the database for the movie
 */
public class MediaDaoImpl implements MediaDao {
    private static MediaDaoImpl instance;
    public static final Logger LOGGER = LogManager.getLogger();
    private static final String SQL_SELECT_BY_NAME_EN =
            "SELECT film_id, film_name_en, film_avatar_en, active FROM testlogin.films " +
                    "WHERE film_name_en = ?";
    private static final String SQL_SELECT_BY_NAME_RU =
            "SELECT film_id, film_name_ru, film_avatar_ru, active FROM testlogin.films " +
                    "WHERE film_name_ru = ?";
    private static final String SQL_AVATAR_RU_BY_NAME_EN =
            "SELECT film_avatar_ru FROM testlogin.films " +
                    "WHERE film_name_en = ?";
    private static final String SQL_SELECT_ALL_ACTIVE_FILMS_EN =
            "SELECT film_id, film_name_en, film_avatar_en, active  FROM testlogin.films " +
                    "WHERE active=? ORDER BY film_id DESC LIMIT ?,?";
    private static final String SQL_SELECT_ALL_ACTIVE_FILMS_RU =
            "SELECT film_id, film_name_ru, film_avatar_ru, active  FROM testlogin.films " +
                    "WHERE active=? ORDER BY film_id DESC LIMIT ?,?";
    private static final String SQL_SELECT_ALL_FILMS_EN =
            "SELECT film_id, film_name_en, film_avatar_en, active  FROM testlogin.films " +
                    "ORDER BY film_id DESC LIMIT ?,?";
    private static final String SQL_SELECT_ALL_FILMS_RU =
            "SELECT film_id, film_name_ru, film_avatar_ru, active FROM testlogin.films " +
                    "ORDER BY film_id DESC LIMIT ?,?";
    private static final String SQL_SELECT_COUNT_OF_ACTIVE_ROWS =
            "SELECT COUNT(film_id) FROM testlogin.films WHERE active = ?";
    private static final String SQL_SELECT_COUNT_OF_ALL_ROWS =
            "SELECT COUNT(film_id) FROM testlogin.films";
    private static final String SQL_SELECT_BY_ID_EN =
            "SELECT film_id, film_name_en, film_avatar_en FROM testlogin.films WHERE film_id = ?";
    private static final String SQL_SELECT_BY_ID_RU =
            "SELECT film_id, film_name_ru, film_avatar_ru FROM testlogin.films WHERE film_id = ?";
    private static final String SQL_SELECT_ACTIVE_FILM_BY_ID_EN =
            "SELECT film_id, film_name_en, film_avatar_en FROM testlogin.films WHERE film_id = ? " +
                    "AND active = ?";
    private static final String SQL_SELECT_ACTIVE_FILM_BY_ID_RU =
            "SELECT film_id, film_name_ru, film_avatar_ru FROM testlogin.films WHERE film_id = ? " +
                    "AND active = ?";
    private static final String SQL_SELECT_INFO_BY_ID_EN =
            "SELECT id_films_info, description_en, year_of_creation, genre_en, link_en, film_id " +
                    "FROM testlogin.films_info WHERE film_id = ?";
    private static final String SQL_SELECT_INFO_BY_ID_RU =
            "SELECT id_films_info, description_ru, year_of_creation, genre_ru, link_ru, film_id " +
                    "FROM testlogin.films_info WHERE film_id = ?";
    private static final String INSERT_NEW_FILM =
            "INSERT INTO testlogin.films(film_name_en) VALUES(?)";
    private static final String UPDATE_NEW_FILM_RU =
            "UPDATE testlogin.films SET film_name_ru = ? WHERE film_id = ?";
    private static final String SQL_SELECT_COUNT_FILMS =
            "SELECT count(*) FROM testlogin.films WHERE film_name_en = ?";
    private static final String INSERT_NEW_FILM_INFO =
            "INSERT INTO testlogin.films_info(description_en,year_of_creation, " +
                    "genre_en, link_en, film_id) VALUES(?,?,?,?,?)";
    private static final String UPDATE_FILM_INFO_RU =
            "UPDATE testlogin.films_info SET description_ru = ?, genre_ru= ?, link_ru= ? " +
                    "WHERE film_id = ?";
    private static final String UPDATE_FILM_AVATAR_RU =
            "UPDATE testlogin.films SET film_avatar_ru = ? WHERE film_name_en = ?";
    private static final String UPDATE_FILM_AVATAR_EN =
            "UPDATE testlogin.films SET film_avatar_en = ? WHERE film_name_en = ?";
    private static final String CHANGE_NAME_FILM_EN =
            "UPDATE testlogin.films SET film_name_en = ? WHERE film_id = ?";
    private static final String CHANGE_FILM_INFO_EN =
            "UPDATE testlogin.films_info SET description_en = ?, genre_en= ?, link_en= ?, " +
                    "year_of_creation = ? WHERE film_id = ?";
    private static final String SQL_CHANGE_ACTIVE_FILM =
            "UPDATE testlogin.films SET active = ? WHERE film_id = ?";
    private static final String SQL_SELECT_FILM_ID_BY_FILM_NAME =
            "SELECT film_id FROM testlogin.films  WHERE film_name = ?";
    private static final String SQL_SELECT_LIKE_NAME_EN =
            "SELECT film_id, film_name_en, film_avatar_en, active FROM testlogin.films " +
                    "WHERE film_name_en LIKE ? AND active = ?";
    private static final String SQL_SELECT_LIKE_NAME_RU =
            "SELECT film_id, film_name_ru, film_avatar_ru, active FROM testlogin.films " +
                    "WHERE film_name_ru LIKE ? AND active = ?";
    private static final String LANGUAGE_EN = "en";
    private static final String LANGUAGE_RU = "ru";
    private static final String SQL_SELECT_INFO_LIKE_DESCRIPTION_EN =
            "SELECT id_films_info, description_en, year_of_creation, genre_en, link_en, " +
                    "film_id FROM testlogin.films_info WHERE description_en LIKE ?";
    private static final String SQL_SELECT_INFO_LIKE_DESCRIPTION_RU =
            "SELECT id_films_info, description_ru, year_of_creation, genre_ru, link_ru, " +
                    "film_id FROM testlogin.films_info WHERE description_ru LIKE ?";
    private static final String SQL_SELECT_INFO_LIKE_GENRE_EN =
            "SELECT id_films_info, description_en, year_of_creation, genre_en, link_en, " +
                    "film_id FROM testlogin.films_info WHERE genre_en LIKE ?";
    private static final String SQL_SELECT_INFO_LIKE_GENRE_RU =
            "SELECT id_films_info, description_ru, year_of_creation, genre_ru, link_ru, " +
                    "film_id FROM testlogin.films_info WHERE genre_ru LIKE ?";

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
        throw new UnsupportedOperationException("This operation is not supported");
    }

    @Override
    public boolean create(Film film) throws DaoException {
        boolean result;
        FilmInfo filmInfo = new FilmInfo(film.getFilmInfo().getDescription(),
                film.getFilmInfo().getYearOfCreation(),
                film.getFilmInfo().getGenre(), film.getFilmInfo().getLink());
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(INSERT_NEW_FILM);
                 PreparedStatement statementForUpdateFilmInfo = connection.prepareStatement(INSERT_NEW_FILM_INFO)) {
                connection.setAutoCommit(false);
                statement.setString(1, film.getFilmName());
                statement.executeUpdate();
                film = findFilmByName(film.getFilmName(), LANGUAGE_EN);
                statementForUpdateFilmInfo.setString(1, filmInfo.getDescription());
                statementForUpdateFilmInfo.setString(2, filmInfo.getYearOfCreation());
                statementForUpdateFilmInfo.setString(3, filmInfo.getGenre());
                statementForUpdateFilmInfo.setString(4, filmInfo.getLink());
                statementForUpdateFilmInfo.setLong(5, film.getFilmId());
                result = statementForUpdateFilmInfo.executeUpdate() > 0;
                connection.commit();
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, "Film creation is failed", e);
                connection.rollback();
                throw new DaoException("Film creation is failed", e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException | PoolException e) {
            LOGGER.log(Level.ERROR, "Connection issues", e);
            throw new DaoException("Connection issues", e);
        }
        return result;
    }

    @Override
    public Film update(Film film) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_NEW_FILM_RU);
                 PreparedStatement statementForUpdateFilmInfo = connection.prepareStatement(UPDATE_FILM_INFO_RU)) {
                connection.setAutoCommit(false);
                statement.setLong(2, film.getFilmId());
                statement.setString(1, film.getFilmName());
                statement.executeUpdate();
                statementForUpdateFilmInfo.setLong(4, film.getFilmId());
                statementForUpdateFilmInfo.setString(1, film.getFilmInfo().getDescription());
                statementForUpdateFilmInfo.setString(2, film.getFilmInfo().getGenre());
                statementForUpdateFilmInfo.setString(3, film.getFilmInfo().getLink());
                statementForUpdateFilmInfo.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, "Film update is failed", e);
                connection.rollback();
                throw new DaoException("Film update is failed", e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException | PoolException e) {
            LOGGER.log(Level.ERROR, "Connection issues", e);
            throw new DaoException("Connection issues", e);
        }
        return film;
    }


    @Override
    public Film findFilmByName(String name, String language) throws DaoException {
        Film film = null;
        FilmInfo filmInfo;
        String query = language.equals(LANGUAGE_EN) ? SQL_SELECT_BY_NAME_EN : SQL_SELECT_BY_NAME_RU;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String filmAvatar = resultSet.getString(buildLocalizedColumn(ColumnName.FILM_AVATAR,
                            language));
                    long id = resultSet.getLong(ColumnName.FILM_ID);
                    String filmName = resultSet.getString(buildLocalizedColumn(ColumnName.FILM_NAME,
                            language));
                    boolean active = resultSet.getBoolean(ColumnName.ACTIVE);
                    filmInfo = findInfoById(id, language);
                    film = new Film(id, filmName, filmAvatar, filmInfo);
                    film.setActive(active);
                }
            }
        } catch (SQLException | PoolException e) {
            throw new DaoException("Film not Found", e);
        }
        return film;
    }

    @Override
    public String findAvatarRuByFilmNameEn(String name) throws DaoException {
        String filmAvatar = "";
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_AVATAR_RU_BY_NAME_EN)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    filmAvatar = resultSet.getString(1);
                }
            }
        } catch (SQLException | PoolException e) {
            throw new DaoException("Film not Found", e);
        }
        return filmAvatar;
    }

    @Override
    public List<Film> findAllActiveFilms(int currentPage, int filmsOnPage, String language,
                                         boolean active) throws DaoException {
        List<Film> undeletedFilms = new ArrayList<>();
        FilmInfo filmInfo;
        int start = currentPage * filmsOnPage - filmsOnPage;
        String query = language.equals(LANGUAGE_EN) ? SQL_SELECT_ALL_ACTIVE_FILMS_EN
                : SQL_SELECT_ALL_ACTIVE_FILMS_RU;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(2, start);
            statement.setInt(3, filmsOnPage);
            statement.setBoolean(1, active);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String filmAvatar = resultSet.getString(buildLocalizedColumn(ColumnName.FILM_AVATAR,
                            language));
                    long filmId = resultSet.getLong(ColumnName.FILM_ID);
                    boolean filmActive = resultSet.getBoolean(ColumnName.ACTIVE);
                    String filmName = resultSet.getString(buildLocalizedColumn(ColumnName.FILM_NAME,
                            language));
                    filmInfo = findInfoById(filmId, language);
                    Film film = new Film(filmId, filmName, filmAvatar, filmInfo);
                    film.setActive(filmActive);
                    undeletedFilms.add(film);
                }
            }
        } catch (SQLException | PoolException e) {
            throw new DaoException("Films not found", e);
        }
        return undeletedFilms;
    }

    @Override
    public int calculateNumberOfActiveRows() throws DaoException {
        int numOfRows = 0;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_COUNT_OF_ACTIVE_ROWS)) {
            statement.setBoolean(1, true);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    numOfRows = resultSet.getInt(1);
                }
            }
        } catch (SQLException | PoolException e) {
            throw new DaoException("Films not found", e);
        }
        return numOfRows;
    }

    @Override
    public int calculateNumberOfAllRows() throws DaoException {
        int numOfRows = 0;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_COUNT_OF_ALL_ROWS)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    numOfRows = resultSet.getInt(1);
                }
            }
        } catch (SQLException | PoolException e) {
            throw new DaoException("Films not found", e);
        }
        return numOfRows;
    }

    @Override
    public Film findFilmById(long id, String language) throws DaoException {
        Film film = null;
        FilmInfo filmInfo;
        String query = language.equals(LANGUAGE_EN) ? SQL_SELECT_BY_ID_EN : SQL_SELECT_BY_ID_RU;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String filmAvatar = resultSet.getString(buildLocalizedColumn(ColumnName.FILM_AVATAR,
                            language));
                    long filmId = resultSet.getLong(ColumnName.FILM_ID);
                    String filmName = resultSet.getString(buildLocalizedColumn(ColumnName.FILM_NAME,
                            language));
                    filmInfo = findInfoById(id, language);
                    film = new Film(filmId, filmName, filmAvatar, filmInfo);
                }
            }
        } catch (SQLException | PoolException e) {
            throw new DaoException("Film not Found", e);
        }
        return film;
    }

    @Override
    public Film findActiveFilmById(long id, String language) throws DaoException {
        Film film = null;
        FilmInfo filmInfo;
        String query = language.equals(LANGUAGE_EN) ? SQL_SELECT_ACTIVE_FILM_BY_ID_EN
                : SQL_SELECT_ACTIVE_FILM_BY_ID_RU;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.setBoolean(2, true);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String filmAvatar = resultSet.getString(buildLocalizedColumn(ColumnName.FILM_AVATAR,
                            language));
                    long filmId = resultSet.getLong(ColumnName.FILM_ID);
                    String filmName = resultSet.getString(buildLocalizedColumn(ColumnName.FILM_NAME,
                            language));
                    filmInfo = findInfoById(id, language);
                    film = new Film(filmId, filmName, filmAvatar, filmInfo);
                }
            }
        } catch (SQLException | PoolException e) {
            throw new DaoException("Film not Found", e);
        }
        return film;
    }

    @Override
    public FilmInfo findInfoById(long filmId, String language) throws DaoException {
        FilmInfo filmInfo = null;
        String query = language.equals(LANGUAGE_EN) ? SQL_SELECT_INFO_BY_ID_EN
                : SQL_SELECT_INFO_BY_ID_RU;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, filmId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String description = resultSet.getString(buildLocalizedColumn(ColumnName.FILM_DESCRIPTION,
                            language));
                    String yearOfCreation = resultSet.getString(ColumnName.FILM_YEAR_OF_CREATION);
                    String genre = resultSet.getString(buildLocalizedColumn(ColumnName.FILM_GENRE, language));
                    String link = resultSet.getString(buildLocalizedColumn(ColumnName.FILM_LINK, language));
                    long idFilm = resultSet.getLong(ColumnName.FILM_INFO_FILM_ID);
                    filmInfo = new FilmInfo(description, yearOfCreation, genre, idFilm, link);
                }
            }
        } catch (SQLException | PoolException e) {
            throw new DaoException("Film not Found", e);
        }
        return filmInfo;
    }

    @Override
    public boolean isFilmExist(String filmName) throws DaoException {
        boolean result = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_COUNT_FILMS)) {
            statement.setString(1, filmName);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int countFilms = resultSet.getInt(1);
                    if (countFilms > 0) {
                        result = true;
                    }
                }
            }
        } catch (SQLException | PoolException e) {
            throw new DaoException("User not found", e);
        }
        return result;
    }

    @Override
    public void updateAvatarRu(String avatar, String filmName) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_FILM_AVATAR_RU);
        ) {
            statement.setString(1, avatar);
            statement.setString(2, filmName);
            statement.executeUpdate();

        } catch (SQLException | PoolException e) {
            throw new DaoException("Avatar has not been updated", e);
        }
    }

    @Override
    public Film updateAvatarEn(Film film) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_FILM_AVATAR_EN);
        ) {
            statement.setString(1, film.getFilmAvatar());
            statement.setString(2, film.getFilmName());
            statement.executeUpdate();

        } catch (SQLException | PoolException e) {
            throw new DaoException("Avatar has not been updated", e);
        }
        return film;
    }

    @Override
    public Film updateInfoEn(Film film) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(CHANGE_NAME_FILM_EN);
                 PreparedStatement statementForUpdateFilmInfo = connection.prepareStatement(CHANGE_FILM_INFO_EN)) {
                connection.setAutoCommit(false);
                statement.setString(1, film.getFilmName());
                statement.setLong(2, film.getFilmId());
                statement.executeUpdate();
                statementForUpdateFilmInfo.setString(1, film.getFilmInfo().getDescription());
                statementForUpdateFilmInfo.setString(2, film.getFilmInfo().getGenre());
                statementForUpdateFilmInfo.setString(3, film.getFilmInfo().getLink());
                statementForUpdateFilmInfo.setString(4, film.getFilmInfo().getYearOfCreation());
                statementForUpdateFilmInfo.setLong(5, film.getFilmId());
                statementForUpdateFilmInfo.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, "Film en update is failed", e);
                connection.rollback();
                throw new DaoException("Film en update is failed", e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException | PoolException e) {
            LOGGER.log(Level.ERROR, "Connection issues", e);
            throw new DaoException("Connection issues", e);
        }
        return film;
    }

    @Override
    public List<Film> findAllFilms(int currentPage, int filmsOnPage, String language) throws DaoException {
        List<Film> undeletedFilms = new ArrayList<>();
        FilmInfo filmInfo;
        int start = currentPage * filmsOnPage - filmsOnPage;
        String query = language.equals(LANGUAGE_EN) ? SQL_SELECT_ALL_FILMS_EN : SQL_SELECT_ALL_FILMS_RU;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, start);
            statement.setInt(2, filmsOnPage);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String filmAvatar = resultSet.getString(buildLocalizedColumn(ColumnName.FILM_AVATAR,
                            language));
                    long filmId = resultSet.getLong(ColumnName.FILM_ID);
                    boolean active = resultSet.getBoolean(ColumnName.ACTIVE);
                    String filmName = resultSet.getString(buildLocalizedColumn(ColumnName.FILM_NAME,
                            language));
                    filmInfo = findInfoById(filmId, language);
                    Film film = new Film(filmId, filmName, filmAvatar, filmInfo);
                    film.setActive(active);
                    undeletedFilms.add(film);
                }
            }
        } catch (SQLException | PoolException e) {
            throw new DaoException("Films not found", e);
        }
        return undeletedFilms;
    }

    @Override
    public Film changeActiveFilm(Film film) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CHANGE_ACTIVE_FILM);
        ) {
            statement.setBoolean(1, film.isActive());
            statement.setLong(2, film.getFilmId());
            statement.executeUpdate();
        } catch (SQLException | PoolException e) {
            throw new DaoException("Info not updated", e);
        }
        return film;
    }

    @Override
    public long findFilmIdByFilmName(String filmName) throws DaoException {
        long filmId = 0;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_FILM_ID_BY_FILM_NAME);
        ) {
            statement.setString(1, filmName);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    filmId = resultSet.getLong(ColumnName.FILM_ID);
                }
            }
        } catch (SQLException | PoolException e) {
            throw new DaoException("Film id not found", e);
        }
        return filmId;
    }

    @Override
    public List<Film> findFilmByFilmName(String filmName, String language) throws DaoException {
        Film film;
        List<Film> films = new ArrayList<>();
        FilmInfo filmInfo;
        String query = language.equals(LANGUAGE_EN) ? SQL_SELECT_LIKE_NAME_EN : SQL_SELECT_LIKE_NAME_RU;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, buildAppealForDataBase(filmName));
            statement.setBoolean(2, true);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String filmAvatar = resultSet.getString(buildLocalizedColumn(ColumnName.FILM_AVATAR,
                            language));
                    long id = resultSet.getLong(ColumnName.FILM_ID);
                    String name = resultSet.getString(buildLocalizedColumn(ColumnName.FILM_NAME, language));
                    boolean active = resultSet.getBoolean(ColumnName.ACTIVE);
                    filmInfo = findInfoById(id, language);
                    film = new Film(id, name, filmAvatar, filmInfo);
                    film.setActive(active);
                    films.add(film);
                }
            }
        } catch (SQLException | PoolException e) {
            throw new DaoException("Film not Found", e);
        }
        return films;
    }

    @Override
    public List<Film> findFilmByDescription(String description, String language) throws DaoException {
        Film film;
        List<Film> films = new ArrayList<>();
        String query = language.equals(LANGUAGE_EN) ? SQL_SELECT_INFO_LIKE_DESCRIPTION_EN
                : SQL_SELECT_INFO_LIKE_DESCRIPTION_RU;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, buildAppealForDataBase(description));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String descriptionFilm = resultSet.getString(buildLocalizedColumn(ColumnName.FILM_DESCRIPTION,
                            language));
                    long id = resultSet.getLong(ColumnName.FILM_ID);
                    String genre = resultSet.getString(buildLocalizedColumn(ColumnName.FILM_GENRE, language));
                    String yearOfCreation = resultSet.getString(ColumnName.FILM_YEAR_OF_CREATION);
                    String link = resultSet.getString(buildLocalizedColumn(ColumnName.FILM_LINK, language));
                    film = findActiveFilmById(id, language);
                    if (film != null) {
                        film.setFilmInfo(new FilmInfo(descriptionFilm, yearOfCreation, genre, link));
                        films.add(film);
                    }
                }
            }
        } catch (SQLException | PoolException e) {
            throw new DaoException("Film not Found", e);
        }
        return films;
    }

    @Override
    public List<Film> findFilmByGenre(String genre, String language) throws DaoException {
        Film film;
        List<Film> films = new ArrayList<>();
        String query = language.equals(LANGUAGE_EN) ? SQL_SELECT_INFO_LIKE_GENRE_EN
                : SQL_SELECT_INFO_LIKE_GENRE_RU;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, buildAppealForDataBase(genre));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String descriptionFilm = resultSet.getString(buildLocalizedColumn(ColumnName.FILM_DESCRIPTION,
                            language));
                    long id = resultSet.getLong(ColumnName.FILM_ID);
                    String genreFilm = resultSet.getString(buildLocalizedColumn(ColumnName.FILM_GENRE,
                            language));
                    String yearOfCreation = resultSet.getString(ColumnName.FILM_YEAR_OF_CREATION);
                    String link = resultSet.getString(buildLocalizedColumn(ColumnName.FILM_LINK, language));
                    film = findActiveFilmById(id, language);
                    if (film != null) {
                        film.setFilmInfo(new FilmInfo(descriptionFilm, yearOfCreation, genreFilm, link));
                        films.add(film);
                    }
                }
            }
        } catch (SQLException | PoolException e) {
            throw new DaoException("Film not Found", e);
        }
        return films;
    }
}


