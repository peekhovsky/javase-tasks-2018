package by.epam.task4.builder;

import by.epam.task4.exception.TaskRuntimeException;
import by.epam.task4.model.Package;
import by.epam.task4.model.*;
import lombok.extern.log4j.Log4j2;
import org.xml.sax.Attributes;

import java.text.ParseException;

/**
 * Class that makes Medicine list (Medicines) using Sax parser.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
@Log4j2
public class MedicinesSaxBuilder extends MedicinesXMLBuilder {

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
     * @param attributes object that contains medicine attributes data.
     * @return this builder
     */
    public MedicinesSaxBuilder medicine(final Attributes attributes) {
        try {
            medicine = Medicine.builder()
                    .id(Integer.parseInt(attributes.getValue("id")))
                    .name(attributes.getValue("name"))
                    .pharm(attributes.getValue("pharm"))
                    .group(attributes.getValue("group"))
                    .build();
        } catch (NullPointerException e) {
            log.error("Null pointer exception: " + e.getMessage());
            throw new TaskRuntimeException("Invalid xml file: "
                    + e.getMessage());
        }
        medicines.getMedicineList().add(medicine);
        return this;
    }

    /**
     * @param attributes object that contains analogs attributes data.
     * @return this builder
     */
    public MedicinesSaxBuilder analogs(final Attributes attributes) {
        analogs = new Analogs();
        medicine.setAnalogs(analogs);
        return this;
    }

    /**
     * @param attributes object that contains analog attributes data.
     * @return this builder
     */
    public MedicinesSaxBuilder analog(final Attributes attributes) {
        if (attributes.getLength() < 1) {
            throw new TaskRuntimeException("Attributes < 1");
        }
        Analog analog = new Analog(attributes.getValue("name"));
        analogs.getAnalogList().add(analog);
        return this;
    }

    /**
     * @param attributes object that contains versions attributes data.
     * @return this builder
     */
    public MedicinesSaxBuilder versions(final Attributes attributes) {
        versions = new Versions();
        medicine.setVersions(versions);
        return this;
    }

    /**
     * @param attributes object that contains version attributes data.
     * @return this builder
     */
    public MedicinesSaxBuilder version(final Attributes attributes) {
        version = new Version();
        String type = attributes.getValue("type");
        if (type != null) {
            version.setType(type);
        } else {
            version.setType("other");
        }
        versions.getVersionList().add(version);
        return this;
    }

    /**
     * @param attributes object that contains company attributes data.
     * @return this builder
     */
    public MedicinesSaxBuilder company(final Attributes attributes) {
        company = new Company();
        company.setName(attributes.getValue("name"));
        version.getCompanyList().add(company);
        return this;
    }

    /**
     * @param attributes object that contains certificate attributes data.
     * @return this builder
     */
    public MedicinesSaxBuilder certificate(final Attributes attributes) {
        try {
            Certificate certificate = Certificate.builder()
                    .num(Long.parseLong(
                            attributes.getValue("num")))
                    .expireDate(DATA_FORMAT.parse(
                            attributes.getValue("expireDate")))
                    .regOrganisation(
                            attributes.getValue("regOrganisation"))
                    .build();

            String issueDateStr = attributes.getValue("issueDate");
            if (issueDateStr != null) {
                certificate.setIssueDate(DATA_FORMAT.parse(issueDateStr));
            }
            company.setCertificate(certificate);
        } catch (ParseException e) {
            throw new TaskRuntimeException("Invalid date format: "
                    + e.getMessage());
        }
        return this;
    }

    /**
     * @param attributes object that contains package attributes data.
     * @return this builder
     */
    public MedicinesSaxBuilder pack(final Attributes attributes) {
        Package pack = Package.builder()
                .type(
                        attributes.getValue("type"))
                .quantity(Integer.parseInt(
                        attributes.getValue("quantity")))
                .price(Long.parseLong(
                        attributes.getValue("price")))
                .build();
        company.setAPackage(pack);
        return this;
    }

    /**
     * @param attributes object that contains dosage attributes data.
     * @return this builder
     */
    public MedicinesSaxBuilder dosage(final Attributes attributes) {
        Dosage dosage = Dosage.builder()
                .valuePerDay(Integer.parseInt(
                        attributes.getValue("valuePerDay")))
                .periodDays(Integer.parseInt(
                        attributes.getValue("periodDays")))
                .build();
        company.setDosage(dosage);
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
