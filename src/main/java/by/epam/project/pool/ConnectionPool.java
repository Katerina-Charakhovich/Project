package by.epam.project.pool;

import by.epam.project.pool.exception.PoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static ConnectionPool instance;
    private static Lock lock = new ReentrantLock();
    private static final AtomicBoolean instanceWasCreated = new AtomicBoolean();
    private BlockingQueue<ProxyConnection> freeConnections;
    private BlockingQueue<ProxyConnection> givenAwayConnections;
    private static final int DEFAULT_POOL_SIZE = 32;
    public static final Logger LOGGER = LogManager.getLogger();


    private ConnectionPool() throws SQLException {
        freeConnections = init();
        givenAwayConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
    }

    private BlockingQueue<ProxyConnection> init() throws SQLException {
        freeConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        Connection connection = ConnectionCreator.getInstance().createConnection();
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            freeConnections.offer(new ProxyConnection(connection));
        }
        return freeConnections;
    }

    public static ConnectionPool getInstance() throws PoolException {
        if (!instanceWasCreated.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    instanceWasCreated.set(true);
                }
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, "Impossible to create Connection", e);
                throw new PoolException("Impossible to create Connection", e);
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public Connection getConnection() throws PoolException {
        ProxyConnection connection;
        try {
            connection = freeConnections.take();
            givenAwayConnections.offer(connection);
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR, "Impossible to create Connection", e);
            throw new PoolException("Impossible to create Connection", e);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            if (connection instanceof ProxyConnection && givenAwayConnections.remove(connection)) {
                freeConnections.offer((ProxyConnection) connection);
            } else {
                LOGGER.log(Level.FATAL, "Connection is not a ProxyConnection");
            }
        }
    }

    public void destroyPool() throws PoolException {
        try {
            for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
                if (!freeConnections.isEmpty()) {
                    ProxyConnection connection = freeConnections.take();
                    connection.trueClose();
                }
            }
            for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
                if (!givenAwayConnections.isEmpty()) {
                    ProxyConnection connection = givenAwayConnections.take();
                    connection.trueClose();
                }
            }
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR, "Impossible to destroy pool", e);
            throw new PoolException("Impossible to destroy pool", e);
        }
        ConnectionCreator.getInstance().deregisterDrivers();
    }
}
