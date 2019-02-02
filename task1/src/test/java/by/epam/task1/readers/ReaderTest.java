package by.epam.task1.readers;

import by.epam.task1.entities.Point;
import by.epam.task1.entities.Sphere;
import by.epam.task1.exeptions.FigureException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * This class tests reader class.
 *
 * There is two actual lists in data provider for ReadFileTest() test.
 * First is good values (file to test is located inTEST_SPHERES_GOOD_VALUE_FILE_PATH)
 * First is bad values (file to test is located in TEST_SPHERES_BAD_VALUE_FILE_PATH)
 * A list with bad values should be empty because reader should not read
 * any value from this file.
 * File with good values contains bad lines, and actual list does not
 * contain them.
 *
 * Test ReadFileTestNoFile() tests reader reaction on not valid file path.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 * @see Reader
 * @see Sphere
 * */
public class ReaderTest extends Assert {

    private static final String TEST_SPHERES_GOOD_VALUE_FILE_PATH =
            "src/main/resources/test_spheres_good_value.txt";
    private static final String TEST_SPHERES_BAD_VALUE_FILE_PATH =
            "src/main/resources/test_spheres_bad_value.txt";


    @DataProvider
    public Object[][] parseData() {
        List<Sphere> actualSpheresGoodValue = new ArrayList<>(11);
        actualSpheresGoodValue.add(0, new Sphere(new Point(
                    1.9, 1.34, 1.3), 1.2));
        actualSpheresGoodValue.add(1, new Sphere(new Point(
                    -2, 3, 4), 5));
        actualSpheresGoodValue.add(2, new Sphere(new Point(
                    2.334, -45.3, 6.78), 1.2));
        actualSpheresGoodValue.add(3, new Sphere(new Point(
                    -23.3, -343.2, -3), 0.1));
        actualSpheresGoodValue.add(4, new Sphere(new Point(
                23223, 23232, 5478), 3232323));
        actualSpheresGoodValue.add(5, new Sphere(new Point(
                -23.3, 34366.72, 667), 0));
        actualSpheresGoodValue.add(6, new Sphere(new Point(
                Double.MAX_VALUE,  Double.MAX_VALUE,  Double.MAX_VALUE),
                Double.MAX_VALUE));
        actualSpheresGoodValue.add(7, new Sphere(new Point(
                Double.MIN_VALUE,  Double.MIN_VALUE,  Double.MIN_VALUE),
                Double.MIN_VALUE));
        actualSpheresGoodValue.add(8, new Sphere(new Point(
                0x1.fffffdfffffffp1023,  0x1.fffffdfffffffp1023,
                0x1.fffffdfffffffp1023),  0x1.fffffdfffffffp1023));
        actualSpheresGoodValue.add(9, new Sphere(new Point(
                -0x1.fffffffffffffp1023,  -0x1.fffffffffffffp1023,
                -0x1.fffffffffffffp1023),  0x1.fffffffffffffp1023));
        actualSpheresGoodValue.add(10, new Sphere(new Point(
                -0x0.0000000000001p-1022,  -0x0.0000000000001p-1022,
                -0x0.0000000000001p-1022),  0x0.0000000000001p-1022));

        List<Sphere> actualSpheresBadValue = new ArrayList<>();

        return new Object[][] {
                { actualSpheresGoodValue, TEST_SPHERES_GOOD_VALUE_FILE_PATH },
                { actualSpheresBadValue, TEST_SPHERES_BAD_VALUE_FILE_PATH   }
        };
    }

    @Test(dataProvider = "parseData")
    void ReadFileTest(List<Sphere> actualSpheres, String filePath)
            throws FigureException {
        Reader reader = new Reader(filePath);
        List<Sphere> expectedSpheres = reader.readSpheres();
        assertEquals(actualSpheres, expectedSpheres);
    }

    @Test
    void ReadFileTestNoFile() {
        Reader reader = new Reader("no/no/no/name.txt");
        assertThrows(FigureException.class, reader::readSpheres);
    }
}
