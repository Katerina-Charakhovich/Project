package by.epam.project.pool;

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

/**
 * The type Connection pool.
 */
public class ConnectionPool {

    public static final Logger LOGGER = LogManager.getLogger();
    private static ConnectionPool instance;
    private static Lock lock = new ReentrantLock();
    private static AtomicBoolean instanceWasCreated = new AtomicBoolean();
    private BlockingQueue<ProxyConnection> freeConnections;
    private BlockingQueue<ProxyConnection> givenAwayConnections;
    private static final int DEFAULT_POOL_SIZE = 32;


    private ConnectionPool() {
        freeConnections = init();
        givenAwayConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
    }

    private BlockingQueue<ProxyConnection> init() {
        freeConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        Connection connection;
        try {
            connection = ConnectionCreator.getInstance().createConnection();
            for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
                freeConnections.offer(new ProxyConnection(connection));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.FATAL, "ConnectionPool was not initialized", e);
            throw new RuntimeException("ConnectionPool was not initialized", e);
        }
        return freeConnections;
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ConnectionPool getInstance() {
        if (!instanceWasCreated.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    instanceWasCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    /**
     * Gets connection.
     *
     * @return the connection
     */
    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            givenAwayConnections.put(connection);
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR, "Impossible to create Connection", e);
        }
        return connection;
    }

    /**
     * Release connection.
     *
     * @param connection the connection
     * @throws PoolException the pool exception
     */
    public void releaseConnection(Connection connection) throws PoolException {
        if (connection != null) {
            if (connection instanceof ProxyConnection && givenAwayConnections.remove(connection)) {
                try {
                    freeConnections.put((ProxyConnection) connection);
                } catch (InterruptedException e) {
                    LOGGER.log(Level.FATAL, "Connection is not a ProxyConnection");
                    throw new PoolException("Connection is not a ProxyConnection", e);
                }
            }
        }
    }

    /**
     * Destroy pool.
     *
     * @throws PoolException the pool exception
     */
    public void destroyPool() throws PoolException {
        try {
            for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
                if (!freeConnections.isEmpty()) {
                    ProxyConnection connection = freeConnections.take();
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
