package by.epam.task4;

import by.epam.task4.constructor.DomMedicinesConstructor;
import by.epam.task4.constructor.MedicinesConstructor;
import by.epam.task4.constructor.SaxMedicinesConstructor;
import by.epam.task4.constructor.StaxMedicinesConstructor;
import by.epam.task4.exception.TaskException;
import by.epam.task4.model.Package;
import by.epam.task4.model.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.testng.Assert.assertEquals;

public class ConstructorTest {

    private static final String TEST_FILE_PATH = "xml/test/test_medicines.xml";

    private Medicines expectedMedicines;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeTest
    public void init() throws ParseException {
        expectedMedicines = new Medicines();
        Medicine medicine1 = Medicine.builder()
                .id(1)
                .name("aspirin")
                .pharm("Pharmacare")
                .group("painkillers")
                .build();

        expectedMedicines.getMedicineList().add(medicine1);
        Analogs analogs1 = new Analogs();

        medicine1.setAnalogs(analogs1);

        Analog analog11 = new Analog("asafen");
        Analog analog12 = new Analog("acetylsalicylic acid");

        analogs1.getAnalogList().add(analog11);
        analogs1.getAnalogList().add(analog12);

        Versions versions = new Versions();

        medicine1.setVersions(versions);

        Version version1 = new Version();
        version1.setType("other");
        Version version2 = new Version();
        version2.setType("powder");

        versions.getVersionList().add(version1);
        versions.getVersionList().add(version2);

        Company company11 = new Company();
        company11.setName("Borisov Med");

        company11.setCertificate(Certificate.builder()
                .num(112233L)
                .expireDate(dateFormat.parse("2018-11-12"))
                .issueDate(dateFormat.parse("2018-11-12"))
                .regOrganisation("Belorussian pharmacy")
                .build()
        );
        company11.setAPackage(Package.builder()
                .type("box")
                .quantity(10)
                .price(10L)
                .build()
        );
        company11.setDosage(Dosage.builder()
                .valuePerDay(2)
                .periodDays(14)
                .build()
        );
        version1.getCompanyList().add(company11);

        Company company12 = new Company();
        company12.setName("Pharmacare");
        company12.setCertificate(Certificate.builder()
                .num(223344L)
                .expireDate(dateFormat.parse("2020-04-21"))
                .issueDate(dateFormat.parse("2008-12-12"))
                .regOrganisation("US DRUGS")
                .build()
        );
        company12.setAPackage(Package.builder()
                .type("box")
                .quantity(5)
                .price(50L)
                .build()
        );
        company12.setDosage(Dosage.builder()
                .valuePerDay(1)
                .periodDays(6)
                .build()
        );
        version1.getCompanyList().add(company12);

        Company company21 = new Company();
        company21.setName("Borisov Med");
        company21.setCertificate(Certificate.builder()
                .num(445566L)
                .expireDate(dateFormat.parse("2025-11-12"))
                .issueDate(dateFormat.parse("1999-11-12"))
                .regOrganisation("Belorussian pharmacy")
                .build()
        );
        company21.setAPackage(Package.builder()
                .type("box")
                .quantity(2)
                .price(15L)
                .build()
        );
        company21.setDosage(Dosage.builder()
                .valuePerDay(1)
                .periodDays(14)
                .build()
        );
        version2.getCompanyList().add(company21);

    }

    @Test
    public void testDomConstructor() throws TaskException {
        MedicinesConstructor constructor
                = DomMedicinesConstructor.getInstance();

        Medicines actualMedicines
                = constructor.parse(TEST_FILE_PATH);
        assertEquals(expectedMedicines, actualMedicines);
    }

    @Test
    public void testSaxConstructor() throws TaskException {
        MedicinesConstructor constructor
                = SaxMedicinesConstructor.getInstance();

        Medicines actualMedicines
                = constructor.parse(TEST_FILE_PATH);
        assertEquals(expectedMedicines, actualMedicines);
    }

    @Test
    public void testStaxConstructor() throws TaskException {
        MedicinesConstructor constructor
                = StaxMedicinesConstructor.getInstance();

        Medicines actualMedicines
                = constructor.parse(TEST_FILE_PATH);
        assertEquals(expectedMedicines, actualMedicines);
    }
}
