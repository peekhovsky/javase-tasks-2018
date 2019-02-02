package by.epam.task4.builder;

import by.epam.task4.exception.TaskRuntimeException;
import by.epam.task4.model.Package;
import by.epam.task4.model.*;
import by.epam.task4.property.AppProperties;
import lombok.extern.log4j.Log4j2;

import javax.xml.namespace.QName;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * Class that makes Medicine list (Medicines) using STaX parser.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
@Log4j2
public class MedicinesStaxBuilder {

    /**
     * A date format for all dates in xml file (XML default dates).
     */
    private final DateFormat dateFormat = new SimpleDateFormat(
            AppProperties.getInstance().getDateFormat());

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
     * @param startElement an object that contains medicine attributes data.
     * @return this builder
     */
    public MedicinesStaxBuilder medicine(final StartElement startElement) {

        medicine = Medicine.builder()
                .id(Integer.parseInt(startElement
                        .getAttributeByName(new QName("id"))
                        .getValue()))
                .name(startElement
                        .getAttributeByName(new QName("name"))
                        .getValue())
                .pharm(startElement
                        .getAttributeByName(new QName("pharm"))
                        .getValue())
                .build();
        Attribute groupAttribute = startElement
                .getAttributeByName(new QName("group"));
        if (groupAttribute != null) {
            medicine.setGroup(groupAttribute.getValue());
        } else {
            medicine.setGroup("other");
        }
        medicines.getMedicineList().add(medicine);
        return this;
    }

    /**
     * @param startElement an object that contains analogs (analog list)
     *                     attributes data. There is no usage of this element
     *                     in body because analogs don't have any of attributes.
     *                     But it is left to make possible to add any of
     *                     attribute to analogs element and change this
     *                     method to get data about it
     * @return this builder
     */
    public MedicinesStaxBuilder analogs(final StartElement startElement) {
        analogs = new Analogs();
        medicine.setAnalogs(analogs);
        return this;
    }

    /**
     * @param startElement an object that contains analog attributes data
     * @return this builder
     */
    public MedicinesStaxBuilder analog(final StartElement startElement) {
        if (!startElement.getAttributes().hasNext()) {
            throw new TaskRuntimeException("Attributes < 1");
        }
        Analog analog = new Analog(startElement
                .getAttributeByName(new QName("name"))
                .getValue());
        analogs.getAnalogList().add(analog);
        return this;
    }

    /**
     * @param startElement an object that contains versions (version list)
     *                     attributes data. There is no usage of this element
     *                     in body because analogs don't have any of attributes.
     *                     But it is left to make possible to add any of
     *                     attribute to analogs element and change this
     *                     method to get data about it
     * @return this builder
     */
    public MedicinesStaxBuilder versions(final StartElement startElement) {
        versions = new Versions();
        medicine.setVersions(versions);
        return this;
    }

    /**
     * @param startElement an object that contains version attributes data
     * @return this builder
     */
    public MedicinesStaxBuilder version(final StartElement startElement) {
        version = new Version();
        Attribute attribute = startElement
                .getAttributeByName(new QName("type"));
        if (attribute != null) {
            version.setType(attribute.getValue());
        } else {
            version.setType("other");
        }
        versions.getVersionList().add(version);
        return this;
    }

    /**
     * @param startElement an object that contains analog attributes data
     * @return this builder
     */
    public MedicinesStaxBuilder company(final StartElement startElement) {
        company = new Company();
        company.setName(startElement
                .getAttributeByName(new QName("name"))
                .getValue());
        version.getCompanyList().add(company);
        return this;
    }

    /**
     * @param startElement an object that contains certificate attributes data
     * @return this builder
     */
    public MedicinesStaxBuilder certificate(final StartElement startElement) {
        try {
            Certificate certificate = Certificate.builder()
                    .num(Long.parseLong(
                            startElement.getAttributeByName(
                                    new QName("num"))
                                    .getValue()))
                    .expireDate(dateFormat.parse(
                            startElement.getAttributeByName(
                                    new QName("expireDate"))
                                    .getValue()))
                    .regOrganisation(startElement
                            .getAttributeByName(
                                    new QName("regOrganisation"))
                            .getValue())
                    .build();

            Attribute issueDateAttr = startElement.getAttributeByName(
                    new QName("issueDate"));
            if (issueDateAttr != null) {
                certificate.setIssueDate(dateFormat.parse(
                        issueDateAttr.getValue()));
            }
            company.setCertificate(certificate);
        } catch (ParseException e) {
            throw new TaskRuntimeException(e.getMessage());
        }
        return this;
    }

    /**
     * @param startElement object that contains package attributes data
     * @return this builder
     */
    public MedicinesStaxBuilder pack(final StartElement startElement) {
        Package pack = Package.builder()
                .type(startElement
                        .getAttributeByName(
                                new QName("type"))
                        .getValue())
                .quantity(Integer.parseInt(startElement
                        .getAttributeByName(
                                new QName("quantity"))
                        .getValue()))
                .price(Long.parseLong(startElement
                        .getAttributeByName(
                                new QName("price"))
                        .getValue()))
                .build();
        company.setAPackage(pack);
        return this;
    }

    /**
     * @param startElement object that contains dosage attributes data
     * @return this builder
     */
    public MedicinesStaxBuilder dosage(final StartElement startElement) {
        Dosage dosage = Dosage.builder()
                .valuePerDay(Integer.parseInt(startElement
                        .getAttributeByName(
                                new QName("valuePerDay"))
                        .getValue()))
                .periodDays(Integer.parseInt(startElement
                        .getAttributeByName(
                                new QName("periodDays"))
                        .getValue()))
                .build();
        company.setDosage(dosage);
        return this;
    }

    /**
     * @return medicine list
     */
    public Medicines build() {
        return medicines;
    }
}
