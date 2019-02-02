package by.epam.task1.entities;

import java.util.Objects;

/**
 * This class describes point in 2D-area using x and y positions.
 *
 * @author Rostislav Pekhovsky (Rastsislau Piakhouski) 2018
 * @version 0.1
 */
@SuppressWarnings("WeakerAccess")
public class Point {
    /*** X-axis variable.
     * */
    private double x;
    /*** Y-axis variable.
     * */
    private double y;
    /*** Z-axis variable.
     * */
    private double z;

    /**
     * Empty-body constructor.
     */
    public Point() {
    }

    /**
     * All-args constructor.
     *
     * @param xNew new x-axis variable
     * @param yNew new y-axis variable
     * @param zNew new z-axis variable
     */
    public Point(final double xNew, final double yNew, final double zNew) {
        this.x = xNew;
        this.y = yNew;
        this.z = zNew;
    }

    /**
     * @return x-axis variable.
     */
    public double getX() {
        return x;
    }

    /**
     * @param xNew new x-axis variable to set.
     */
    public void setX(final double xNew) {
        this.x = xNew;
    }

    /**
     * @return y-axis variable.
     */
    public double getY() {
        return y;
    }

    /**
     * @param yNew new y-axis variable to set.
     */
    public void setY(final double yNew) {
        this.y = yNew;
    }

    /**
     * @return y-axis variable.
     */
    public double getZ() {
        return z;
    }

    /**
     * @param zNew new z-axis variable to set.
     */
    public void setZ(final double zNew) {
        this.z = zNew;
    }

    /**
     * @param o object to compare**
     * @return true if points are similar, false if not
     */
    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Point point = (Point) o;
        return Double.compare(point.getX(), x) == 0
                && Double.compare(point.getY(), y) == 0
                && Double.compare(point.getZ(), z) == 0;
    }

    /**
     * @return hash code of a point
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    /**
     * @return string description (x-axis and y-axis) of a point
     */
    @Override
    public String toString() {
        return "x = " + x + ", y = " + y + ", z = " + z;
    }
}
