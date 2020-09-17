package by.epam.project.pool;

import by.epam.project.pool.exception.PoolException;
import by.epam.project.command.manager.MessageManager;
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
    private static AtomicBoolean instanceWasCreated = new AtomicBoolean();
    private BlockingQueue<ProxyConnection> freeConnections;
    private BlockingQueue<ProxyConnection> givenAwayConnections;
    private static final int DEFAULT_POOL_SIZE = 32;
    public static final Logger LOGGER = LogManager.getLogger();


    private ConnectionPool() {
        freeConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        givenAwayConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
    }

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

    public Connection getConnection() throws SQLException, PoolException {
        Connection connection = ConnectionCreator.getInstance().createConnection();
        try {
            for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
                connection = freeConnections.take();
                givenAwayConnections.offer(new ProxyConnection(connection));
            }
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR, MessageManager.getProperty("message.getconnection"), e);
            throw new PoolException(MessageManager.getProperty("message.getconnection"), e);
        }
        return connection;
    }

    public void releaseConnection(ProxyConnection connection) {
        givenAwayConnections.remove(connection);
        freeConnections.offer(connection);
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
            LOGGER.log(Level.ERROR, MessageManager.getProperty("message.destroypool"), e);
            throw new PoolException(MessageManager.getProperty("message.destroypool"), e);
        }
        ConnectionCreator.getInstance().deregisterDrivers();
    }
}
