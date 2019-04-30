package by.epam.provider;

import by.epam.provider.exception.ProviderException;
import by.epam.provider.transaction.ConnectionPool;
import by.epam.provider.property.AppProperty;
import org.testng.annotations.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;


/**
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */

@Test
public class ConnectionPoolTest {

    @BeforeTest
    private void init() {
        AppProperty.setPropertiesPath("application.properties");
    }

    @DataProvider
    public Object[][] dataProviderConnectionPool() {
        return new Object[][]{
                {
                        10, 1
                },
                {
                        1, 1
                }
        };
    }

    @Test(dataProvider = "dataProviderConnectionPool")
    public void testConnectionPool(final int initCapacity,
                                   final int busyConnections)
            throws ProviderException {
        ConnectionPool.setInitCapacity(initCapacity);
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        connectionPool.pullConnection();
        assertEquals(connectionPool.processingConnections(), busyConnections);
    }

    @AfterTest
    private void exit() {

    }
}
