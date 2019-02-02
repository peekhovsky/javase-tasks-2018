package by.epam.task4.handler;

import by.epam.task4.builder.MedicinesStaxBuilder;
import by.epam.task4.exception.TaskException;
import by.epam.task4.exception.TaskRuntimeException;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;


/**
 * STaX handler. Makes medicines object from node tree using STaX technology.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
@Log4j2
public class MedicineStaxHandler {

    /**
     * Medicines stax builder.
     */
    @Getter
    private MedicinesStaxBuilder builder = new MedicinesStaxBuilder();

    /**
     * Event reader (StAX).
     */
    private XMLEventReader reader;

    /**
     * Constructor.
     *
     * @param stream xml file input stream
     * @throws TaskException if there is an error in creating of reader
     */
    public MedicineStaxHandler(final InputStream stream) throws TaskException {
        try {
            reader = XMLInputFactory.newInstance()
                    .createXMLEventReader(stream);
        } catch (XMLStreamException e) {
            throw new TaskException("Cannot create reader: " + e.getMessage());
        }
    }

    /**
     * This method starts the process of reading.
     */
    public void parse() {
        try {
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    startElement(event.asStartElement());
                }
            }
        } catch (XMLStreamException e) {
            throw new TaskRuntimeException(e.getMessage());
        }
    }

    /**
     * This method is called to process an attribute data from elements
     * depending on type of element.
     *
     * @param startElement element data
     */
    private void startElement(final StartElement startElement) {
        switch (startElement.getName().getLocalPart()) {
            case "medicines":
                break;

            case "medicine":
                builder.medicine(startElement);
                break;

            case "analogs":
                builder.analogs(startElement);
                break;

            case "analog":
                builder.analog(startElement);
                break;

            case "versions":
                builder.versions(startElement);
                break;

            case "version":
                builder.version(startElement);
                break;

            case "company":
                builder.company(startElement);
                break;

            case "certificate":
                builder.certificate(startElement);
                break;

            case "package":
                builder.pack(startElement);
                break;

            case "dosage":
                builder.dosage(startElement);
                break;

            default:
                throw new TaskRuntimeException(
                        "Error while parsing xml file. Illegal tag: "
                                + startElement.getName().getLocalPart());
        }
    }
}
