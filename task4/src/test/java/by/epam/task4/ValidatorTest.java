package by.epam.task4;

import by.epam.task4.validator.MedicinesXmlValidator;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static org.testng.AssertJUnit.assertEquals;

public class ValidatorTest {

    private MedicinesXmlValidator validator
            = MedicinesXmlValidator.getInstance();

    private static final String XSD_SCHEMA_PATH
            = "xml/medicines.xsd";

    private static final String TEST_VALID_FILE_PATH
            = "xml/test/valid_medicines.xml";

    private static final String TEST_INVALID_FILE_PATH
            = "xml/test/invalid_medicines.xml";

    private DateFormat dateFormat
            = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeTest
    public void init() {

    }

    @DataProvider
    Object[][] testValidatorDataProvider() {
        return new Object[][] {
                {
                    TEST_VALID_FILE_PATH,
                        true
                },
                {
                    TEST_INVALID_FILE_PATH,
                        false
                }
        };
     }

    @Test(dataProvider = "testValidatorDataProvider")
    public void testValidatorOnValid(String filePath,
                                     boolean expectedResultOfValidation) {
        boolean res = validator.validate(filePath, XSD_SCHEMA_PATH);
        assertEquals(res, expectedResultOfValidation);
    }
}
