package by.epam.task1.repository;

import by.epam.task1.entities.Sphere;

import java.util.List;

/**
 * There is simple interface for repository, which stores spheres.
 *
 * @author Rostislav Pekhovsky (Rastsislau Piakhouski) 2018
 * @version 0.1
 * @see Sphere
 */
public interface SphereRepository extends Repository<Sphere> {

    /**
     * @param name a name to find in storage all spheres with similar name
     * @return a list with appropriate spheres
     */
    List<Sphere> findByName(String name);

    /**
     * @param sphere a sphere to find in storage all spheres that are in it.
     * @return a list with appropriate spheres
     */
    List<Sphere> findAllInSphere(Sphere sphere);

    /**
     * @param range a range to find in storage all spheres that are in it.
     * @return a list with appropriate spheres
     * */
    List<Sphere> findAllInRange(double range);

    /**
     * This method sorts spheres in a storage by their name.
     * */
    void sortByName();

    /**
     * This method sorts spheres in a storage by x-axis of centre point.
     * */
    void sortByCentrePointX();

    /**
     * This method sorts spheres in a storage by y-axis of centre point.
     * */
    void sortByCentrePointY();

    /**
     * This method sorts spheres in a storage by z-axis of centre points.
     * */
    void sortByCentrePointZ();

    /**
     * This method sorts spheres in a storage by size of radius.
     * */
    void sortByRadius();

}
