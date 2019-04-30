package by.epam.provider.transaction;

import by.epam.provider.exception.ProviderException;
import by.epam.provider.exception.ProviderRuntimeException;
import by.epam.provider.property.AppProperty;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Deque;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.locks.ReentrantLock;


@Log4j2
public class ConnectionPool {


    private static volatile ConnectionPool instance;

    @Setter
    private static int initCapacity = 10;

    private AppProperty appProperty = AppProperty.getInstance();

    private String dbURL;

    private static final String DB_LOGIN
            = AppProperty.getInstance().getDbLogin();

    private static final String DB_PASSWORD
            = AppProperty.getInstance().getDbPassword();

    private static final String DB_DRIVER
            = AppProperty.getInstance().getDbDriver();

    private Deque<Connection> busyConnections = new ConcurrentLinkedDeque<>();

    private Deque<Connection> freeConnections = new ConcurrentLinkedDeque<>();


    public static ConnectionPool getInstance() {
       // ReentrantLock locker = new ReentrantLock();
        try {

            if (instance == null) {
                //locker.lock();
                if (instance == null) {
                    instance = new ConnectionPool(initCapacity);
                }
            }
            return instance;
        } catch (ProviderException e) {
            log.fatal("Cannot create pullConnection pool");
            throw new ProviderRuntimeException(e);
        } finally {
            //locker.unlock();
        }
    }

    private ConnectionPool() {
        dbURL = "jdbc:mysql://" + appProperty.getDbDomain() + "/"
                + appProperty.getDbServerName();
    }

    private ConnectionPool(final int initCapacity) throws ProviderException {
        dbURL = "jdbc:mysql://" + appProperty.getDbDomain() + "/"
                + appProperty.getDbServerName();

        log.debug(dbURL);
        try {
            Class.forName(DB_DRIVER).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ProviderException(e);
        } catch (ClassNotFoundException e) {
            throw new ProviderException(
                    "Cannot load driver. " + e.getMessage(), e);
        }

        for (int i = 0; i < initCapacity; i++) {
            Connection connection = createConnection();
            freeConnections.add(connection);
        }
    }

    public Connection pullConnection() throws ProviderException {
        log.debug("Get pullConnection");
        Connection connection;
        if (freeConnections.isEmpty()) {
            connection = createConnection();
            busyConnections.add(connection);
        } else {
            connection = freeConnections.poll();
            busyConnections.add(connection);
        }
        log.trace("Empty connections: " + freeConnections.size());
        log.trace("Busy connections: " + busyConnections.size());
        try {
            if (!connection.isClosed()) {
                return connection;
            } else {
                throw new ProviderException("Connection is closed. ");
            }
        } catch (SQLException e) {
            throw new ProviderException(e);
        }
    }

    public void pushConnection(Connection c) {
        log.debug("Return pullConnection");
        if (busyConnections.remove(c)) {
            freeConnections.add(c);
        } else {
            log.warn("There is no that pullConnection in busy connections. ");
        }
    }

    public int processingConnections() {
        return busyConnections.size();
    }


    private Connection createConnection() throws ProviderException {

        try {
            Connection connection = DriverManager
                    .getConnection(dbURL, DB_LOGIN, DB_PASSWORD);
            log.debug("Add a new pullConnection to pullConnection pool.");
            return connection;
        } catch (SQLException e) {
            log.error("");
            throw new ProviderException(
                    "Cannot create a new pullConnection with db. "
                            + e.getMessage(), e);
        }
    }

    public void closeConnections() {
        try {
            for (Connection c : busyConnections) {
                c.close();
            }
            for (Connection c : freeConnections) {
                c.close();
            }
        } catch (SQLException e) {
            log.error("Cannot close connections.");
            throw new ProviderRuntimeException(e);
        }


        log.info("All connections have been closed. ");
    }
}
