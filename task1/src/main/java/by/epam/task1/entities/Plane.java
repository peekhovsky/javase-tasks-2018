package by.epam.task1.entities;

import java.util.Objects;

/**
 * An Entity that describes plane using 4 coefficients. Plane formula:
 * <p>
 * y = Ax + By + Cz + D
 * <p>
 * The only think class should have is only four (A, B, C, D) coefficients
 * to know everything about plane.
 *
 * @author Rostislav Pekhovsky (Rastsislau Piakhouski) 2018
 * @version 0.1
 */
public class Plane {
    /**
     * Coefficient A.
     */
    private double a;
    /**
     * Coefficient B.
     */
    private double b;
    /**
     * Coefficient C.
     */
    private double c;
    /**
     * Coefficient D.
     */
    private double d;

    /**
     * No args constructor.
     */
    private Plane() {
    }

    /*** All args constructor.
     *
     * @param aNew coefficient A
     * @param bNew Coefficient B
     * @param cNew coefficient C
     * @param dNew coefficient D
     */
    public Plane(final double aNew, final double bNew,
                 final double cNew, final double dNew) {
        this.a = aNew;
        this.b = bNew;
        this.c = cNew;
        this.d = dNew;
    }

    /**
     * @return coefficient A
     */
    public double getA() {
        return a;
    }

    /**
     * @param aNew value to set coefficient A
     */
    public void setA(final double aNew) {
        this.a = aNew;
    }

    /**
     * @return coefficient B
     */
    public double getB() {
        return b;
    }

    /**
     * @param bNew value to set coefficient B
     */
    public void setB(final double bNew) {
        this.b = bNew;
    }

    /**
     * @return coefficient C
     */
    public double getC() {
        return c;
    }

    /**
     * @param cNew value to set coefficient C
     */
    public void setC(final double cNew) {
        this.c = cNew;
    }

    /**
     * @return coefficient D
     */
    public double getD() {
        return d;
    }

    /**
     * @param dNew value to set coefficient D
     */
    public void setD(final double dNew) {
        this.d = dNew;
    }

    /**
     * @param o object to compare**
     * @return true if planes are similar, false if not
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Plane plane = (Plane) o;
        return Objects.equals(a, plane.a)
                && Objects.equals(b, plane.b)
                && Objects.equals(c, plane.c)
                && Objects.equals(d, plane.d);
    }

    /**
     * @return hash code of a point
     */
    @Override
    public int hashCode() {
        return Objects.hash(a, b, c, d);
    }

    /**
     * @return string description (x-axis and y-axis) of a point
     */
    @Override
    public String toString() {
        return "Plane: (" + a + ")x + (" + b + ")y + ("
                + c + ")z + (" + d + ").\n";
    }
}
