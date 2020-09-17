package by.epam.project.pool;


import by.epam.project.pool.exception.PoolException;
import by.epam.project.command.manager.MessageManager;
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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ConnectionCreator {
    public static final Logger LOGGER = LogManager.getLogger();
    private static ConnectionCreator instance;
    private static Lock lock = new ReentrantLock();
    private static AtomicBoolean instanceWasCreated = new AtomicBoolean();
    private static final Properties properties = new Properties();
    private static final String DATABASE_URL;
    private static final String PROPERTIES_FILE = "dataBase.properties";


    static {
        try(InputStream inputStream =
                     ConnectionCreator.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE))
            {
            properties.load(inputStream);
            String driverName = (String) properties.get("db.driver");
            Class.forName(driverName);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        DATABASE_URL = (String)properties.get("db.url");
    }
    private ConnectionCreator() {
    }
    public static ConnectionCreator getInstance() {
        if (!instanceWasCreated.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionCreator();
                    instanceWasCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }
    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, properties);
    }
    void deregisterDrivers() throws PoolException {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, MessageManager.getProperty("message.driversnotfound"),e);
                throw new PoolException(MessageManager.getProperty("message.driversnotfound"),e);
            }
        }
    }
}
