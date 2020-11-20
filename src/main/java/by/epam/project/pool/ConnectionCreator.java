package by.epam.project.pool;


import by.epam.project.pool.exception.PoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;


class ConnectionCreator {

    public static final Logger LOGGER = LogManager.getLogger();
    private static ConnectionCreator instance;
    private static final Properties properties = new Properties();
    private static final String DATABASE_URL;
    private static final String PROPERTIES_FILE = "dataBase.properties";
    private static final String DRIVER_NAME = "db.driver";
    private static final String URL = "db.url";

    static {
        try (InputStream inputStream =
                     ConnectionCreator.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            properties.load(inputStream);
            String driverName = (String) properties.get(DRIVER_NAME);
            Class.forName(driverName);
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.FATAL, "ConnectionCreator was not initialized", e);
            throw new ExceptionInInitializerError("ConnectionCreator was not initialized");
        }
        DATABASE_URL = (String) properties.get(URL);
    }

    private ConnectionCreator() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    static ConnectionCreator getInstance() {
        if (instance == null) {
            instance = new ConnectionCreator();
        }
        return instance;
    }

    /**
     * Create connection connection.
     *
     * @return the connection
     * @throws SQLException the sql exception
     */
    Connection createConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, properties);
    }

    /**
     * Deregister drivers.
     *
     * @throws PoolException the pool exception
     */
    void deregisterDrivers() throws PoolException {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, "Registered drivers are missing", e);
                throw new PoolException("Registered drivers are missing", e);
            }
        }
    }
}
