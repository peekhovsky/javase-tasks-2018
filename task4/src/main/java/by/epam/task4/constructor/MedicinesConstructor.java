package by.epam.task4.constructor;

import by.epam.task4.exception.TaskException;
import by.epam.task4.model.Medicines;

import java.io.InputStream;

/**
 * Interface that describes an object that reads XML file from file path or
 * input stream and makes medicine list from its data.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 * @see Medicines
 */
public interface MedicinesConstructor {

    /**
     * This method reads XML file from file path and makes medicine
     * list from its data.
     *
     * @param path path to xml file
     * @return medicine list
     * @throws TaskException if there is an error in file opening or parsing
     */
    Medicines parse(String path) throws TaskException;

    /**
     * This method reads XML file from input stream and makes medicine
     * list from its data.
     *
     * @param stream xml file input stream
     * @return medicine list
     * @throws TaskException if there is an error in file opening or parsing
     */
    Medicines parse(InputStream stream) throws TaskException;
}
