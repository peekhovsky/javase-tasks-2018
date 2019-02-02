package by.epam.task4.builder;

import by.epam.task4.exception.TaskRuntimeException;
import by.epam.task4.model.Package;
import by.epam.task4.model.*;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.text.ParseException;


/**
 * Class that makes Medicine list (Medicines) using DOM parser.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
public class MedicinesDomBuilder extends MedicinesXMLBuilder {

    /**
     * A medicine list as an object.
     */
    private Medicines medicines = new Medicines();

    /**
     * A medicine.
     */
    private Medicine medicine;

    /**
     * * An analogs list as an object.
     */
    private Analogs analogs;

    /**
     * An versions list as an object.
     */
    private Versions versions;

    /**
     * A version.
     */
    private Version version;

    /**
     * A company.
     */
    private Company company;

    /**
     * @param node note that contains medicine data.
     * @return builder
     * @see Medicine
     */
    public MedicinesDomBuilder medicine(final Node node) {
        NamedNodeMap attributeNodes = node.getAttributes();
        medicine = new Medicine();
        medicines.getMedicineList().add(medicine);

        medicine.setId(Integer.parseInt(
                attributeNodes.getNamedItem("id").getNodeValue()));
        medicine.setName(
                attributeNodes.getNamedItem("name").getNodeValue());
        medicine.setPharm(
                attributeNodes.getNamedItem("pharm").getNodeValue());
        Node groupNode = attributeNodes.getNamedItem("group");
        if (groupNode != null) {
            medicine.setGroup(groupNode.getNodeValue());
        } else {
            medicine.setGroup("other");
        }
        return this;
    }

    /**
     * @param node note that contains Analogs data.
     * @return builder
     * @see Analogs
     */
    public MedicinesDomBuilder analogs(final Node node) {
        analogs = new Analogs();
        medicine.setAnalogs(analogs);
        return this;
    }

    /**
     * @param node note that contains Analog data.
     * @return builder
     * @see Analog
     */
    public MedicinesDomBuilder analog(final Node node) {
        Analog analog = Analog.builder()
                .name(node
                        .getAttributes()
                        .getNamedItem("name")
                        .getNodeValue())
                .build();
        analogs.getAnalogList().add(analog);
        return this;
    }

    /**
     * @param node note that contains Versions data.
     * @return builder
     * @see Versions
     */
    public MedicinesDomBuilder versions(final Node node) {
        versions = new Versions();
        medicine.setVersions(versions);
        return this;
    }

    /**
     * @param node note that contains Version data.
     * @return builder
     * @see Version
     */
    public MedicinesDomBuilder version(final Node node) {
        version = new Version();
        Node typeNode = node.getAttributes().getNamedItem("type");
        if (typeNode != null) {
            version.setType(typeNode.getNodeValue());
        } else {
            version.setType("other");
        }
        versions.getVersionList().add(version);
        return this;
    }

    /**
     * @param node note that contains Company data.
     * @return builder
     * @see Company
     */
    public MedicinesDomBuilder company(final Node node) {
        company = new Company();
        company.setName(node
                .getAttributes()
                .getNamedItem("name")
                .getNodeValue()
        );
        version.getCompanyList().add(company);
        return this;
    }

    /**
     * @param node note that contains Certificate data.
     * @return builder
     * @see Certificate
     */
    public MedicinesDomBuilder certificate(final Node node) {
        try {
            NamedNodeMap attributes = node.getAttributes();
            Certificate certificate = Certificate
                    .builder()
                    .num(Long.parseUnsignedLong(
                            attributes.getNamedItem("num")
                                    .getNodeValue())
                    )
                    .expireDate(DATA_FORMAT.parse(
                            attributes.getNamedItem("expireDate")
                                    .getNodeValue())
                    )
                    .regOrganisation(
                            attributes.getNamedItem("regOrganisation")
                                    .getNodeValue())
                    .build();

            if (attributes.getNamedItem("issueDate") != null) {
                certificate.setIssueDate(DATA_FORMAT.parse(
                        attributes.getNamedItem("issueDate")
                                .getNodeValue()));
            }
            company.setCertificate(certificate);
        } catch (ParseException e) {
            throw new TaskRuntimeException("Invalid date format: "
                    + e.getMessage());
        } catch (NullPointerException e) {
            throw new TaskRuntimeException("Invalid xml node: "
                    + e.getMessage());
        }
        return this;
    }

    /**
     * @param node note that contains Package data.
     * @return builder
     * @see Package
     */
    public MedicinesDomBuilder pack(final Node node) {
        try {
            NamedNodeMap attributes = node.getAttributes();
            Package pack = Package
                    .builder()
                    .quantity(Long.parseLong(
                            attributes.getNamedItem("quantity")
                                    .getNodeValue())
                    )
                    .type(
                            attributes.getNamedItem("type")
                                    .getNodeValue())
                    .price(Long.parseLong(
                            attributes.getNamedItem("price")
                                    .getNodeValue())
                    )
                    .build();
            company.setAPackage(pack);
        } catch (NullPointerException e) {
            throw new TaskRuntimeException("Invalid xml node: "
                    + e.getMessage());
        }
        return this;
    }

    /**
     * @param node note that contains Dosage data.
     * @return builder
     * @see Dosage
     */
    public MedicinesDomBuilder dosage(final Node node) {
        try {
            NamedNodeMap attributes = node.getAttributes();
            Dosage dosage = Dosage
                    .builder()
                    .periodDays(Integer.parseInt(
                            attributes.getNamedItem("periodDays")
                                    .getNodeValue())
                    )
                    .valuePerDay(Integer.parseInt(
                            attributes.getNamedItem("valuePerDay")
                                    .getNodeValue())
                    )
                    .build();
            company.setDosage(dosage);
        } catch (NullPointerException e) {
            throw new TaskRuntimeException("Invalid xml node: "
                    + e.getMessage());
        }
        return this;
    }

    /**
     * @return medicines
     */
    @Override
    public Medicines build() {
        return medicines;
    }
}
