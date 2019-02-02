package by.epam.task1.repository;

import by.epam.task1.entities.Point;
import by.epam.task1.entities.Sphere;
import by.epam.task1.repository.implementation.SphereRepositoryImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SphereRepositoryImplTest extends Assert {

    private SphereRepository sphereRepository
            = SphereRepositoryImpl.getInstance();

    private List<Sphere> spheres;

    @BeforeMethod
    void prepareSphereRepository() {
        sphereRepository.deleteAll();
        Point point = new Point(1, 1, 1);
        spheres = new ArrayList<>();
        spheres.add(new Sphere("sphere1", point, 5));
        spheres.add(new Sphere("sphere2", point, 5));
        spheres.add(new Sphere("sphere2", point, 7));
        spheres.add(new Sphere(point, 1));
        spheres.add(new Sphere(point, 1));
        spheres.forEach(sphere -> sphereRepository.add(sphere));
    }

    @DataProvider
    Object[] parseDataAddDeleteFind() {
        Point point = new Point(1, 1, 1);
        return new Object[]{
                new Sphere("sphere1", point, 5),
                new Sphere("SPHERE2", point, 1),
                new Sphere("sphere2", point, 1),
                new Sphere("sphere333", point, 1)
        };
    }

    @Test
    void testSingleton() {
        assertSame(sphereRepository, SphereRepositoryImpl.getInstance());
    }

    @Test(dataProvider = "parseDataAddDeleteFind")
    void testAdd(final Sphere newSphere) {
        int id = sphereRepository.add(newSphere);
        Optional<Sphere> sphereOptional = sphereRepository.findById(id);
        assertTrue(sphereOptional.isPresent()
                && sphereOptional.get().equals(newSphere));
    }

    @Test(dataProvider = "parseDataAddDeleteFind")
    void testDelete(final Sphere newSphere) {
        sphereRepository.deleteAll();
        int id = sphereRepository.add(newSphere);
        sphereRepository.delete(id);
        assertFalse(sphereRepository.findById(id).isPresent());
    }

    @Test(dataProvider = "parseDataAddDeleteFind")
    void testFindById(final Sphere newSphere) {
        int id = sphereRepository.add(newSphere);
        assertTrue(sphereRepository.findById(id).isPresent());
    }

    @Test(dataProvider = "parseDataAddDeleteFind")
    void testFindByName(final Sphere sphere1) {
        sphereRepository.deleteAll();
        List<Sphere> sphereList = new ArrayList<>();
        Sphere sphere2 = new Sphere(sphere1);
        Sphere sphere3 = new Sphere(sphere1);
        sphereList.add(sphere1);
        sphereList.add(sphere2);
        sphereList.add(sphere3);
        sphereRepository.add(sphere1);
        sphereRepository.add(sphere2);
        sphereRepository.add(sphere3);
        assertEquals(sphereRepository.findByName(sphere1.getName()), sphereList);
    }

    @Test
    void testFindAll() {
        assertEquals(spheres, sphereRepository.findAll());
    }


    @DataProvider
    Object[][] parseDataFindAllInSphereAndInRange() {

        List<Sphere> innerSpheres = new ArrayList<>();
        innerSpheres.add(
                new Sphere(new Point(0, 0, 0), 3));

        innerSpheres.add(
                new Sphere(new Point(-1.5, 1, 1), 1));

        innerSpheres.add(
                new Sphere(new Point(0, 0, 4), 1));

        return new Object[][]{
                {
                        new Sphere(new Point(0, 0, 0),
                                5),
                        innerSpheres
                }
        };
    }

    @Test(dataProvider = "parseDataFindAllInSphereAndInRange")
    void testFindAllInSphere(Sphere outerSphere, List<Sphere> innerSpheres) {
        sphereRepository.deleteAll();
        innerSpheres.forEach(sphere -> sphereRepository.add(sphere));
        assertEquals(innerSpheres,
                sphereRepository.findAllInSphere(outerSphere));
    }

    @Test(dataProvider = "parseDataFindAllInSphereAndInRange")
    void testFindAllInRange(Sphere outerSphere, List<Sphere> innerSpheres) {
        sphereRepository.deleteAll();
        innerSpheres.forEach(sphere -> sphereRepository.add(sphere));
        assertEquals(innerSpheres,
                sphereRepository.findAllInRange(outerSphere.getRadius()));
    }

    @DataProvider
    Object[][] parseDataForSorting() {

        Sphere sphere1 = new Sphere("cname1",
                new Point(-1, -1, -1), 66);
        Sphere sphere2 = new Sphere("3name2",
                new Point(-4.88, -4.88, -4.88), 3);
        Sphere sphere3 = new Sphere("z3333",
                new Point(777, 777, 777), 8898.56);
        Sphere sphere4 = new Sphere("rdd",
                new Point(345.87, 345.87, 345.87), 112);

        List<Sphere> expectedList = new ArrayList<>();
        expectedList.add(sphere1);
        expectedList.add(sphere2);
        expectedList.add(sphere3);
        expectedList.add(sphere4);

        List<Sphere> actualList = new ArrayList<>();
        actualList.add(sphere2);
        actualList.add(sphere1);
        actualList.add(sphere4);
        actualList.add(sphere3);


        return new Object[][]{
                {
                        expectedList,
                        actualList
                }
        };
    }

    @Test(dataProvider = "parseDataForSorting")
    void testSortByName(List<Sphere> expectedList, List<Sphere> actualList) {
        sphereRepository.deleteAll();
        sphereRepository.addAll(expectedList);
        sphereRepository.sortByName();
        assertEquals(sphereRepository.findAll(), actualList);
    }

    @Test(dataProvider = "parseDataForSorting")
    void testSortByPointX(List<Sphere> expectedList, List<Sphere> actualList) {
        sphereRepository.deleteAll();
        sphereRepository.addAll(expectedList);
        sphereRepository.sortByCentrePointX();
        assertEquals(sphereRepository.findAll(), actualList);
    }

    @Test(dataProvider = "parseDataForSorting")
    void testSortByPointY(List<Sphere> expectedList, List<Sphere> actualList) {
        sphereRepository.deleteAll();
        sphereRepository.addAll(expectedList);
        sphereRepository.sortByCentrePointY();
        assertEquals(sphereRepository.findAll(), actualList);
    }

    @Test(dataProvider = "parseDataForSorting")
    void testSortByPointZ(List<Sphere> expectedList, List<Sphere> actualList) {
        sphereRepository.deleteAll();
        sphereRepository.addAll(expectedList);
        sphereRepository.sortByCentrePointZ();
        assertEquals(sphereRepository.findAll(), actualList);
    }

    @Test(dataProvider = "parseDataForSorting")
    void testSortByRadius(List<Sphere> expectedList, List<Sphere> actualList) {
        sphereRepository.deleteAll();
        sphereRepository.addAll(expectedList);
        sphereRepository.sortByRadius();
        assertEquals(sphereRepository.findAll(), actualList);
    }
}


