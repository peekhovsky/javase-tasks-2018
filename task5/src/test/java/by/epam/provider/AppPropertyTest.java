package by.epam.provider;

import by.epam.provider.property.AppProperty;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * #database
 * provider.db.serverName=test_db
 * provider.db.dmbs=test_domain
 * provider.db.login=test_login
 * provider.db.password=test_password
 * provider.db.driver=test.Driver
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
@Test
public class AppPropertyTest {

    @BeforeTest
    public void init() {
        AppProperty.setPropertiesPath("test_property.properties");
    }

    public void testAppPropertyLoadServerName() {
        String dbDriver = AppProperty.getInstance().getDbServerName();
        String expectedDbDriver = "test_db";
        assertEquals(expectedDbDriver, dbDriver);
    }

    public void testAppPropertyLoadDomain() {
        String dbDriver = AppProperty.getInstance().getDbDomain();
        String expectedDbDriver = "test_domain";
        assertEquals(expectedDbDriver, dbDriver);
    }

    public void testAppPropertyLoadDriver() {
        String dbDriver = AppProperty.getInstance().getDbDriver();
        String expectedDbDriver = "test.Driver";
        assertEquals(expectedDbDriver, dbDriver);
    }

    public void testAppPropertyLoadLogin() {
        String dbLogin = AppProperty.getInstance().getDbLogin();
        String expectedDbLogin = "test_login";
        assertEquals(expectedDbLogin, dbLogin);
    }

    public void testAppPropertyLoadPassword() {
        String dbPassword = AppProperty.getInstance().getDbPassword();
        String expectedDbPassword = "test_password";
        assertEquals(expectedDbPassword, dbPassword);
    }
}
