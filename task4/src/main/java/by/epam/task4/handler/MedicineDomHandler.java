package by.epam.task4.handler;

import by.epam.task4.builder.MedicinesDomBuilder;
import by.epam.task4.exception.TaskException;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;


/**
 * DOM handler. Makes medicines object from node tree using DOM technology.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
@Log4j2
public class MedicineDomHandler {

    /**
     * Medicines dom builder. Makes Medinices and inner objects
     * from attributes in node.
     */
    @Getter
    private MedicinesDomBuilder builder = new MedicinesDomBuilder();

    /**
     * Document is main node of all xml file.
     */
    private Document document;

    /**
     * Constructor. Makes a document from xml file input stream.
     *
     * @param stream xml file input stream
     * @throws TaskException if there is an error in file reading or in parser
     *                       configuration
     */
    public MedicineDomHandler(final InputStream stream) throws TaskException {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder();
            document = documentBuilder.parse(stream);

        } catch (ParserConfigurationException e) {
            throw new TaskException("Parser error: " + e.getMessage());
        } catch (IOException e) {
            throw new TaskException("Cannot read file: " + e.getMessage());
        } catch (SAXException e) {
            throw new TaskException("SAX error: " + e.getMessage());
        }
    }

    /**
     * Constructor. Makes a document from xml file input stream.
     *
     * @param path path to xml file
     * @throws TaskException if there is an error in file reading or in parser
     *                       configuration
     */
    public MedicineDomHandler(final String path) throws TaskException {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder();
            document = documentBuilder.parse(path);
        } catch (ParserConfigurationException e) {
            throw new TaskException("Parser error: " + e.getMessage());
        } catch (SAXException e) {
            throw new TaskException("SAX error: " + e.getMessage());
        } catch (IOException e) {
            throw new TaskException("Cannot read file: " + e.getMessage());
        }
    }

    /**
     * This method goes though child nodes of document and depending on type of
     * node calls medicine parser (medicineFromNode) if it is medicine node.
     *
     * @throws TaskException if there is no nodes in file
     */
    public void parse() throws TaskException {
        NodeList medicinesNodes = document.getChildNodes();
        NodeList medicineNodes = null;
        for (int i = 0; i < medicinesNodes.getLength(); i++) {
            if (medicinesNodes.item(i).getNodeName().equals("medicines")) {
                medicineNodes = medicinesNodes.item(i).getChildNodes();
            }
        }
        if (medicineNodes == null) {
            throw new TaskException("Invalid xml file.");
        }

        for (int i = 0; i < medicineNodes.getLength(); i++) {
            Node medicineNode = medicineNodes.item(i);
            if (medicineNode.getNodeName().equals("medicine")) {
                builder.medicine(medicineNode);
                medicineFromNode(medicineNode);
            }
        }
    }

    /**
     * This method gets a data from medicine attributes and calls an appropriate
     * methods for its child nodes.
     *
     * @param medicineNode node with medicine data
     */
    private void medicineFromNode(final Node medicineNode) {
        NodeList nodeList = medicineNode.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            switch (node.getNodeName()) {
                case "analogs":
                    builder.analogs(node);
                    analogsFromNode(node);
                    break;

                case "versions":
                    builder.versions(node);
                    versionsFromNode(node);
                    break;
                default:
                    log.trace("Default case");
            }
        }
    }

    /**
     * This method calls an appropriate methods for analogs child nodes.
     *
     * @param analogsNode node with analogs data
     */
    private void analogsFromNode(final Node analogsNode) {
        NodeList nodeList = analogsNode.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeName().equals("analog")) {
                builder.analog(node);
            }
        }
    }

    /**
     * This method calls an appropriate methods for versions child nodes.
     *
     * @param versionsNode node with versions data
     */
    private void versionsFromNode(final Node versionsNode) {
        NodeList nodeList = versionsNode.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeName().equals("version")) {
                builder.version(node);
                versionFromNode(node);
            }
        }
    }

    /**
     * This method gets a data from version attributes and calls an appropriate
     * methods for its child nodes.
     *
     * @param versionNode node with version data
     */
    private void versionFromNode(final Node versionNode) {
        NodeList nodeList = versionNode.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeName().equals("company")) {
                builder.company(node);
                companyFromNode(node);

            }
        }
    }

    /**
     * This method gets a data from compant attributes and calls an appropriate
     * methods for its child nodes.
     *
     * @param companyNode node with company data
     */
    private void companyFromNode(final Node companyNode) {
        NodeList nodeList = companyNode.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            switch (node.getNodeName()) {
                case "certificate":
                    builder.certificate(node);
                    break;

                case "package":
                    builder.pack(node);
                    break;

                case "dosage":
                    builder.dosage(node);
                    break;

                default:
                    log.trace("Default (companyFromNode): "
                            + node.getNodeName());
            }
        }
    }
}
