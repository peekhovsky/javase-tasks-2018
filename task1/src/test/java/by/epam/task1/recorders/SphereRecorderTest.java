package by.epam.task1.recorders;

import by.epam.task1.actions.SphereAction;
import by.epam.task1.entities.Point;
import by.epam.task1.entities.Sphere;
import by.epam.task1.exeptions.FigureException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SphereRecorderTest extends Assert {

    @DataProvider
    public Object[][] parseData() {
        return new Object[][]{
                {
                        new Sphere(new Point(-2, 3, 4), 5),
                        new Sphere(new Point(24.4545, -3.3434, -4.999), 5)
                },
                {
                        new Sphere(new Point(-23.3, -343.2, -3), 0.1),
                        new Sphere(new Point(23223, 23232, 5478), 3232323)
                }
        };
    }

    @Test(dataProvider = "parseData")
    void ReadFileTest(Sphere sphere, Sphere sphereWithNewValues) throws FigureException {
        SphereAction sphereAction = new SphereAction();

        SphereRecorder sphereRecorder = new SphereRecorder();
        sphere.addObserver(sphereRecorder);
        sphere.setRadius(sphereWithNewValues.getRadius());
        sphere.setCentrePoint(sphereWithNewValues.getCentrePoint());
        assertTrue(
                sphereRecorder.getVolume() == sphereAction
                        .calcVolume(sphereWithNewValues)
                        && sphereRecorder.getSurfaceArea() == sphereAction
                        .calcSurfaceArea(sphereWithNewValues));
    }
}
