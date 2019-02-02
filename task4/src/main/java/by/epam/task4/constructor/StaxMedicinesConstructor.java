package by.epam.task4.constructor;

import by.epam.task4.exception.TaskException;
import by.epam.task4.handler.MedicineStaxHandler;
import by.epam.task4.model.Medicines;
import lombok.extern.log4j.Log4j2;

import java.io.InputStream;

/**
 * Class that makes medicines list from file using SAX parser.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
@Log4j2
public final class StaxMedicinesConstructor implements MedicinesConstructor {

    /**
     * An instance of object (for singleton realisation).
     */
    private static StaxMedicinesConstructor instance;

    /**
     * Private no args constructor (singleton realisation).
     */
    private StaxMedicinesConstructor() {
    }

    /**
     * @return an instance of this object
     */
    public static StaxMedicinesConstructor getInstance() {
        if (instance == null) {
            instance = new StaxMedicinesConstructor();
        }
        return instance;
    }

    @Override
    public Medicines parse(final String path) throws TaskException {
        MedicineStaxHandler staxHandler
                = new MedicineStaxHandler(getClass()
                .getClassLoader().getResourceAsStream(path));
        staxHandler.parse();
        return staxHandler.getBuilder().build();

    }

    @Override
    public Medicines parse(final InputStream stream) throws TaskException {
        MedicineStaxHandler staxHandler = new MedicineStaxHandler(stream);
        staxHandler.parse();
        return staxHandler.getBuilder().build();
    }
}
