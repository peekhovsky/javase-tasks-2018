package by.epam.task1.entities;

import java.util.Objects;
import java.util.Observable;

/**
 * An Entity that describes sphere using centre point and radius.
 *
 * @author Rostislav Pekhovsky (Rastsislau Piakhouski) 2018
 * @version 0.1
 */
@SuppressWarnings("WeakerAccess")
public class Sphere extends Observable {
    /**
     * Sphere name.
     */
    private String name;
    /**
     * Point that is located in the centre of a circle.
     */
    private Point centrePoint;
    /**
     * Sphere radius.
     */
    private double radius;

    /**
     * No args constructor.
     */
    public Sphere() {
    }

    /**
     * Constructor without name and id parameter.
     *
     * @param centrePointNew point that is located in the centre of a circle.
     * @param radiusNew      sphere radius
     */
    public Sphere(final Point centrePointNew, final double radiusNew) {
        this.centrePoint = centrePointNew;
        this.radius = radiusNew;
        this.name = "";
    }

    /**
     * Constructor without id parameter.
     *
     * @param newName        sphere new name to set
     * @param centrePointNew point that is located in the centre of a circle.
     * @param radiusNew      sphere radius
     */
    public Sphere(final String newName,
                  final Point centrePointNew, final double radiusNew) {
        this.name = newName;
        this.centrePoint = centrePointNew;
        this.radius = radiusNew;
    }

    /**
     * Copy constructor.
     *
     * @param oldSphere sphere to copy
     */
    public Sphere(final Sphere oldSphere) {
        this.name = oldSphere.name;
        this.centrePoint = oldSphere.centrePoint;
        this.radius = oldSphere.radius;
    }

    /**
     * @return sphere name
     */
    public String getName() {
        return name;
    }

    /**
     * @param newName new name for sphere
     */
    public void setName(final String newName) {
        this.name = newName;
    }

    /**
     * @return point that is located in the centre of a circle.
     */
    public Point getCentrePoint() {
        return centrePoint;
    }

    /**
     * @param newCentrePoint new point that is located in the centre
     *                       of a circle.
     */
    public void setCentrePoint(final Point newCentrePoint) {
        this.centrePoint = newCentrePoint;
        setChanged();
        notifyObservers();
    }

    /**
     * @return a circle radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * @param radiusNew new radius for a circle
     */
    public void setRadius(final double radiusNew) {
        this.radius = radiusNew;
        setChanged();
        notifyObservers();
    }

    /**
     * @param o an object to compare
     * @return true if circles are similar, false if not.
     */
    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sphere sphere = (Sphere) o;
        return this.centrePoint.equals(sphere.getCentrePoint());
    }

    /**
     * @return a hash code of a circle
     */
    @Override
    public int hashCode() {
        return Objects.hash(centrePoint, radius);
    }

    /**
     * @return string description of a circle
     */
    @Override
    public String toString() {
        return "\nSphere: centre point - " + centrePoint.toString()
                + "; radius - " + radius;
    }
}

