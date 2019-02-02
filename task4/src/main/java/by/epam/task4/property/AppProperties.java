package by.epam.task4.property;


import by.epam.task4.exception.TaskRuntimeException;
import lombok.Getter;
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
public final class AppProperties {

    /**
     * Properties.
     */
    private static AppProperties appProperties;

    /**
     * XML file path property.
     */
    @Getter
    private String medicinesXmlFilePath;

    /**
     * XSD file path property.
     */
    @Getter
    private String medicinesXsdFilePath;

    /**
     * Date format property.
     */
    @Getter
    private String dateFormat;

    /**
     * Properties file path.
     */
    private static final String PROP_FILE_PATH
            = "application.properties";

    /**
     * @return property singleton
     */
    public static AppProperties getInstance() {
        if (appProperties == null) {
            appProperties = new AppProperties();
        }
        return appProperties;
    }

    /**
     * Private constructor for singleton.
     * Loads properties file from resources.
     */
    private AppProperties() {
        loadProperties();
    }

    /**
     * This method loads properties file from resources.
     */
    private void loadProperties() {
        Properties prop = new Properties();
        try {
            InputStream inputStream = getClass().getClassLoader()
                    .getResourceAsStream(PROP_FILE_PATH);
            prop.load(inputStream);
            medicinesXmlFilePath = prop.getProperty("medicinesXmlFilePath");
            dateFormat = prop.getProperty("dateFormat");
            medicinesXsdFilePath = prop.getProperty("medicinesXsdFilePath");
        } catch (IOException e) {
            log.fatal("Cannot load property file.");
            throw new TaskRuntimeException("Cannot load property file");
        }
    }
}
