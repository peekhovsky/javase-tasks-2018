package by.epam.task1.validators;

import by.epam.task1.entities.Plane;
import by.epam.task1.entities.Point;
import by.epam.task1.entities.Sphere;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class FigureValidatorTest extends Assert {

    @DataProvider
    Object[][] parseDataIsSphereValid() {
        return new Object[][]{
                {
                        new Sphere(new Point(41454.4,41.3,
                                -11212),1766),
                        true
                },
                {
                        new Sphere(new Point(-91,2.232,
                                71),676761),
                        true
                },
                {
                        new Sphere(new Point(Double.NaN,2.232,
                                71),676761),
                        false
                },
                {
                        new Sphere(new Point(Double.NaN,2.232,
                                71),-123),
                        false
                },
        };
    }

    @Test(dataProvider = "parseDataIsSphereValid")
    void testIsSphereValid(final Sphere sphere, final boolean res) {
        assertEquals(FigureValidator.isSphereValid(sphere), res);
    }

    @DataProvider
    Object[][] parseDataIsPlaneValid() {
        return new Object[][]{
                {
                        new Plane(112,112344,
                                3.1212, 23),
                        true
                },
                {
                        new Plane(112,1344,
                                -3.1212, 23),
                        true
                },
                {
                        new Plane(112,-112344,
                                3.1212, 23),
                        true
                },
                {
                        new Plane(Double.MIN_VALUE,Double.MIN_VALUE,
                                Double.MIN_VALUE, Double.MIN_VALUE),
                        true
                },
                {
                        new Plane(Double.MAX_VALUE,Double.MAX_VALUE,
                                Double.MAX_VALUE, Double.MAX_VALUE),
                        true
                },
                {
                        new Plane(Double.NEGATIVE_INFINITY, Double.MAX_VALUE,
                                Double.MAX_VALUE, Double.MAX_VALUE),
                        false
                },
                {
                        new Plane(343, -34.2,
                                2, Double.NaN),
                        false
                }

        };
    }
    
    @Test(dataProvider = "parseDataIsSphereValid")
    void testIsPlaneValid(final Sphere sphere, final boolean res) {
        assertEquals(FigureValidator.isSphereValid(sphere), res);
    }
}
