package by.epam.task4.validator;

import by.epam.task4.exception.TaskRuntimeException;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.util.Objects;


/**
 * Validator based on xsd schema for xml medicine file.
 *
 * @author Rostislav Pekhovksy 2018
 * @version 0.1
 */
@Log4j2
public final class MedicinesXmlValidator {

    /**
     * An instance (singleton realisation).
     */
    private static MedicinesXmlValidator instance;

    /**
     * Schema language (ns uri).
     */
    private static final String SCHEMA_LANGUAGE
            = XMLConstants.W3C_XML_SCHEMA_NS_URI;

    /**
     * Schema factory.
     */
    private final SchemaFactory schemaFactory
            = SchemaFactory.newInstance(SCHEMA_LANGUAGE);

    /**
     * Private constructor (singleton realisation).
     */
    private MedicinesXmlValidator() {
    }

    /**
     * @return an instance of this object
     */
    public static MedicinesXmlValidator getInstance() {
        if (instance == null) {
            instance = new MedicinesXmlValidator();
        }
        return instance;
    }

    /**
     * This method validates xml file using xsd schema that is been loaded in
     * constructor.
     *
     * @param xmlFileInputPath xml file path
     * @param xsdFilePath      xsd file path
     * @return true if file is valid, otherwise false
     */
    public boolean validate(@NonNull final String xmlFileInputPath,
                            @NonNull final String xsdFilePath) {
        try {
            Schema schema = schemaFactory.newSchema(
                    Objects.requireNonNull(
                            getClass().getClassLoader()
                                    .getResource(xsdFilePath)));
            Validator validator = schema.newValidator();
            Source source = new StreamSource(getClass().getClassLoader()
                    .getResourceAsStream(xmlFileInputPath));
            validator.validate(source);
            log.info("Document " + xmlFileInputPath + "is valid.");
        } catch (SAXException e) {
            log.error("Cannot validate this document: "
                    + e.getMessage());
            return false;
        } catch (IOException e) {
            throw new TaskRuntimeException("Cannot read xml document: "
                    + e.getMessage());
        }
        return true;
    }
}
