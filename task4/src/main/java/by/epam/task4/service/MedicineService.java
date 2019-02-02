package by.epam.task4.service;

import by.epam.task4.constructor.DomMedicinesConstructor;
import by.epam.task4.constructor.MedicinesConstructor;
import by.epam.task4.constructor.SaxMedicinesConstructor;
import by.epam.task4.constructor.StaxMedicinesConstructor;
import by.epam.task4.exception.TaskException;
import by.epam.task4.model.Medicines;
import by.epam.task4.property.AppProperties;
import by.epam.task4.validator.MedicinesXmlValidator;

import java.io.InputStream;


/**
 * This class is used to connect data objects with servlet.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
public class MedicineService {

    /**
     * Dom constructor.
     */
    private MedicinesConstructor domMedicinesConstructor
            = DomMedicinesConstructor.getInstance();

    /**
     * Sax constructor.
     */
    private MedicinesConstructor saxMedicinesConstructor
            = SaxMedicinesConstructor.getInstance();

    /**
     * Stax constructor.
     */
    private MedicinesConstructor staxMedicinesConstructor
            = StaxMedicinesConstructor.getInstance();
    /**
     * Medicines xml file path.
     */
    private String medicinesXmlFilePath;

    /**
     * Xsd schema to validate medicines xml file.
     */
    private String medicinesXsdSchema = AppProperties
            .getInstance().getMedicinesXsdFilePath();

    /**
     * Constructor. Loads properties of app: gets a path of medicine xml file
     * from property file.
     *
     * @throws TaskException if there is a problems with reading of properties
     */
    public MedicineService() throws TaskException {
        medicinesXmlFilePath
                = AppProperties.getInstance().getMedicinesXmlFilePath();
    }

    /**
     * @return medicine object list after sax parsing
     * @throws TaskException if there is a problems with reading of xml file
     */
    public Medicines findMedicinesUsingSax() throws TaskException {
        validateXMLFile();
        return saxMedicinesConstructor.parse(newInputStreamOfXmlFile());
    }


    /**
     * @return medicine object list after stax parsing
     * @throws TaskException if there is a problems with reading of xml file
     */
    public Medicines findMedicinesUsingStax() throws TaskException {
        validateXMLFile();
        return staxMedicinesConstructor.parse(newInputStreamOfXmlFile());
    }

    /**
     * @return medicine object list after dom parsing
     * @throws TaskException if there is a problems with reading of xml file
     */
    public Medicines findMedicinesUsingDom() throws TaskException {
        validateXMLFile();
        return domMedicinesConstructor.parse(medicinesXmlFilePath);
    }

    /**
     * This method calls validator for xml file.
     *
     * @throws TaskException if file is not valid
     */
    private void validateXMLFile() throws TaskException {
        if (!MedicinesXmlValidator.getInstance()
                .validate(medicinesXmlFilePath, medicinesXsdSchema)) {
            throw new TaskException("XML file is not valid");
        }
    }

    /**
     * @return new input stream of xml file
     */
    private InputStream newInputStreamOfXmlFile() {
        return getClass().getClassLoader()
                .getResourceAsStream(medicinesXmlFilePath);
    }
}
