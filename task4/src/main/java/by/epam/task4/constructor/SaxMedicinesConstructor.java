package by.epam.task4.constructor;

import by.epam.task4.exception.TaskException;
import by.epam.task4.model.Medicines;
import by.epam.task4.handler.MedicineSaxHandler;
import lombok.extern.log4j.Log4j2;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Class that makes medicines list from file using SAX parser.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
@Log4j2
public final class SaxMedicinesConstructor implements MedicinesConstructor {

    /**
     * An instance of object (for singleton realisation).
     */
    private static SaxMedicinesConstructor instance;


    /**
     * Private no args constructor (singleton realisation).
     */
    private SaxMedicinesConstructor() {
    }

    /**
     * @return an instance of this object
     */
    public static SaxMedicinesConstructor getInstance() {
        if (instance == null) {
            instance = new SaxMedicinesConstructor();
        }
        return instance;
    }


    @Override
    public Medicines parse(final String path) throws TaskException {
        try {
            XMLReader reader = XMLReaderFactory.createXMLReader();
            MedicineSaxHandler medicineSaxHandler = new MedicineSaxHandler();
            reader.setContentHandler(medicineSaxHandler);
            reader.parse(new InputSource(
                    getClass().getClassLoader().getResourceAsStream(path))
            );
            return medicineSaxHandler.getBuilder().build();

        } catch (SAXException e) {
            log.fatal(e.getMessage());
            throw new TaskException("Parsing exception, "
                    + e.getMessage() + ".");
        } catch (IOException e) {
            throw new TaskException("Illegal file path, " + e.getMessage());
        }
    }

    @Override
    public Medicines parse(final InputStream stream) throws TaskException {
        try {
            XMLReader reader = XMLReaderFactory.createXMLReader();
            MedicineSaxHandler medicineSaxHandler = new MedicineSaxHandler();
            reader.setContentHandler(medicineSaxHandler);
            reader.parse(new InputSource(stream));
            return medicineSaxHandler.getBuilder().build();

        } catch (SAXException e) {
            log.fatal(e.getMessage());
            throw new TaskException("Parsing exception, "
                    + e.getMessage() + ".");
        } catch (IOException e) {
            throw new TaskException("Illegal file path, " + e.getMessage());
        }
    }
}
