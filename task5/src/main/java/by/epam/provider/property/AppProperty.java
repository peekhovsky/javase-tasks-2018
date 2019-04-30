package by.epam.provider.property;

import by.epam.provider.exception.ProviderRuntimeException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class is used to load properties from properties file
 * for this app.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
@Log4j2
public final class AppProperty {

    /**
     * App properties file path.
     */
    @Getter
    @Setter
    private static String propertiesPath = "application.properties";

    /**
     * Properties.
     */
    private static AppProperty appProperties;

    /**
     * Date format property.
     */
    @Getter
    private String dateFormat;

    /**
     * A login to authorise for database.
     */
    @Getter
    private String dbLogin;

    /**
     * A password to authorise for database.
     */
    @Getter
    private String dbPassword;


    /**
     * A driver name for database.
     */
    @Getter
    private String dbDriver;

    /**
     * Server url (for instance, localhost:3306).
     */
    @Getter
    private String dbDomain;

    /**
     * Server name.
     */
    @Getter
    private String dbServerName;

    /**
     * @return property singleton
     */
    public static synchronized AppProperty getInstance() {
        if (appProperties == null) {
            appProperties = new AppProperty();
        }
        return appProperties;
    }

    /**
     * Private constructor for singleton.
     * Loads properties file from resources.
     */
    private AppProperty() {
        loadProperties(propertiesPath);
    }

    /**
     * This method loads properties file from resources.
     */
    private void loadProperties(final String propertiesPath) {
        Properties prop = new Properties();
        try {
            InputStream inputStream = getClass().getClassLoader()
                    .getResourceAsStream(propertiesPath);
            prop.load(inputStream);

            dbLogin = prop.getProperty("provider.db.login");
            dbPassword = prop.getProperty("provider.db.password");
            dbDriver = prop.getProperty("provider.db.driver");
            dbDomain = prop.getProperty("provider.db.dmbs");
            dbServerName = prop.getProperty("provider.db.serverName");
            dateFormat = prop.getProperty("dateFormat");

        } catch (IOException e) {
            log.fatal("Cannot load property file.");
            throw new ProviderRuntimeException("Cannot load property file", e);
        }
    }
}