package by.epam.task4.handler;

import by.epam.task4.builder.MedicinesSaxBuilder;
import by.epam.task4.exception.TaskRuntimeException;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;


/**
 * SAX handler. Makes medicines object from node tree using SAX technology.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
@Log4j2
public final class MedicineSaxHandler extends DefaultHandler {

    /**
     * Medicine builder.
     */
    @Getter
    private MedicinesSaxBuilder builder = new MedicinesSaxBuilder();

    @Override
    public void startElement(final String uri, final String localName,
                             final String qName, final Attributes attributes) {
        switch (qName) {
            case "medicines":
                break;

            case "medicine":
                builder.medicine(attributes);
                break;

            case "analogs":
                builder.analogs(attributes);
                break;

            case "analog":
                builder.analog(attributes);
                break;

            case "versions":
                builder.versions(attributes);
                break;

            case "version":
                builder.version(attributes);
                break;

            case "company":
                builder.company(attributes);
                break;

            case "certificate":
                builder.certificate(attributes);
                break;

            case "package":
                builder.pack(attributes);
                break;

            case "dosage":
                builder.dosage(attributes);
                break;

            default:
                throw new TaskRuntimeException(
                        "Error while parsing xml file. Illegal tag.");
        }
    }
}
