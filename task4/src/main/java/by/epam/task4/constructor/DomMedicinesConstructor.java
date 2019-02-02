package by.epam.task4.constructor;

import by.epam.task4.exception.TaskException;
import by.epam.task4.model.Medicines;
import by.epam.task4.handler.MedicineDomHandler;
import lombok.extern.log4j.Log4j2;

import java.io.InputStream;

/**
 * Class that makes medicines list from file using DOM parser.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
@Log4j2
public final class DomMedicinesConstructor implements MedicinesConstructor {

    /**
     * An instance of this object.
     */
    private static DomMedicinesConstructor instance;

    /**
     * Private no args constructor (singleton realisation).
     */
    private DomMedicinesConstructor() {
    }

    /**
     * @return an instance of this object
     */
    public static DomMedicinesConstructor getInstance() {
        if (instance == null) {
            instance = new DomMedicinesConstructor();
        }
        return instance;
    }

    @Override
    public Medicines parse(final String path) throws TaskException {
        MedicineDomHandler domHandler = new MedicineDomHandler(
                getClass().getClassLoader().getResourceAsStream(path));
        domHandler.parse();
        return domHandler.getBuilder().build();
    }

    @Override
    public Medicines parse(final InputStream stream) throws TaskException {
        MedicineDomHandler domHandler = new MedicineDomHandler(stream);
        domHandler.parse();
        return domHandler.getBuilder().build();
    }
}
