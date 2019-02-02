package by.epam.task1.actions;

import by.epam.task1.entities.Plane;
import by.epam.task1.entities.Point;
import by.epam.task1.entities.Sphere;
import by.epam.task1.exeptions.FigureException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SphereActionTest extends Assert {

    private final SphereAction sphereAction = new SphereAction();

    /*
    This class is for data provider. Data provider allows only two params in
    method, use this class too extend a number of params.
    */
    final static class TestData {
        Sphere sphere;
        Plane plane;
        Object actualValue;

        TestData(Sphere sphere, Plane plane, Object actualRatio) {
            this.sphere = sphere;
            this.plane = plane;
            this.actualValue = actualRatio;
        }
    }

    /*
    --------------------Calc volume-------------------
    */
    @DataProvider
    public Object[][] parseDataForCalcVolume() {
        Point point = new Point();
        return new Object[][]{
                {
                        new Sphere(point, 7.270882),
                        1610.0890585960587
                },
                {
                        new Sphere(point, 523.89),
                        6.022945529118623E8
                },
                {
                        new Sphere(point, 8),
                        2144.660584850632
                },
                {
                        new Sphere(point, 334343434.3444),
                        1.5655502318534943E26
                },
                {
                        new Sphere(point, 7000000.00001),
                        1.4367550402478893E21
                },
                {
                        new Sphere(point, 5555000006.9),
                        7.180270420076787E29
                }
        };
    }

    @Test(dataProvider = "parseDataForCalcVolume")
    void testCalcVolume(final Sphere sphere, final double actualVolume)
            throws FigureException {
        double expectedVolume = sphereAction.calcVolume(sphere);
        assertEquals(expectedVolume, actualVolume, 0.0000001);
    }

    @DataProvider
    public Object[] parseDataThrowsException() {
        Point point = new Point();
        return new Object[][]{
                {
                        new Sphere(point, -7.270882)
                },
                {
                        new Sphere(point, -523.89)
                },
                {
                        new Sphere(point, Double.MAX_VALUE)
                },
                {
                        new Sphere(point, Double.NaN)
                },
                {
                        new Sphere(point, Double.POSITIVE_INFINITY)
                },
                {
                        new Sphere(point, Double.NEGATIVE_INFINITY)
                }
        };
    }

    @Test(dataProvider = "parseDataThrowsException")
    void testCalcVolumeThrowsException(final Sphere sphere) {
        assertThrows(FigureException.class,
                () -> sphereAction.calcVolume(sphere));
    }

    /*
    --------------------Surface area-------------------
     */
    @DataProvider
    public Object[][] parseDataForCalcSurfaceArea() {
        Point point = new Point();
        return new Object[][]{
                {
                        new Sphere(point, 7.270882),
                        664.3302938746875
                },
                {
                        new Sphere(point, 523.89),
                        3448975.2786569446
                },
                {
                        new Sphere(point, 8),
                        804.247719318987
                },
                {
                        new Sphere(point, 334343434.3444),
                        1.40473842555632922E18
                },
                {
                        new Sphere(point, 7000000.00001),
                        6.157521601053586E14
                },
                {
                        new Sphere(point, 5555000006.9),
                        3.8777337954048606E20
                }
        };

    }

    @Test(dataProvider = "parseDataForCalcSurfaceArea")
    void testCalcSurfaceArea(final Sphere sphere, final double actualArea)
            throws FigureException {

        double expectedVolume = sphereAction.calcSurfaceArea(sphere);
        assertEquals(expectedVolume, actualArea, 0.0000001);
    }

    @Test(dataProvider = "parseDataThrowsException")
    void testCalcSurfaceAreaThrowsException(final Sphere sphere) {
        assertThrows(FigureException.class,
                () -> sphereAction.calcSurfaceArea(sphere));
    }

    //--------------------Calc ratio-------------------//
    @DataProvider
    public Object[] parseDataForCalcRatio() {
        return new Object[]{
                new TestData(
                        new Sphere(new Point(-2, 3, 4), 5),
                        new Plane(2, 2, 2, 2),
                        0.04314334891977073),
                new TestData(
                        new Sphere(new Point(23.3, -33.2, 3), 100),
                        new Plane(23.3, 3.8783, 7.32, 2),
                        0.004498791163843578),
                new TestData(
                        new Sphere(new Point(23223, 23232, 5478), 3232323),
                        new Plane(2, 2, 2, 2),
                        1.5396341282684656E-7),
        };
    }

    @DataProvider
    public Object[] parseDataForCalcRatioThrowsException() {
        return new Object[]{
                new TestData(
                        new Sphere(new Point(2.334, -45.3, 6.78), 1.2),
                        new Plane(-6, -2, -2, -2),
                        0),
                new TestData(
                        new Sphere(new Point(2.334, -45.3, 6.78), -1.2),
                        new Plane(6, 2, 2, 2),
                        0),
                new TestData(
                        new Sphere(new Point(Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE), Double.MIN_VALUE),
                        new Plane(Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE),
                        0),
        };
    }

    @Test(dataProvider = "parseDataForCalcRatio")
    void testCalcRatioOfVolumes(final TestData data)
            throws FigureException {
        double expectedRatio = sphereAction.calcRatioOfVolumes(data.sphere,
                data.plane);
        assertEquals(expectedRatio, (double) data.actualValue, 0.0000001);
    }

    @Test(dataProvider = "parseDataForCalcRatioThrowsException")
    void testCalcRatioOfVolumesThrowsException(final TestData data) {
        assertThrows(FigureException.class,
                () -> sphereAction.calcRatioOfVolumes(data.sphere, data.plane));
    }

    /*
    ---------------tangent to plane-----------------
    */
    @DataProvider
    public Object[] parseDataForIsSphereTangentToPlane() {
        return new Object[]{
                new TestData(
                        new Sphere(new Point(-2, 3, 4), 3.464101615137755),
                        new Plane(2, 2, 2, 2),
                        true),
                new TestData(
                        new Sphere(new Point(-23.3, -343.2, -3), 212.75357419637712),
                        new Plane(2, 2, 2, 2),
                        true),
                new TestData(
                        new Sphere(new Point(23223, 23232, 5478), 3232323),
                        new Plane(2, 2, 2, 2),
                        false),
                new TestData(
                        new Sphere(new Point(0, 0, 0), 10.0),
                        new Plane(2, 2, 2, 2),
                        false),
        };
    }

    @DataProvider
    public Object[] parseDataForIsSphereTangentToPlaneThrowsException() {
        return new Object[]{
                new TestData(
                        new Sphere(new Point(2.334, -45.3, 6.78), -1.2),
                        new Plane(-6, -2, -2, -2),
                        0),
                new TestData(
                        new Sphere(new Point(Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE), Double.MIN_VALUE),
                        new Plane(Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE),
                        0),
                new TestData(
                        new Sphere(new Point(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE), Double.MAX_VALUE),
                        new Plane(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE),
                        0)
        };
    }

    @Test(dataProvider = "parseDataForIsSphereTangentToPlane")
    void testIsSphereTangentToPlane(final TestData data)
            throws FigureException {
        boolean expectedValue = sphereAction.isSphereTangentToPlane(data.sphere, data.plane);
        assertEquals(expectedValue, (boolean) data.actualValue);
    }

    @Test(dataProvider = "parseDataForIsSphereTangentToPlaneThrowsException")
    void testIsSphereTangentToPlaneThrowsException(final TestData data) {
        assertThrows(FigureException.class,
                () -> sphereAction.calcRatioOfVolumes(data.sphere, data.plane));
    }


    /*
    ----------------------is sphere-------------------
    */
    @DataProvider
    public Object[][] parseDataForIsSphere() {
        return new Object[][]{
                {
                    new Sphere(new Point(-2, 3, 4), 3.464101615137755),
                        true
                },
                {
                    new Sphere(new Point(2.334, -45.3, 6.78), -1.2),
                        false
                },
                {
                    new Point(2.334, -45.3, 6.78),
                        false
                },
                {
                    null,
                        false
                }
        };
    }

    @Test(dataProvider = "parseDataForIsSphere")
    void isSphereTest(final Object o, final boolean actualValue) {
        assertEquals(sphereAction.isSphere(o), actualValue);
    }
}

